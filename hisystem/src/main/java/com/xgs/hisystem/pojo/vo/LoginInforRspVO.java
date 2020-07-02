package com.xgs.hisystem.pojo.vo;

/**
 * @author xgs
 * @date 2019/4/4
 * @description:
 */
public class LoginInforRspVO {

    private String loginAddress;

    private String loginBroswer;

    private String loginIp;

    private String createDatetime;

    public String getLoginAddress() {
        return loginAddress;
    }

    public void setLoginAddress(String loginAddress) {
        this.loginAddress = loginAddress;
    }

    public String getLoginBroswer() {
        return loginBroswer;
    }

    public void setLoginBroswer(String loginBroswer) {
        this.loginBroswer = loginBroswer;
    }

    public String getLoginIp() {
        return loginIp;
    }

    public void setLoginIp(String loginIp) {
        this.loginIp = loginIp;
    }

    public String getCreateDatetime() {
        return createDatetime;
    }

    public void setCreateDatetime(String createDatetime) {
        this.createDatetime = createDatetime;
    }
}
