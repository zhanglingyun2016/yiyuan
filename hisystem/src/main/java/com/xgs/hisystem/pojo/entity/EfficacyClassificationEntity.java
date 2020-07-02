package com.xgs.hisystem.pojo.entity;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Table;

/**
 * @author xgs
 * @date 2019-5-23
 * @description: 药物功效分类表
 */
@Entity
@Table(name = "his_efficacyClassification")
public class EfficacyClassificationEntity extends BaseEntity {

    @Column(name = "efficacyClassification", nullable = false, length = 50)
    private String efficacyClassification;

    public String getEfficacyClassification() {
        return efficacyClassification;
    }

    public void setEfficacyClassification(String efficacyClassification) {
        this.efficacyClassification = efficacyClassification;
    }
}
