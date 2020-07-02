package com.xgs.hisystem.pojo.entity;

import org.hibernate.annotations.DynamicInsert;
import org.hibernate.annotations.DynamicUpdate;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.ManyToMany;
import javax.persistence.Table;
import java.util.List;

/**
 * @author XGS
 * @date 2020/3/28
 * @desciption 角色表
 */

@Entity
@Table(name = "his_role")
@DynamicInsert(true)
@DynamicUpdate(true)
public class RoleEntity extends BaseEntity{

    @Column(name = "role")
    private String role;

    @Column(name = "role_value")
    private Integer roleValue;

    @Column(name = "description")
    private String description;

    @ManyToMany(mappedBy = "roleList")
    private List<UserEntity> userList;

    public String getRole() {
        return role;
    }

    public void setRole(String role) {
        this.role = role;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public List<UserEntity> getUserList() {
        return userList;
    }

    public void setUserList(List<UserEntity> userList) {
        this.userList = userList;
    }

    public Integer getRoleValue() {
        return roleValue;
    }

    public void setRoleValue(Integer roleValue) {
        this.roleValue = roleValue;
    }
}
