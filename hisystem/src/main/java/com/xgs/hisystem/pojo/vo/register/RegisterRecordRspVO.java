package com.xgs.hisystem.pojo.vo.register;

/**
 * @author xgs
 * @date 2019-5-2
 * @description:
 */
public class RegisterRecordRspVO {

    private String cardId;

    private String department;

    private String name;

    private String doctor;

    private String registerType;

    private String createDateTime;

    private String createPerson;

    private String createPersonEmail;

    public String getCardId() {
        return cardId;
    }

    public void setCardId(String cardId) {
        this.cardId = cardId;
    }

    public String getDepartment() {
        return department;
    }

    public void setDepartment(String department) {
        this.department = department;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDoctor() {
        return doctor;
    }

    public void setDoctor(String doctor) {
        this.doctor = doctor;
    }

    public String getRegisterType() {
        return registerType;
    }

    public void setRegisterType(String registerType) {
        this.registerType = registerType;
    }

    public String getCreateDateTime() {
        return createDateTime;
    }

    public void setCreateDateTime(String createDateTime) {
        this.createDateTime = createDateTime;
    }

    public String getCreatePerson() {
        return createPerson;
    }

    public void setCreatePerson(String createPerson) {
        this.createPerson = createPerson;
    }

    public String getCreatePersonEmail() {
        return createPersonEmail;
    }

    public void setCreatePersonEmail(String createPersonEmail) {
        this.createPersonEmail = createPersonEmail;
    }
}
