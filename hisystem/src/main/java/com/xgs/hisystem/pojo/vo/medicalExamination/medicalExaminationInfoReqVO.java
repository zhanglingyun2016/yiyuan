package com.xgs.hisystem.pojo.vo.medicalExamination;

import javax.validation.constraints.NotBlank;

/**
 * @author xgs
 * @date 2019-5-18
 * @description:
 */
public class medicalExaminationInfoReqVO {

    @NotBlank(message = "请先读取就诊卡")
    private String cardId;

    @NotBlank(message = "请填写体温")
    private String bodyTemperature; //体温

    @NotBlank(message = "请填写脉搏")
    private String pulse;   //脉搏

    @NotBlank(message = "请填写心率")
    private String heartRate;  //心率

    @NotBlank(message = "请填写血压")
    private String bloodPressure;  //血压

    @NotBlank(message = "请填写检查费用")
    private String examinationCost;

    /**
     * 队列Id
     */
    private String queueId;

    /**
     * 处方号
     */
    private String prescriptionNum;

    public String getBodyTemperature() {
        return bodyTemperature;
    }

    public void setBodyTemperature(String bodyTemperature) {
        this.bodyTemperature = bodyTemperature;
    }

    public String getPulse() {
        return pulse;
    }

    public void setPulse(String pulse) {
        this.pulse = pulse;
    }

    public String getHeartRate() {
        return heartRate;
    }

    public void setHeartRate(String heartRate) {
        this.heartRate = heartRate;
    }

    public String getBloodPressure() {
        return bloodPressure;
    }

    public void setBloodPressure(String bloodPressure) {
        this.bloodPressure = bloodPressure;
    }

    public String getExaminationCost() {
        return examinationCost;
    }

    public void setExaminationCost(String examinationCost) {
        this.examinationCost = examinationCost;
    }

    public String getCardId() {
        return cardId;
    }

    public void setCardId(String cardId) {
        this.cardId = cardId;
    }

    public String getQueueId() {
        return queueId;
    }

    public void setQueueId(String queueId) {
        this.queueId = queueId;
    }

    public String getPrescriptionNum() {
        return prescriptionNum;
    }

    public void setPrescriptionNum(String prescriptionNum) {
        this.prescriptionNum = prescriptionNum;
    }
}
