package com.xgs.hisystem.pojo.vo;

import javax.validation.constraints.Min;

/**
 * @author xgs
 * @date 2019-4-30
 * @description:
 */
public class AccountRoleVO {

    @Min(1)
    private String roleValue;

    public String getRoleValue() {
        return roleValue;
    }

    public void setRoleValue(String roleValue) {
        this.roleValue = roleValue;
    }
}
