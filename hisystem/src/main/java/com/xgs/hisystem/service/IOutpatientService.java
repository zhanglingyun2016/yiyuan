package com.xgs.hisystem.service;

import com.xgs.hisystem.pojo.bo.BaseResponse;
import com.xgs.hisystem.pojo.vo.outpatient.*;
import com.xgs.hisystem.pojo.vo.register.GetCardIdInforReqVO;

import java.util.List;

/**
 * @author xgs
 * @date 2019-5-6
 * @description:
 */
public interface IOutpatientService {

    PatientInforRspVO getCardIdInfor(GetCardIdInforReqVO reqVO) throws Exception;

    BaseResponse<String> changePatientInfor(OtherPatientInforReqVO reqVO);

    BaseResponse<String> processLaterMedicalRecord(MedicalRecordReqVO reqVO);

    PatientInforRspVO restorePatientInfor(String registerId) throws Exception;

    List<String> getAllDrug();

    DrugRspVO getDrugInfor(String drug);

    BaseResponse<String> addMedicalRecord(MedicalRecordReqVO reqVO);

    medicalExaminationInfoRspVO getMedicalExamination(String prescriptionNum);

    List<GetAllOutpatientQueueRspVO> getAllOutpatientQueue();

}
