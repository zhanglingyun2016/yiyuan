package com.xgs.hisystem.pojo.entity;

import javax.persistence.*;

/**
 * @author xgs
 * @date 2019-5-6
 * @description: 就诊队列表
 */
@Entity
@Table(name = "his_outpatient_queue",indexes = {@Index(name = "his_outpatient_queue_index",columnList = "register_id,patient_id")})
public class OutpatientQueueEntity extends BaseEntity {

    @ManyToOne(cascade = {CascadeType.MERGE, CascadeType.REFRESH}, optional = false)
    @JoinColumn(name = "user_id")
    private UserEntity user;

    @OneToOne
    @JoinColumn(name = "register_id", referencedColumnName = "id")
    private RegisterEntity register;

    @OneToOne
    @JoinColumn(name = "patient_id", referencedColumnName = "id")
    private PatientEntity patient;

    @Column(name = "description", nullable = true, length = 50)
    private String description;

    @Column(name = "outpatientQueueStatus", nullable = true, length = 2)
    private int outpatientQueueStatus; //   1表示正常状态，-1表示稍后处理，0过期

    public UserEntity getUser() {
        return user;
    }

    public void setUser(UserEntity user) {
        this.user = user;
    }

    public RegisterEntity getRegister() {
        return register;
    }

    public void setRegister(RegisterEntity register) {
        this.register = register;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public int getOutpatientQueueStatus() {
        return outpatientQueueStatus;
    }

    public void setOutpatientQueueStatus(int outpatientQueueStatus) {
        this.outpatientQueueStatus = outpatientQueueStatus;
    }

    public PatientEntity getPatient() {
        return patient;
    }

    public void setPatient(PatientEntity patient) {
        this.patient = patient;
    }
}
