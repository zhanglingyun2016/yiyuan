package com.xgs.hisystem.pojo.vo.outpatient;

/**
 * @author xgs
 * @date 2019-5-18
 * @description:
 */
public class medicalExaminationInfoRspVO {

    private String bodyTemperature; //体温

    private String pulse;   //脉搏

    private String heartRate;  //心率

    private String bloodPressure;  //血压

    private String message;

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

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
}
