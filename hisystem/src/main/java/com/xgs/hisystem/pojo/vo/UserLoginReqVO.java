package com.xgs.hisystem.pojo.vo;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;

/**
 * @author xgs
 * @Description:
 * @date 2019/3/26
 */
public class UserLoginReqVO {

    @Email(message = "邮箱格式错误")
    @NotBlank(message = "邮箱不能为空")
    private String email;

    @NotBlank(message = "密码不能为空")
    private String password;

    private String ip;

    private String broswer;

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getIp() {
        return ip;
    }

    public void setIp(String ip) {
        this.ip = ip;
    }

    public String getBroswer() {
        return broswer;
    }

    public void setBroswer(String broswer) {
        this.broswer = broswer;
    }
}
