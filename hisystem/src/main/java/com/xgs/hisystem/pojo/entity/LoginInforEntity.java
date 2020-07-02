package com.xgs.hisystem.pojo.entity;

import javax.persistence.*;

/**
 * @author xgs
 * @Description: 登录信息表
 * @date 2019/3/22
 */
@Entity
@Table(name = "his_login_infor")
public class LoginInforEntity extends BaseEntity {

    @Column(name = "login_address")
    private String loginAddress;

    @Column(name = "login_broswer")
    private String loginBroswer;

    @Column(name = "login_ip")
    private String loginIp;

    @ManyToOne(cascade = {CascadeType.MERGE, CascadeType.REFRESH}, optional = false)
    @JoinColumn(name = "user_id")
    private UserEntity user;

    @Column(name = "description")
    private String description;

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

    public UserEntity getUser() {
        return user;
    }

    public void setUser(UserEntity user) {
        this.user = user;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }
}
