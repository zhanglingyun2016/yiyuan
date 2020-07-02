package com.xgs.hisystem.pojo.vo.register;

import com.xgs.hisystem.pojo.vo.BasePageReqVO;

/**
 * @author xgs
 * @date 2019-5-2
 * @description:
 */
public class RegisterRecordSearchReqVO extends BasePageReqVO {

    private String department;

    private String registerType;

    private String startTime;

    private String endTime;

    public String getStartTime() {
        return startTime;
    }

    public void setStartTime(String startTime) {
        this.startTime = startTime;
    }

    public String getEndTime() {
        return endTime;
    }

    public void setEndTime(String endTime) {
        this.endTime = endTime;
    }

    public String getDepartment() {
        return department;
    }

    public void setDepartment(String department) {
        this.department = department;
    }

    public String getRegisterType() {
        return registerType;
    }

    public void setRegisterType(String registerType) {
        this.registerType = registerType;
    }
}
