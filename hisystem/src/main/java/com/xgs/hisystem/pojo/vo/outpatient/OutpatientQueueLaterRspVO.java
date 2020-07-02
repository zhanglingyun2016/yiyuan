package com.xgs.hisystem.pojo.vo.outpatient;

/**
 * @author xgs
 * @date 2019-5-7
 * @description:
 */
public class OutpatientQueueLaterRspVO {

    private String cardId;

    private String patientName;

    private String registerId;

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
}
