package com.xgs.hisystem.pojo.vo.register;

import javax.validation.constraints.NotBlank;

/**
 * @author xgs
 * @date 2019/4/27
 * @description:
 */
public class PatientInforReqVO {

    @NotBlank(message = "就诊卡号不能为空！")
    private String cardId;

    @NotBlank(message = "住址不能为空！")
    private String address;

    @NotBlank(message = "姓名不能为空！")
    private String name;

    @NotBlank(message = "民族不能为空！")
    private String nationality;

    @NotBlank(message = "身份证号不能为空！")
    private String idCard;

    @NotBlank(message = "性别不能为空！")
    private String sex;

    @NotBlank(message = "出生日期不能为空！")
    private String birthday;

    private String telphone;

    public String getCardId() {
        return cardId;
    }

    public void setCardId(String cardId) {
        this.cardId = cardId;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getNationality() {
        return nationality;
    }

    public void setNationality(String nationality) {
        this.nationality = nationality;
    }

    public String getIdCard() {
        return idCard;
    }

    public void setIdCard(String idCard) {
        this.idCard = idCard;
    }

    public String getSex() {
        return sex;
    }

    public void setSex(String sex) {
        this.sex = sex;
    }

    public String getBirthday() {
        return birthday;
    }

    public void setBirthday(String birthday) {
        this.birthday = birthday;
    }

    public String getTelphone() {
        return telphone;
    }

    public void setTelphone(String telphone) {
        this.telphone = telphone;
    }
}
