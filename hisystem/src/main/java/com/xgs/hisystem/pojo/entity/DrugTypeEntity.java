package com.xgs.hisystem.pojo.entity;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Table;

/**
 * @author xgs
 * @date 2019-5-23
 * @description: 药物类型：注射剂、片剂、胶囊、、、
 */
@Entity
@Table(name = "his_drugType")
public class DrugTypeEntity extends BaseEntity {

    @Column(name = "drugType", nullable = false, length = 50)
    private String drugType;

    public String getDrugType() {
        return drugType;
    }

    public void setDrugType(String drugType) {
        this.drugType = drugType;
    }
}
