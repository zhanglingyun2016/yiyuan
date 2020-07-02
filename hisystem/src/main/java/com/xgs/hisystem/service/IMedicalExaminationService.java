package com.xgs.hisystem.service;

import com.xgs.hisystem.pojo.bo.BaseResponse;
import com.xgs.hisystem.pojo.vo.medicalExamination.medicalExaminationInfoReqVO;
import com.xgs.hisystem.pojo.vo.medicalExamination.PatientInforRspVO;
import com.xgs.hisystem.pojo.vo.register.GetCardIdInforReqVO;

/**
 * @author xgs
 * @date 2019-5-18
 * @description:
 */
public interface IMedicalExaminationService {

    PatientInforRspVO getCardIdInfor(GetCardIdInforReqVO reqVO) throws Exception;

    BaseResponse<String> saveMedicalExaminationInfo(medicalExaminationInfoReqVO reqVO);
}
