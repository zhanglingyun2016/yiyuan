package com.xgs.hisystem.pojo.vo.outpatient;

/**
 * @author XueGuiSheng
 * @date 2020/3/31
 * @description:
 */
public class GetAllOutpatientQueueRspVO {

    private String cardId;

    private String patientName;

    private String registerId;

    private int status;

    public String getRegisterId() {
        return registerId;
    }

    public void setRegisterId(String registerId) {
        this.registerId = registerId;
    }

    public String getCardId() {
        return cardId;
    }

    public void setCardId(String cardId) {
        this.cardId = cardId;
    }

    public String getPatientName() {
        return patientName;
    }

    public void setPatientName(String patientName) {
        this.patientName = patientName;
    }

    public int getStatus() {
        return status;
    }

    public void setStatus(int status) {
        this.status = status;
    }
}
