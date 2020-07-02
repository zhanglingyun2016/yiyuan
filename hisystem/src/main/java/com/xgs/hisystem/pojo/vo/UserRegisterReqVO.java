package com.xgs.hisystem.pojo.vo;

import org.hibernate.validator.constraints.Length;

import javax.servlet.http.HttpServletRequest;
import javax.validation.constraints.Email;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotBlank;

/**
 * @author xgs
 * @Description:
 * @date 2019/2/14
 */
public class UserRegisterReqVO {

    @Length(max = 50, message = "邮箱长度不超过50")
    @Email(message = "邮箱格式错误")
    @NotBlank(message = "邮箱不能为空")
    private String email;

    @Length(max = 20, message = "用户名长度不超过20")
    @NotBlank(message = "用户名不能为空")
    private String username;

    @Length(max = 20, message = "密码长度不超过20")
    @NotBlank(message = "密码不能为空")
    private String password;

    @NotBlank(message = "请选择角色")
    private String roleName;

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getRoleName() {
        return roleName;
    }

    public void setRoleName(String roleName) {
        this.roleName = roleName;
    }
}
