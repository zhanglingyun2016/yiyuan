package com.xgs.hisystem.service.impl;

import com.xgs.hisystem.config.HisConstants;
import com.xgs.hisystem.pojo.bo.BaseResponse;
import com.xgs.hisystem.pojo.bo.PageRspBO;
import com.xgs.hisystem.pojo.entity.*;
import com.xgs.hisystem.pojo.vo.register.*;
import com.xgs.hisystem.repository.*;
import com.xgs.hisystem.service.IGetPatientInfoService;
import com.xgs.hisystem.service.IRegisterService;
import com.xgs.hisystem.util.DateUtil;
import com.xgs.hisystem.util.IdCardValidUtil;
import org.apache.shiro.SecurityUtils;
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
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import static com.xgs.hisystem.util.card.Card.defaultGetCardId;
import static java.time.LocalDate.now;

/**
 * @author xgs
 * @date 2019/4/19
 * @description:
 */
@Service
public class RegisterServiceImpl implements IRegisterService {

    @Autowired
    private IUserRepository iUserRepository;

    @Autowired
    private IIDcardRepository iiDcardRepository;

    @Autowired
    private IPatientRepository iPatientRepository;
    @Autowired
    private IRegisterRepository iRegisterRepository;
    @Autowired
    private IOutpatientQueueRepository iOutpatientQueueRepository;
    @Autowired
    private IGetPatientInfoService iGetPatientInfoService;
    @Autowired
    private IDepartmentRepository iDepartmentRepository;

    private static final Logger logger = LoggerFactory.getLogger(RegisterServiceImpl.class);

    /**
     * 获取就诊卡信息
     *
     * @return
     */
    @Override
    public PatientInforRspVO getCardIdInfor(GetCardIdInforReqVO reqVO) throws Exception {

        PatientInforRspVO rspVO = new PatientInforRspVO();

        try {
            //获取患者信息
            BaseResponse<PatientEntity> baseResponse = iGetPatientInfoService.getPatientInfo(reqVO);

            if (BaseResponse.RESPONSE_FAIL.equals(baseResponse.getStatus())) {
                rspVO.setMessage(baseResponse.getMessage());
                return rspVO;
            }
            PatientEntity patientInfor = baseResponse.getData();

            String patientId = patientInfor.getId();

            List<RegisterEntity> registerList = iRegisterRepository.findByPatientId(patientId);

            if (registerList != null && !registerList.isEmpty()) {
                //过期的挂号
                List<RegisterEntity> expiredList = new ArrayList<>();

                for (RegisterEntity register : registerList) {

                    int registerStatus = register.getRegisterStatus();
                    int treatmentStatus = register.getTreatmentStatus();
                    int chargeStatus = register.getChargeStatus();
                    //已挂号
                    if (registerStatus == 1) {
                        //未就诊情况下
                        if (treatmentStatus == 0) {

                            String createDate = DateUtil.DateTimeToDate(register.getCreateDatetime());
                            String nowDate = DateUtil.getCurrentDateSimpleToString();
                            //当天情况下
                            if (nowDate.equals(createDate)) {

                                //检查门诊队列是否待处理状态
                                OutpatientQueueEntity outpatientQueue = iOutpatientQueueRepository.findByRegisterId(register.getId());
                                if (outpatientQueue != null && outpatientQueue.getOutpatientQueueStatus() == HisConstants.QUEUE.NORMAL) {
                                    String doctorName = Arrays.asList(outpatientQueue.getDescription().split("#")).get(1);
                                    rspVO.setMessage("当日有未完成的就诊，请完成就诊！门诊医生：" + doctorName);
                                    return rspVO;
                                }
                            }
                            //不是当天则修改挂号状态为：-1 （过期）
                            else {
                                register.setRegisterStatus(-1);
                                expiredList.add(register);
                            }
                        }
                        //已就诊未缴费
                        if (treatmentStatus == 1 && chargeStatus == 0) {
                            rspVO.setMessage("存在已就诊未收费的记录，请及时缴费！");
                            return rspVO;
                        }
                    }
                }
                iRegisterRepository.saveAll(expiredList);
            }
            rspVO.setAge(DateUtil.getAge(patientInfor.getBirthday()));
            BeanUtils.copyProperties(patientInfor, rspVO);
        } catch (Exception e) {
            rspVO.setMessage("查询就诊卡信息失败，请检查添加就诊卡时输入信息是否规范！");
            e.printStackTrace();
        }
        return rspVO;
    }


    /**
     * 【没有身份证阅读器，将普通IC卡与病人信息绑定】
     * 获取身份证信息
     *
     * @return
     */
    @Override
    public IDcardRspVO getIDcardInfor() {

        String message = defaultGetCardId();

        IDcardRspVO iDcardRspVO = new IDcardRspVO();

        if ("fail".equals(message)) {
            iDcardRspVO.setMessage("读卡失败！请刷新页面重试");
            return iDcardRspVO;
        } else if ("none".equals(message)) {
            iDcardRspVO.setMessage("未识别到卡片！");
            return iDcardRspVO;
        } else {
            IDcardEntity iDcardEntity = iiDcardRepository.findByCardId(message);

            if (iDcardEntity == null) {
                iDcardRspVO.setMessage("未从该卡片识别到证件信息！");
                return iDcardRspVO;
            }
            BeanUtils.copyProperties(iDcardEntity, iDcardRspVO);
            return iDcardRspVO;
        }
    }

    /**
     * 读取新卡
     *
     * @return
     */
    @Override
    public BaseResponse<String> getDefaultGetCardId() {

        return BaseResponse.success(defaultGetCardId());
    }

    /**
     * 添加就诊卡
     *
     * @param reqVO
     * @return
     */
    @Override
    public BaseResponse<String> addPatientInfor(PatientInforReqVO reqVO) throws Exception {

        boolean bool = IdCardValidUtil.validateIdCard(reqVO.getIdCard());
        if (!bool) {
            return BaseResponse.error("身份证号码格式有误！");
        }

        try {
            //验证该就诊卡是否已被使用
            PatientEntity patientEntity1 = iPatientRepository.findByCardId(reqVO.getCardId());
            if (!StringUtils.isEmpty(patientEntity1)) {
                return BaseResponse.error(HisConstants.REGISTER.ACTIVATED);
            }
            //验证患者已注册过就诊卡
            PatientEntity patientEntity2 = iPatientRepository.findByIdCard(reqVO.getIdCard());
            if (!StringUtils.isEmpty(patientEntity2)) {
                return BaseResponse.error(HisConstants.REGISTER.COVER);
            }

            PatientEntity patientInfor = new PatientEntity();
            BeanUtils.copyProperties(reqVO, patientInfor);

            iPatientRepository.saveAndFlush(patientInfor);
            return BaseResponse.success(HisConstants.USER.SUCCESS);
        } catch (Exception e) {
            return BaseResponse.error("添加就诊卡异常，请稍后重试！");
        }
    }

    /**
     * 补办就诊卡
     *
     * @param reqVO
     * @return
     */
    @Override
    public BaseResponse<String> coverCardId(PatientInforReqVO reqVO) {

        PatientEntity patientInfor = iPatientRepository.findByIdCard(reqVO.getIdCard());
        if (StringUtils.isEmpty(patientInfor)) {
            return BaseResponse.error(HisConstants.USER.FAIL);
        }
        patientInfor.setCardId(reqVO.getCardId());

        try {
            iPatientRepository.saveAndFlush(patientInfor);
            return BaseResponse.success(HisConstants.USER.SUCCESS);
        } catch (Exception e) {
            return BaseResponse.error("补办就诊卡异常！");
        }
    }

    /**
     * 挂号获取医生
     *
     * @param reqVO
     * @return
     */
    @Override
    public List<RegisterDoctorRspVO> getAllRegisterDoctor(RegisterTypeReqVO reqVO) {

        List<RegisterDoctorRspVO> registerDoctorRspList = new ArrayList<>();

        List<UserEntity> userList = iUserRepository.findByDepartmentAndDepartmentType(reqVO.getDepartment(), reqVO.getRegisterType());

        if (userList != null && userList.size() > 0) {
            RegisterDoctorRspVO registerDoctorRspVO = new RegisterDoctorRspVO();
            userList.forEach(user -> {
                //更新已挂号数
                if (!DateUtil.getCurrentDateSimpleToString().equals(user.getUpdateTime())) {
                    user.setNowNum(0);
                    user.setUpdateTime(DateUtil.getCurrentDateSimpleToString());
                    iUserRepository.saveAndFlush(user);
                }
                registerDoctorRspVO.setDoctorName(user.getUsername());
                registerDoctorRspVO.setAllowNum(user.getAllowNum());
                registerDoctorRspVO.setNowNum(user.getNowNum());
                registerDoctorRspVO.setWorkDateTime(user.getWorkDateTime());
                registerDoctorRspVO.setPrice(user.getTreatmentPrice());
                registerDoctorRspVO.setId(user.getId());
                registerDoctorRspVO.setWorkAddress(user.getWorkAddress());

                registerDoctorRspList.add(registerDoctorRspVO);
            });

        }

        return registerDoctorRspList;
    }

    /**
     * 保存挂号记录
     *
     * @param reqVO
     * @return
     */
    @Override
    public BaseResponse<String> addRegisterInfor(RegisterInforReqVO reqVO) {

        try {

            UserEntity user = (UserEntity) SecurityUtils.getSubject().getPrincipal();
            if (StringUtils.isEmpty(user)) {
                return BaseResponse.error("登录信息已过期！");
            }
            Optional<UserEntity> userDoctor = iUserRepository.findById(reqVO.getDoctorId());

            if (!userDoctor.isPresent()) {
                return BaseResponse.error("未查询到相关医生信息，请稍后重试！");
            }
            int allowNum = userDoctor.get().getAllowNum();
            int nowNum = userDoctor.get().getNowNum();
            if (nowNum == allowNum) {
                return BaseResponse.error("该医生已挂号人数已达上限，请刷新页面重新选择！");
            }


            PatientEntity patient = iPatientRepository.findByCardId(reqVO.getCardId());

            //患者当前门诊队列状态是待处理，验证第二次挂的是否为体检号
            List<RegisterEntity> registerTemp = iRegisterRepository.findAll(new Specification<RegisterEntity>() {
                @Override
                public Predicate toPredicate(Root<RegisterEntity> root, CriteriaQuery<?> query, CriteriaBuilder cb) {
                    List<Predicate> filter = new ArrayList<>();

                    filter.add(cb.equal(root.get("patient"), patient));
                    filter.add(cb.equal(root.get("registerStatus"), 1));
                    filter.add(cb.equal(root.get("treatmentStatus"), 0));
                    //当天
                    String nowDate = DateUtil.getCurrentDateSimpleToString();
                    filter.add(cb.between(root.get("createDatetime"), nowDate.concat(" 00:00:00"), nowDate.concat(" 23:59:59")));

                    return query.where(filter.toArray(new Predicate[filter.size()])).getRestriction();
                }
            });
            if (registerTemp != null && !registerTemp.isEmpty()) {

                if (registerTemp.size() != 1) {
                    return BaseResponse.error("挂号记录异常，请联系管理员！");
                }
                if (!"12".equals(reqVO.getDepartment())) {
                    return BaseResponse.error("门诊待处理状态，只允许再挂体检号！");
                }
            }


            //更新已挂号数量
            userDoctor.get().setNowNum(nowNum + 1);
            iUserRepository.saveAndFlush(userDoctor.get());

            //保存挂号记录
            RegisterEntity register = new RegisterEntity();
            register.setDepartment(reqVO.getDepartment());
            register.setDoctor(reqVO.getDoctor());
            register.setDoctorId(reqVO.getDoctorId());
            register.setOperatorName(user.getUsername());
            register.setOperatorEmail(user.getEmail());
            register.setPatient(patient);
            register.setPayType(reqVO.getPayType());
            register.setRegisterType(reqVO.getRegisterType());
            register.setTreatmentPrice(reqVO.getTreatmentPrice());
            register.setRegisterStatus(1);

            String registeredNum = "RE" + System.currentTimeMillis() + (int) (Math.random() * 900 + 100);
            register.setRegisteredNum(registeredNum);

            iRegisterRepository.saveAndFlush(register);

            //将患者加入门诊队列
            OutpatientQueueEntity outpatientQueue = new OutpatientQueueEntity();

            outpatientQueue.setPatient(patient);
            outpatientQueue.setRegister(register);
            outpatientQueue.setUser(userDoctor.get());
            outpatientQueue.setDescription(patient.getName() + '#' + userDoctor.get().getUsername());
            outpatientQueue.setOutpatientQueueStatus(HisConstants.QUEUE.NORMAL);

            iOutpatientQueueRepository.saveAndFlush(outpatientQueue);

            return BaseResponse.success(HisConstants.USER.SUCCESS);
        } catch (Exception e) {
            logger.error("保存挂号记录异常！", e);
            return BaseResponse.error("挂号异常，请刷新页面重试！");
        }
    }

    /**
     * 挂号记录查询
     *
     * @param reqVO
     * @return
     */
    @Override
    public PageRspBO<RegisterRecordRspVO> getRegisterRecord(RegisterRecordSearchReqVO reqVO) {
        Page<RegisterEntity> page = iRegisterRepository.findAll(new Specification<RegisterEntity>() {
            @Override
            public Predicate toPredicate(Root<RegisterEntity> root, CriteriaQuery<?> query, CriteriaBuilder cb) {
                List<Predicate> predicateList = new ArrayList<>();

                if (!StringUtils.isEmpty(reqVO.getDepartment())) {
                    predicateList.add(cb.equal(root.get("department"), reqVO.getDepartment()));
                }
                if (!StringUtils.isEmpty(reqVO.getRegisterType())) {
                    predicateList.add(cb.equal(root.get("registerType"), reqVO.getRegisterType()));
                }
                if (!StringUtils.isEmpty(reqVO.getStartTime())) {
                    predicateList.add(cb.greaterThanOrEqualTo(root.get("createDatetime"), reqVO.getStartTime()));
                }
                if (!StringUtils.isEmpty(reqVO.getEndTime())) {
                    predicateList.add(cb.lessThanOrEqualTo(root.get("createDatetime"), reqVO.getEndTime()));
                }

                //默认列表
                if (StringUtils.isEmpty(reqVO.getDepartment()) && StringUtils.isEmpty(reqVO.getRegisterType())
                        && StringUtils.isEmpty(reqVO.getStartTime()) && StringUtils.isEmpty(reqVO.getEndTime())) {
                    predicateList.add(cb.greaterThanOrEqualTo(root.get("createDatetime"), now().toString()));
                }

                query.where(predicateList.toArray(new Predicate[predicateList.size()]));
                return null;
            }
        }, PageRequest.of(reqVO.getPageNumber(), reqVO.getPageSize(), Sort.Direction.DESC, "createDatetime"));
        if (page == null) {
            return null;
        }
        List<RegisterEntity> registerList = page.getContent();
        List<RegisterRecordRspVO> registerRecordList = new ArrayList<>();
        registerList.forEach(register -> {
            RegisterRecordRspVO registerRecord = new RegisterRecordRspVO();
            registerRecord.setCardId(register.getPatient().getCardId());

            String departmentName = "";
            DepartmentEntity department = iDepartmentRepository.findByCode(Integer.parseInt(register.getDepartment()));
            if (department != null) {
                departmentName = department.getName();
            }
            registerRecord.setDepartment(departmentName);

            registerRecord.setRegisterType(register.getRegisterType());
            registerRecord.setName(register.getPatient().getName());
            registerRecord.setDoctor(register.getDoctor());
            registerRecord.setCreateDateTime(register.getCreateDatetime());
            registerRecord.setCreatePerson(register.getOperatorName());
            registerRecord.setCreatePersonEmail(register.getOperatorEmail());
            registerRecordList.add(registerRecord);
        });
        PageRspBO pageRspBO = new PageRspBO();
        pageRspBO.setTotal(page.getTotalElements());
        pageRspBO.setRows(registerRecordList);
        return pageRspBO;
    }

}
