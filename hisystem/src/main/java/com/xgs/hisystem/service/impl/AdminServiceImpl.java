package com.xgs.hisystem.service.impl;

import com.xgs.hisystem.config.HisConstants;
import com.xgs.hisystem.pojo.bo.BaseResponse;
import com.xgs.hisystem.pojo.bo.PageRspBO;
import com.xgs.hisystem.pojo.entity.*;
import com.xgs.hisystem.pojo.vo.*;
import com.xgs.hisystem.repository.*;
import com.xgs.hisystem.service.IAdminService;
import com.xgs.hisystem.util.ChineseCharacterUtil;
import com.xgs.hisystem.util.DateUtil;
import com.xgs.hisystem.util.MD5Util;
import org.apache.shiro.SecurityUtils;
import org.apache.shiro.crypto.hash.SimpleHash;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;
import javax.transaction.Transactional;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

/**
 * @author xgs
 * @date 2019/4/3
 * @description:
 */
@Service
public class AdminServiceImpl implements IAdminService {

    @Autowired
    private IUserRepository iUserRepository;

    @Autowired
    private IRoleRespository iRoleRespository;

    @Autowired
    private IUserRoleRepository iUserRoleRepository;

    @Autowired
    private IAnnouncementRepository iAnnouncementRepository;

    @Autowired
    private IDepartmentRepository iDepartmentRepository;

    private static final Logger logger = LoggerFactory.getLogger(AdminServiceImpl.class);

    /**
     * 新增角色
     *
     * @param roleVO
     * @return
     */
    @Override
    public BaseResponse<String> createRole(RoleVO roleVO) {

        RoleEntity roleEntity = new RoleEntity();
        roleEntity.setRole(roleVO.getRolename());
        roleEntity.setRoleValue(roleVO.getRoleValue());
        roleEntity.setDescription(roleVO.getDesciption());

        try {
            iRoleRespository.saveAndFlush(roleEntity);
            return BaseResponse.success();
        } catch (Exception e) {
            return BaseResponse.error(e.getMessage());
        }

    }

    /**
     * 用户添加角色
     *
     * @param addRoleVO
     * @return
     */
    @Transactional
    @Override
    public BaseResponse<String> addRole(AddRoleVO addRoleVO) {

        UserEntity user = iUserRepository.findByEmail(addRoleVO.getEmail());

        String uId = user.getId();
        try {
            addRoleVO.getRoleList().forEach(role -> {

                RoleEntity roleEntity = iRoleRespository.findByRoleValue(role);
                String roleId = roleEntity.getId();
                UserRoleEntity checkUserRole = iUserRoleRepository.findByUIdAndRoleId(uId, roleId);
                if (checkUserRole != null) {
                    logger.info("--**--账户：{} 已拥有 {} 角色--**--", user.getEmail(), roleEntity.getRole());
                    return;
                }

                UserRoleEntity userRoleEntity = new UserRoleEntity();
                userRoleEntity.setuId(uId);
                userRoleEntity.setRoleId(roleId);
                String desciption = user.getEmail() + "#" + roleEntity.getRole();
                userRoleEntity.setDescription(desciption);
                iUserRoleRepository.saveAndFlush(userRoleEntity);
            });
            return BaseResponse.success();
        } catch (Exception e) {
            return BaseResponse.error(e.getMessage());
        }

    }

    @Override
    public PageRspBO<applyRspVO> getRoleApply(BasePageReqVO reqVO) {

        Page<UserRoleEntity> page = iUserRoleRepository.findAll(new Specification<UserRoleEntity>() {
            @Override
            public Predicate toPredicate(Root<UserRoleEntity> root, CriteriaQuery<?> query, CriteriaBuilder criteriaBuilder) {
                List<Predicate> predicateList = new ArrayList<>();

                Predicate userRole = criteriaBuilder.equal(root.get("roleStatus"), 0);
                predicateList.add(userRole);
                query.where(predicateList.toArray(new Predicate[predicateList.size()]));
                return null;
            }
        }, PageRequest.of(reqVO.getPageNumber(), reqVO.getPageSize(), Sort.Direction.DESC,"createDatetime"));
        if (page == null) {
            return null;
        }
        List<UserRoleEntity> userRoleList = page.getContent();

        List<applyRspVO> applyRspVOList = new ArrayList<>();

        userRoleList.forEach(userRole -> {
            Optional<UserEntity> user = iUserRepository.findById(userRole.getuId());

            applyRspVO applyRspVO = new applyRspVO();
            applyRspVO.setEmail(user.get().getEmail());
            applyRspVO.setUsername(user.get().getUsername());
            applyRspVO.setDateTime(userRole.getCreateDatetime());

            Optional<RoleEntity> role = iRoleRespository.findById(userRole.getRoleId());
            applyRspVO.setRole(role.get().getDescription());

            applyRspVOList.add(applyRspVO);
        });

        PageRspBO pageRspBO = new PageRspBO();
        pageRspBO.setRows(applyRspVOList);
        pageRspBO.setTotal(page.getTotalElements());
        return pageRspBO;
    }

    /**
     * 后台添加账户
     *
     * @param reqVO
     * @return
     */
    @Override
    public BaseResponse<String> saveUserAndSendEmailTemp(UserRegisterReqVO reqVO) {
        String email = reqVO.getEmail();
        String roleName = reqVO.getRoleName();

        //验证角色
        RoleEntity role = iRoleRespository.findByDescription(roleName);
        if (role == null) {
            return BaseResponse.error("您选择的角色不存在，请重试！");
        }

        UserEntity checkUser = iUserRepository.findByEmail(email);

        if (checkUser != null) {

            return BaseResponse.error(HisConstants.USER.ACCOUNT_EXIST);
        }

        UserEntity userEntity = new UserEntity();

        userEntity.setEmail(email);
        userEntity.setUsername(reqVO.getUsername());
        userEntity.setPlainPassword(reqVO.getPassword());
        //生成盐和加盐密码
        String salt = MD5Util.md5Encrypt32Lower(reqVO.getEmail());
        String password = new SimpleHash("MD5", reqVO.getPassword(), salt, 1024).toHex(); // 使用SimpleHash类对原始密码进行加密

        userEntity.setPassword(password);
        userEntity.setSalt(salt);
        //生成激活码
        String validateCode = MD5Util.md5Encrypt32Upper(reqVO.getEmail());
        userEntity.setValidateCode(validateCode);
        userEntity.setEmailStatus(0);

        try {
            iUserRepository.saveAndFlush(userEntity);
            //保存角色
            UserEntity user = iUserRepository.findByEmail(email);
            String uId = user.getId();

            UserRoleEntity userRole = new UserRoleEntity();
            userRole.setuId(uId);
            userRole.setRoleId(role.getId());
            String desciption = user.getEmail() + "#" + role.getRole();
            userRole.setDescription(desciption);
            userRole.setRoleStatus(0);

            iUserRoleRepository.saveAndFlush(userRole);
            return BaseResponse.success(HisConstants.USER.SUCCESS);
        } catch (Exception e) {
            e.printStackTrace();
            return BaseResponse.error("保存用户信息发送异常!");
        }


    }

    /**
     * 审核角色的通知
     *
     * @return
     */
    @Override
    public List<applyRspVO> getRoleNotice() {

        //从shiro中获取当前登录用户信息
        UserEntity userEntity = (UserEntity) SecurityUtils.getSubject().getPrincipal();

        if (StringUtils.isEmpty(userEntity)) {
            return null;
        }

        List<applyRspVO> applyRspList = new ArrayList<>();
        //若为管理员，组装审核角色通知参数
        long checkCount = userEntity.getRoleList()
                .stream().filter(roleEntity -> roleEntity.getRole().equals("admin")).count();

        if (checkCount > 0) {
            List<UserRoleEntity> userRoleList = iUserRoleRepository.findByRoleStatusOrderByCreateDatetimeDesc(0);

            if (userRoleList != null && userRoleList.size() > 0) {
                applyRspList.addAll(userRoleList.stream().map(userRole -> {
                    applyRspVO applyRspVO = new applyRspVO();
                    applyRspVO.setDateTime(userRole.getCreateDatetime());
                    Optional<UserEntity> user = iUserRepository.findById(userRole.getuId());
                    if (user.isPresent()) {
                        applyRspVO.setEmail(user.get().getEmail());
                        applyRspVO.setUsername(user.get().getUsername());
                    }
                    Optional<RoleEntity> role = iRoleRespository.findById(userRole.getRoleId());
                    role.ifPresent(roleEntity -> applyRspVO.setRole(roleEntity.getDescription()));
                    return applyRspVO;
                }).collect(Collectors.toList()));
            }
        }
        return applyRspList;
    }

    /**
     * 审核角色
     *
     * @param status
     * @param email
     * @return
     */
    @Override
    public BaseResponse<?> changeRoleStatus(String status, String email) {

        if (!StringUtils.isEmpty(status)) {
            UserEntity user = iUserRepository.findByEmail(email);

            UserRoleEntity userRole = iUserRoleRepository.findByUIdAndRoleStatus(user.getId(), 0);

            int mystatus = Integer.parseInt(status);
            userRole.setRoleStatus(mystatus);

            iUserRoleRepository.saveAndFlush(userRole);
        }
        return BaseResponse.success();
    }

    /**
     * 公告相关
     *
     * @param reqVO
     * @return
     */

    @Override
    public BaseResponse<String> addAnnouncement(AnnouncementVO reqVO) {

        AnnouncementEntity announcementTemp = iAnnouncementRepository.findByTitle(reqVO.getTitle());
        if (announcementTemp != null && reqVO.getContents().equals(announcementTemp.getContents())) {

            return BaseResponse.error(HisConstants.USER.ANN_EQUALS);
        }

        AnnouncementEntity announcement = new AnnouncementEntity();
        announcement.setTitle(reqVO.getTitle());
        announcement.setContents(reqVO.getContents());

        announcement.setAnnStatus(0);
        announcement.setAnnDate(DateUtil.getCurrentDateSimpleToString());
        try {
            iAnnouncementRepository.saveAndFlush(announcement);

            return BaseResponse.success(HisConstants.USER.SUCCESS);
        } catch (Exception e) {
            return BaseResponse.error("系统异常，请稍后重试！");
        }
    }

    @Override
    public PageRspBO<AnnouncementVO> getAnnouncement(BasePageReqVO reqVO) {
        Page<AnnouncementEntity> page = iAnnouncementRepository.findAll(new Specification<AnnouncementEntity>() {
            @Override
            public Predicate toPredicate(Root<AnnouncementEntity> root, CriteriaQuery<?> query, CriteriaBuilder criteriaBuilder) {
                List<Predicate> predicateList = new ArrayList<>();
                predicateList.add(criteriaBuilder.isNotNull(root.get("title")));
                query.where(predicateList.toArray(new Predicate[predicateList.size()]));

                return null;
            }
        }, PageRequest.of(reqVO.getPageNumber(), reqVO.getPageSize()));

        if (page == null) {
            return null;
        }
        List<AnnouncementEntity> announcementList = page.getContent();
        List<AnnouncementVO> announcementVOList = new ArrayList<>();
        announcementList.forEach(announcement -> {
            AnnouncementVO announcementVO = new AnnouncementVO();
            announcementVO.setId(announcement.getId());
            announcementVO.setTitle(announcement.getTitle());
            announcementVO.setContents(announcement.getContents());

            announcementVO.setAnnStatus(announcement.getAnnStatus());
            announcementVOList.add(announcementVO);
        });

        PageRspBO pageRspBO = new PageRspBO();
        pageRspBO.setTotal(page.getTotalElements());
        pageRspBO.setRows(announcementVOList);
        return pageRspBO;
    }

    @Override
    public BaseResponse<String> changeAnnouncement(AnnouncementVO announcementVO) {


        Optional<AnnouncementEntity> announcement = iAnnouncementRepository.findById(announcementVO.getId());
        announcement.get().setTitle(announcementVO.getTitle());
        announcement.get().setContents(announcementVO.getContents());
        announcement.get().setAnnDate(DateUtil.getCurrentDateSimpleToString());
        try {
            iAnnouncementRepository.saveAndFlush(announcement.get());
            return BaseResponse.success(HisConstants.USER.SUCCESS);
        } catch (Exception e) {
            return BaseResponse.error(HisConstants.USER.FAIL);
        }


    }

    @Override
    public BaseResponse<String> deleteAnnouncement(String id) {

        try {
            iAnnouncementRepository.deleteById(id);
            return BaseResponse.success(HisConstants.USER.SUCCESS);
        } catch (Exception e) {
            return BaseResponse.error(HisConstants.USER.FAIL);
        }

    }

    @Override
    public BaseResponse<String> showAnnouncement(String id) {

        Optional<AnnouncementEntity> announcement = iAnnouncementRepository.findById(id);
        announcement.get().setAnnStatus(1);
        try {
            iAnnouncementRepository.saveAndFlush(announcement.get());
            return BaseResponse.success(HisConstants.USER.SUCCESS);
        } catch (Exception e) {
            return BaseResponse.success(HisConstants.USER.FAIL);
        }
    }

    @Override
    public BaseResponse<String> hiddenAnnouncement(String id) {

        Optional<AnnouncementEntity> announcement = iAnnouncementRepository.findById(id);
        announcement.get().setAnnStatus(0);
        try {
            iAnnouncementRepository.saveAndFlush(announcement.get());
            return BaseResponse.success(HisConstants.USER.SUCCESS);
        } catch (Exception e) {
            return BaseResponse.success(HisConstants.USER.FAIL);
        }
    }

    @Override
    public BaseResponse<String> addDepartment(AddDepartmentReqVO reqVO) {

        String name = reqVO.getName();
        String address = reqVO.getAddress();

        DepartmentEntity department = iDepartmentRepository.findByNameAndAddress(name, address);

        if (department != null) {
            return BaseResponse.error("该科室已存在！");
        }
        department = new DepartmentEntity();
        BeanUtils.copyProperties(reqVO, department);

        String nameCode = ChineseCharacterUtil.getUpperCase(name, false)
                .concat("-").concat(ChineseCharacterUtil.getUpperCase(address, false));
        department.setNameCode(nameCode);

        iDepartmentRepository.saveAndFlush(department);

        return BaseResponse.success();
    }

    @Override
    public List<GetDepartmentRspVO> getDepartment() {

        List<GetDepartmentRspVO> rspVO = new ArrayList<>();

        List<DepartmentEntity> departmentList = iDepartmentRepository.findAll();

        if (departmentList != null && !departmentList.isEmpty()) {

            rspVO.addAll(departmentList.stream().map(department -> {
                GetDepartmentRspVO rsp = new GetDepartmentRspVO();
                BeanUtils.copyProperties(department, rsp);
                return rsp;
            }).collect(Collectors.toList()));
        }
        return rspVO;
    }
}
