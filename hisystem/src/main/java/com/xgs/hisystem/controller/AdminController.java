package com.xgs.hisystem.controller;

import com.xgs.hisystem.pojo.bo.BaseResponse;
import com.xgs.hisystem.pojo.bo.PageRspBO;
import com.xgs.hisystem.pojo.vo.*;
import com.xgs.hisystem.service.IAdminService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * @author xgs
 * @date 2019/4/3
 * @description:
 */
@RestController
@RequestMapping(value = "/admin")
@Api(tags = "管理员操作API")
public class AdminController {

    @Autowired
    private IAdminService iadminService;

    /**
     * 新建角色
     *
     * @param roleVO
     * @return
     * @RequestBody 接口将读到的内容（json数据）转换为java对象并绑定到Controller方法的参数上。
     * @validated 来校验数据，如果数据异常则会统一抛出异常，方便异常中心统一处理
     */
    @RequestMapping(value = "/createRole", method = RequestMethod.POST)
    public BaseResponse<String> createRole(@RequestBody @Validated RoleVO roleVO) {

        return iadminService.createRole(roleVO);

    }

    /**
     * 后台添加账户
     *
     * @param reqVO
     * @return
     */
    @RequestMapping(value = "/adduser", method = RequestMethod.POST)
    public BaseResponse<String> saveUserAndSendEmailTemp(@RequestBody @Validated UserRegisterReqVO reqVO) {

        return iadminService.saveUserAndSendEmailTemp(reqVO);
    }

    /**
     * 后台添加角色
     *
     * @param addRoleVO
     * @return
     */
    @RequestMapping(value = "/addRole", method = RequestMethod.POST)
    public BaseResponse<String> addRole(@RequestBody @Validated AddRoleVO addRoleVO) {

        return iadminService.addRole(addRoleVO);
    }


    /**
     * 获取审核角色
     *
     * @param
     * @return
     */
    @GetMapping(value = "/getRoleApply")
    public PageRspBO<applyRspVO> getRoleApply(BasePageReqVO reqVO) {

        return iadminService.getRoleApply(reqVO);

    }


    /**
     * 修改角色状态
     *
     * @param status
     * @param email
     */
    @PostMapping(value = "/changeRoleStatus")
    public void changeRoleStatus(@RequestParam String status, @RequestParam String email) {

        iadminService.changeRoleStatus(status, email);
    }

    /**
     * 公告
     *
     * @param reqVO
     * @return
     */
    @PostMapping(value = "/addAnnouncement")
    public BaseResponse<String> addAnnouncement(@RequestBody @Validated AnnouncementVO reqVO) {

        return iadminService.addAnnouncement(reqVO);
    }

    @GetMapping(value = "/getAnnouncement")
    public PageRspBO<AnnouncementVO> getAnnouncement(BasePageReqVO reqVO) {

        return iadminService.getAnnouncement(reqVO);
    }

    @PostMapping(value = "/changeAnnouncement")
    public BaseResponse<String> changeAnnouncement(@RequestBody @Validated AnnouncementVO announcementVO) {

        return iadminService.changeAnnouncement(announcementVO);
    }

    @PostMapping(value = "/deleteAnnouncement")
    public BaseResponse<String> deleteAnnouncement(@RequestParam String id) {

        return iadminService.deleteAnnouncement(id);
    }

    @PostMapping(value = "/showAnnouncement")
    public BaseResponse<String> showAnnouncement(@RequestParam String id) {

        return iadminService.showAnnouncement(id);
    }

    @PostMapping(value = "/hiddenAnnouncement")
    public BaseResponse<String> hiddenAnnouncement(@RequestParam String id) {

        return iadminService.hiddenAnnouncement(id);
    }

    @PostMapping(value = "/adddepartment")
    @ApiOperation(value = "添加科室", httpMethod = "POST", notes = "添加科室")
    @ApiImplicitParam(name = "reqVO",value = "添加科室", dataType = "AddDepartmentReqVO")
    public BaseResponse<String> addDepartment(@RequestBody @Validated AddDepartmentReqVO reqVO) {

        return iadminService.addDepartment(reqVO);
    }

    @PostMapping(value = "/getDepartment")
    @ApiOperation(value = "获取所有科室", httpMethod = "POST", notes = "获取所有科室")
    public List<GetDepartmentRspVO> getDepartment() {
        return iadminService.getDepartment();
    }
}
