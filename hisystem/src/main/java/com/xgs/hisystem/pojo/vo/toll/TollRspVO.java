package com.xgs.hisystem.pojo.vo.toll;

/**
 * @author xgs
 * @date 2019-5-14
 * @description:
 */
public class TollRspVO {

    private String outpatientDate;

    private String registerId;

    private String department;

    private String doctorName;

    private String registerType;

    private String prescriptionNum;

    public String getOutpatientDate() {
        return outpatientDate;
    }

    public void setOutpatientDate(String outpatientDate) {
        this.outpatientDate = outpatientDate;
    }

    public String getRegisterId() {
        return registerId;
    }

    public void setRegisterId(String registerId) {
        this.registerId = registerId;
    }

    public String getDepartment() {
        return department;
    }

    public void setDepartment(String department) {
        this.department = department;
    }

    public String getDoctorName() {
        return doctorName;
    }

    public void setDoctorName(String doctorName) {
        this.doctorName = doctorName;
    }

    public String getRegisterType() {
        return registerType;
    }

    public void setRegisterType(String registerType) {
        this.registerType = registerType;
    }

    public String getPrescriptionNum() {
        return prescriptionNum;
    }

    public void setPrescriptionNum(String prescriptionNum) {
        this.prescriptionNum = prescriptionNum;
    }

}
