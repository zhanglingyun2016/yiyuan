package com.xgs.hisystem.pojo.entity;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Table;

/**
 * @author xgs
 * @Description: 用户-角色表
 * @date 2019/3/20
 */
@Entity
@Table(name = "his_user_role")
public class UserRoleEntity extends BaseEntity {

    @Column(name = "uid")
    private String uId;

    @Column(name = "role_id")
    private String roleId;

    @Column(name = "description")
    private String description;
    /**
     * 管理员审核状态 -1审核不通过 0等待审核 1通过
     **/
    @Column(name = "role_status")
    private Integer roleStatus;

    public String getuId() {
        return uId;
    }

    public void setuId(String uId) {
        this.uId = uId;
    }

    public String getRoleId() {
        return roleId;
    }

    public void setRoleId(String roleId) {
        this.roleId = roleId;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public Integer getRoleStatus() {
        return roleStatus;
    }

    public void setRoleStatus(Integer roleStatus) {
        this.roleStatus = roleStatus;
    }
}
