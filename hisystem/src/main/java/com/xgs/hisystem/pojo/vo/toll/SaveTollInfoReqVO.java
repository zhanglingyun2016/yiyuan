package com.xgs.hisystem.pojo.vo.toll;

import javax.validation.constraints.NotBlank;

/**
 * @author xgs
 * @date 2019-5-15
 * @description:
 */
public class SaveTollInfoReqVO {

    @NotBlank(message = "请先读取就诊卡！")
    private String registerId;

    @NotBlank(message = "请先选择收费处方！")
    private String prescriptionNum;

    public String getRegisterId() {
        return registerId;
    }

    public void setRegisterId(String registerId) {
        this.registerId = registerId;
    }

    public String getPrescriptionNum() {
        return prescriptionNum;
    }

    public void setPrescriptionNum(String prescriptionNum) {
        this.prescriptionNum = prescriptionNum;
    }
}
