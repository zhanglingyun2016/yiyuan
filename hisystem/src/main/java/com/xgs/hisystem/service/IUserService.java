package com.xgs.hisystem.service;

import com.xgs.hisystem.pojo.bo.BaseResponse;
import com.xgs.hisystem.pojo.bo.PageRspBO;
import com.xgs.hisystem.pojo.vo.*;

import java.text.ParseException;
import java.util.List;

public interface IUserService {

    BaseResponse<String> doLogin(UserLoginReqVO reqVO);

    BaseResponse<String> saveUserAndSendEmail(UserRegisterReqVO reqVO);

    BaseResponse<String> activation(String email, String validateCode) throws ParseException;

    PageRspBO<LoginInforRspVO> getLoginfor(BasePageReqVO reqVO);

    BaseResponse<String> changePassword(ChangePasswordReqVO reqVO);

    List<UserInfoVO> getUserInfo();

    BaseResponse<String> changeUserInfo(UserInfoVO reqVO);

    List<AnnouncementVO> annDisplay();

    AnnRspVO getAnnContent(String id);

    List<String> getAccountRole();

    BaseResponse<String> addAnotherRole(AccountRoleVO reqVO);

    List<GetAllRoleRspVO> getAllRole();
}
