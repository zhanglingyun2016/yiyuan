package com.xgs.hisystem.pojo.vo.outpatient;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

/**
 * @author xgs
 * @date 2019-5-10
 * @description:
 */
public class MedicalRecordReqVO {

    @NotBlank(message = "请先读取就诊卡！")
    private String cardId;

    @NotBlank(message = "请填写主诉！")
    private String conditionDescription;

    @NotBlank(message = "请先读取就诊卡！")
    private String prescriptionNum; //处方号

    @NotBlank(message = "请选择处方药！")
    private String prescription;  //处方

    @NotBlank(message = "请填写医嘱！")
    private String medicalOrder; //医嘱

    @NotNull(message = "请选择处方药！")
    private double drugCost; //药物总费用

    @NotBlank(message = "请填写初步诊断！")
    private String diagnosisResult;  //初步诊断结果

    /**
     * 队列Id
     */
    private String queueId;

    public String getCardId() {
        return cardId;
    }

    public void setCardId(String cardId) {
        this.cardId = cardId;
    }

    public String getConditionDescription() {
        return conditionDescription;
    }

    public void setConditionDescription(String conditionDescription) {
        this.conditionDescription = conditionDescription;
    }

    public String getPrescriptionNum() {
        return prescriptionNum;
    }

    public void setPrescriptionNum(String prescriptionNum) {
        this.prescriptionNum = prescriptionNum;
    }

    public String getPrescription() {
        return prescription;
    }

    public void setPrescription(String prescription) {
        this.prescription = prescription;
    }

    public String getMedicalOrder() {
        return medicalOrder;
    }

    public void setMedicalOrder(String medicalOrder) {
        this.medicalOrder = medicalOrder;
    }

    public double getDrugCost() {
        return drugCost;
    }

    public void setDrugCost(double drugCost) {
        this.drugCost = drugCost;
    }

    public String getDiagnosisResult() {
        return diagnosisResult;
    }

    public void setDiagnosisResult(String diagnosisResult) {
        this.diagnosisResult = diagnosisResult;
    }


    public String getQueueId() {
        return queueId;
    }

    public void setQueueId(String queueId) {
        this.queueId = queueId;
    }
}
