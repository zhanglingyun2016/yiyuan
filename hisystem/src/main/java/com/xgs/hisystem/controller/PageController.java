package com.xgs.hisystem.controller;

import com.xgs.hisystem.config.ServerConfig;
import com.xgs.hisystem.pojo.bo.BaseResponse;
import com.xgs.hisystem.pojo.vo.AnnouncementVO;
import com.xgs.hisystem.pojo.vo.applyRspVO;
import com.xgs.hisystem.service.IAdminService;
import com.xgs.hisystem.service.IUserService;
import io.swagger.annotations.Api;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import javax.servlet.http.HttpServletRequest;
import java.text.ParseException;
import java.util.List;

/**
 * @author xgs
 * @date 2019/4/3
 * @description:
 */
@Controller
@Api(tags = "页面跳转")
public class PageController {

    @Autowired
    private IUserService iUserService;
    @Autowired
    private IAdminService iAdminService;
    @Autowired
    private ServerConfig serverConfig;

    @GetMapping(value = "/")
    public String login() {
        return "login";
    }

    /**
     * 主页
     *
     * @param model
     * @return
     */
    @GetMapping(value = "/main")
    public String main(Model model) {
        return "main";
    }

    /**
     * 邮件发送成功
     *
     * @param request
     * @param model
     * @return
     */
    @GetMapping(value = "/fmail")
    public String fmail(HttpServletRequest request, Model model) {
        String email = request.getParameter("email");
        model.addAttribute("email", email);
        model.addAttribute("url", serverConfig.getUrl());
        return "email/fmail";
    }

    /**
     * 激活用户状态
     *
     * @param email
     * @param validateCode
     * @return
     * @throws ParseException
     */
    @RequestMapping(value = "/activation", method = RequestMethod.GET)
    public String activation(String email, String validateCode, Model model) throws ParseException {

        BaseResponse baseResponse = iUserService.activation(email, validateCode);

        model.addAttribute("url", serverConfig.getUrl());

        if (baseResponse.getStatus() == 1) {
            return "email/dmail";
        } else {
            return "error";
        }
    }


    @GetMapping(value = "/accountset")
    public String accountSet() {
        return "accountset";
    }


    @GetMapping("/toApply")
    public String toApply() {
        return "/admin/roleApply";
    }

    @GetMapping(value = "/register")
    public String getUserID() {
        return "register/register";
    }

    /**
     * 导航栏通知数量显示，角色审核
     *
     * @param model
     * @return
     */
    @GetMapping(value = "/getRoleNotice")
    public String getRoleNotice(Model model) {

        List<applyRspVO> applyRspList = iAdminService.getRoleNotice();

        model.addAttribute("applyRspList", applyRspList);
        return "common/common_head::notice";

    }

    @GetMapping(value = "/toUserinfo")
    public String toUserinfo() {
        return "userInfo";
    }


    @GetMapping(value = "/announcement")
    public String announcement() {
        return "admin/announcement";
    }

    /**
     * 首页公告显示
     *
     * @param model
     * @return
     */
    @GetMapping(value = "/annDisplay")
    public String annDisplay(Model model) {

        List<AnnouncementVO> annList = iUserService.annDisplay();
        model.addAttribute("annList", annList);

        return "main::ann";
    }

    /**
     * 获取当前用户所有角色
     *
     * @param model
     * @return
     */
    @GetMapping(value = "/getAccountRole")
    public String getAccountRole(Model model) {

        List<String> accountRoleList = iUserService.getAccountRole();
        model.addAttribute("accountRole", accountRoleList);

        return "accountset::accountRole";
    }

    @GetMapping(value = "/registerRecord")
    public String registerRecord() {
        return "register/registerRecord";
    }

    @GetMapping(value = "/outpatient")
    public String outpatient() {
        return "outpatient/outpatient";
    }


    @GetMapping(value = "/storageManage")
    public String storageManage() {

        return "drugStore/storageManage";
    }

    @GetMapping(value = "/outpatientToll")
    public String outpatientToll() {

        return "toll/outpatientToll";
    }

    @GetMapping(value = "/examinationToll")
    public String examinationToll() {

        return "toll/examinationToll";
    }

    @GetMapping(value = "/takingdrug")
    public String takingdrug() {

        return "takingdrug/takingdrug";
    }


    @GetMapping(value = "/medicalExamination")
    public String medicalExamination() {

        return "medicalExamination/medicalExamination";
    }

    @GetMapping(value = "/drugManage")
    public String drugManage() {

        return "drugStore/drugManage";
    }


    @GetMapping(value = "/medicalRecord")
    public String medicalRecord() {

        return "outpatient/medicalRecord";
    }
}

