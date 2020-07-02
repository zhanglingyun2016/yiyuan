package com.xgs.hisystem.pojo.vo;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

/**
 * @author xgs
 * @Description:
 * @date 2019/3/20
 */
public class RoleVO {

    @NotBlank(message = "角色名不能为空")
    private String rolename;

    @NotNull(message = "角色编码不能为空")
    private Integer roleValue;

    @NotBlank(message = "描述不能为空")
    private String desciption;

    public String getRolename() {
        return rolename;
    }

    public void setRolename(String rolename) {
        this.rolename = rolename;
    }

    public Integer getRoleValue() {
        return roleValue;
    }

    public void setRoleValue(Integer roleValue) {
        this.roleValue = roleValue;
    }

    public String getDesciption() {
        return desciption;
    }

    public void setDesciption(String desciption) {
        this.desciption = desciption;
    }
}
