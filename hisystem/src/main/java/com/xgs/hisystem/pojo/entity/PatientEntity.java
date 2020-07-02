package com.xgs.hisystem.pojo.entity;

import javax.persistence.*;
import java.util.List;

/**
 * @author xgs
 * @date 2019/4/22
 * @description: 患者信息表
 */
@Entity
@Table(name = "his_patient",indexes = {@Index(name = "his_patient_index",columnList = "cardId")})
public class PatientEntity extends BaseEntity {

    @Column(name = "cardId", nullable = false, length = 20)
    private String cardId;

    @Column(name = "name", nullable = false, length = 20)
    private String name;

    @Column(name = "sex", nullable = false, length = 4)
    private String sex;

    @Column(name = "birthday", nullable = false, length = 20)
    private String birthday;

    @Column(name = "nationality", nullable = false, length = 4)
    private String nationality;

    @Column(name = "address", nullable = false, length = 20)
    private String address;

    @Column(name = "idCard", nullable = false, length = 20)
    private String idCard;

    @Column(name = "telphone", nullable = true, length = 20)
    private String telphone;

    @Column(name = "career", nullable = true, length = 20)
    private String career;  //职业

    @Column(name = "maritalStatus", nullable = true, length = 10)
    private String maritalStatus;  //婚姻状况

    @Column(name = "personalHistory", nullable = true, length = 255)
    private String personalHistory;  //个人史

    @Column(name = "pastHistory", nullable = true, length = 255)
    private String pastHistory;  //既往史

    @Column(name = "familyHistory", nullable = true, length = 255)
    private String familyHistory;  //家族史

    @OneToMany(mappedBy = "patient", cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    private List<RegisterEntity> registerList;  //挂号信息

    public String getCardId() {
        return cardId;
    }

    public void setCardId(String cardId) {
        this.cardId = cardId;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
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

    public String getNationality() {
        return nationality;
    }

    public void setNationality(String nationality) {
        this.nationality = nationality;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getIdCard() {
        return idCard;
    }

    public void setIdCard(String idCard) {
        this.idCard = idCard;
    }

    public String getTelphone() {
        return telphone;
    }

    public void setTelphone(String telphone) {
        this.telphone = telphone;
    }

    public List<RegisterEntity> getRegisterList() {
        return registerList;
    }

    public void setRegisterList(List<RegisterEntity> registerList) {
        this.registerList = registerList;
    }

    public String getCareer() {
        return career;
    }

    public void setCareer(String career) {
        this.career = career;
    }

    public String getMaritalStatus() {
        return maritalStatus;
    }

    public void setMaritalStatus(String maritalStatus) {
        this.maritalStatus = maritalStatus;
    }

    public String getPersonalHistory() {
        return personalHistory;
    }

    public void setPersonalHistory(String personalHistory) {
        this.personalHistory = personalHistory;
    }

    public String getPastHistory() {
        return pastHistory;
    }

    public void setPastHistory(String pastHistory) {
        this.pastHistory = pastHistory;
    }

    public String getFamilyHistory() {
        return familyHistory;
    }

    public void setFamilyHistory(String familyHistory) {
        this.familyHistory = familyHistory;
    }
}
