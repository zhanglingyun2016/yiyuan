package com.xgs.hisystem.pojo.entity;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Table;

/**
 * @author XueGuiSheng
 * @date 2020/3/23
 * @description: 科室表
 */
@Entity
@Table(name = "his_department")
public class DepartmentEntity extends BaseEntity{

    @Column(name = "code")
    private Integer code;

    @Column(name = "address")
    private String address;

    @Column(name = "name")
    private String name;

    /*名称，地址拼音缩写*/
    @Column(name = "name_code")
    private String nameCode;

    /*0普通 1急诊*/
    @Column(name = "type",nullable = false)
    private int type;

    public Integer getCode() {
        return code;
    }

    public void setCode(Integer code) {
        this.code = code;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getNameCode() {
        return nameCode;
    }

    public void setNameCode(String nameCode) {
        this.nameCode = nameCode;
    }

    public int getType() {
        return type;
    }

    public void setType(int type) {
        this.type = type;
    }
}
