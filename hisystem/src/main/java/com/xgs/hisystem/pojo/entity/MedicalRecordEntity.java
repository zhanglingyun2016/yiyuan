package com.xgs.hisystem.pojo.entity;

import javax.persistence.*;

/**
 * @author xgs
 * @date 2019-5-8
 * @description: 就诊记录表
 */
@Entity
@Table(name = "his_medical_record")
public class MedicalRecordEntity extends BaseEntity {

    @Column(name = "conditionDescription", nullable = true, length = 255)
    private String conditionDescription;  //主诉

    @Column(name = "prescription", nullable = true, length = 500)
    private String prescription;  //处方

    @OneToOne
    @JoinColumn(name = "registerId", referencedColumnName = "id")
    private RegisterEntity register;

    @Column(name = "prescriptionNum", nullable = false, length = 255)
    private String prescriptionNum;  //处方号

    @Column(name = "drugCost", nullable = true, length = 50)
    private double drugCost; //药物总费用

    @Column(name = "diagnosisResult", nullable = true, length = 255)
    private String diagnosisResult;  //初步诊断结果

    @Column(name = "medicalOrder", nullable = true, length = 255)
    private String medicalOrder; //医嘱

    public String getConditionDescription() {
        return conditionDescription;
    }

    public void setConditionDescription(String conditionDescription) {
        this.conditionDescription = conditionDescription;
    }

    public RegisterEntity getRegister() {
        return register;
    }

    public void setRegister(RegisterEntity register) {
        this.register = register;
    }

    public String getPrescriptionNum() {
        return prescriptionNum;
    }

    public void setPrescriptionNum(String prescriptionNum) {
        this.prescriptionNum = prescriptionNum;
    }

    public double getDrugCost() {
        return drugCost;
    }

    public void setDrugCost(double drugCost) {
        this.drugCost = drugCost;
    }

    public String getPrescription() {
        return prescription;
    }

    public void setPrescription(String prescription) {
        this.prescription = prescription;
    }

    public String getDiagnosisResult() {
        return diagnosisResult;
    }

    public void setDiagnosisResult(String diagnosisResult) {
        this.diagnosisResult = diagnosisResult;
    }

    public String getMedicalOrder() {
        return medicalOrder;
    }

    public void setMedicalOrder(String medicalOrder) {
        this.medicalOrder = medicalOrder;
    }
}
