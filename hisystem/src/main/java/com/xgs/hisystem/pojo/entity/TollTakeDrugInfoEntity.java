package com.xgs.hisystem.pojo.entity;

import org.hibernate.annotations.DynamicInsert;
import org.hibernate.annotations.DynamicUpdate;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Table;

/**
 * @author XGS
 * @date 2020/3/10
 * @description: 划价收费—拿药信息表
 */
@Entity
@Table(name = "his_toll_takedrug")
@DynamicInsert(true)
@DynamicUpdate(true)
public class TollTakeDrugInfoEntity extends BaseEntity {

    @Column(name = "patientId")
    private String patientId;
    /**
     * 处方号
     */
    @Column(name = "prescriptionNum", nullable = false, length = 255)
    private String prescriptionNum;

    /**
     * 收费操作员
     */
    @Column(name = "tollOperator", nullable = true, length = 255)
    private String tollOperator;
    /**
     * 收费时间
     */
    @Column(name = "tollDateTime", nullable = true, length = 50)
    private String tollDateTime;
    /**
     * 取药状态：0未取 1已取
     */
    @Column(name = "takingDrugStatus", nullable = false, length = 20)
    private int takingDrugStatus;
    /**
     * 药房操作员
     */
    @Column(name = "takingDrugOperator", nullable = true, length = 255)
    private String takingDrugOperator;
    /**
     * 拿药时间
     */
    @Column(name = "takingDrugDateTime", nullable = true, length = 50)
    private String takingDrugDateTime;

    public String getPatientId() {
        return patientId;
    }

    public void setPatientId(String patientId) {
        this.patientId = patientId;
    }

    public String getPrescriptionNum() {
        return prescriptionNum;
    }

    public void setPrescriptionNum(String prescriptionNum) {
        this.prescriptionNum = prescriptionNum;
    }

    public int getTakingDrugStatus() {
        return takingDrugStatus;
    }

    public void setTakingDrugStatus(int takingDrugStatus) {
        this.takingDrugStatus = takingDrugStatus;
    }

    public String getTollOperator() {
        return tollOperator;
    }

    public void setTollOperator(String tollOperator) {
        this.tollOperator = tollOperator;
    }

    public String getTollDateTime() {
        return tollDateTime;
    }

    public void setTollDateTime(String tollDateTime) {
        this.tollDateTime = tollDateTime;
    }

    public String getTakingDrugOperator() {
        return takingDrugOperator;
    }

    public void setTakingDrugOperator(String takingDrugOperator) {
        this.takingDrugOperator = takingDrugOperator;
    }

    public String getTakingDrugDateTime() {
        return takingDrugDateTime;
    }

    public void setTakingDrugDateTime(String takingDrugDateTime) {
        this.takingDrugDateTime = takingDrugDateTime;
    }
}
