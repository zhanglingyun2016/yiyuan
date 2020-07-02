package com.xgs.hisystem.controller;

import com.xgs.hisystem.pojo.bo.BaseResponse;
import com.xgs.hisystem.pojo.bo.PageRspBO;
import com.xgs.hisystem.pojo.vo.*;
import com.xgs.hisystem.service.IUserService;
import io.swagger.annotations.Api;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.ui.Model;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping(value = "/user")
@Api(tags = "用户管理API")
public class UserController {

    @Autowired
    private IUserService iUserService;


    /**
     * 登录验证
     *
     * @param reqVO
     * @param model
     * @return
     */
    @RequestMapping(value = "/dologin", method = RequestMethod.POST)
    public BaseResponse<String> doLogin(@RequestBody @Validated UserLoginReqVO reqVO, Model model) {

        return iUserService.doLogin(reqVO);
    }

    /**
     * 保存用户注册信息，向用户发送激活链接
     *
     * @param reqVO
     * @return
     */
    @RequestMapping(value = "/doregister", method = RequestMethod.POST)
    public BaseResponse<String> registered(@RequestBody @Validated UserRegisterReqVO reqVO, Model model) {

        return iUserService.saveUserAndSendEmail(reqVO);
    }


    /**
     * 获取登录日志
     *
     * @param reqVO
     * @return
     */
    @RequestMapping(value = "/getLoginfor",method = RequestMethod.GET)
    public PageRspBO<LoginInforRspVO> getLoginfor(BasePageReqVO reqVO) {

        return iUserService.getLoginfor(reqVO);
    }

    /**
     * 修改密码
     *
     * @param reqVO
     * @return
     */
    @PostMapping(value = "/changePassword")
    public BaseResponse<String> changePassword(@RequestBody @Validated ChangePasswordReqVO reqVO) {

        return iUserService.changePassword(reqVO);
    }

    /**
     * 个人资料设置
     *
     * @return
     */
    @PostMapping(value = "/getUserInfo")
    public List<UserInfoVO> getUserInfo() {

        return iUserService.getUserInfo();
    }

    @PostMapping(value = "/changeUserInfo")
    public BaseResponse<String> changeUserInfo(@RequestBody @Validated UserInfoVO reqVO) {

        return  iUserService.changeUserInfo(reqVO);
    }

    @PostMapping(value = "/getAnnContent")
    public AnnRspVO getAnnContent(@RequestParam String id) {

        return iUserService.getAnnContent(id);
    }

    @PostMapping(value = "/addAnotherRole")
    public BaseResponse<String> addAnotherRole(@RequestBody @Validated AccountRoleVO reqVO) {

        return iUserService.addAnotherRole(reqVO);
    }


    /**
     * 获取所有角色
     * @param
     * @return
     */
    @PostMapping(value = "/getAllRole")
    public List<GetAllRoleRspVO> getAllRole() {
        return iUserService.getAllRole();
    }

}
