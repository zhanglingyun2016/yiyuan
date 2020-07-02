package com.xgs.hisystem.service;

import com.xgs.hisystem.pojo.bo.BaseResponse;
import com.xgs.hisystem.pojo.vo.register.GetCardIdInforReqVO;
import com.xgs.hisystem.pojo.vo.toll.*;

import java.util.List;

/**
 * @author xgs
 * @date 2019-5-14
 * @description:
 */
public interface ITollService {

    cardRspVO getCardIdInfor();

    List<TollRspVO> getAllMedicalRecord(String cardId, String tollStatus);

    TollMedicalRecordRspVO getMedicalRecord(String cardId, String registerId) throws Exception;

    BaseResponse<String> saveTollInfo(SaveTollInfoReqVO reqVO);

    BaseResponse<GetExaminationTollInfoRspVO> getExaminationTollInfo(GetCardIdInforReqVO reqVO);

    BaseResponse<String> saveExaminationTollInfo(String registerId);
}