package com.xgs.hisystem.service;

import com.xgs.hisystem.pojo.bo.BaseResponse;
import com.xgs.hisystem.pojo.entity.PatientEntity;
import com.xgs.hisystem.pojo.vo.register.GetCardIdInforReqVO;

/**
 * @author XueGuiSheng
 * @date 2020/4/9
 * @description: 统一获取卡号
 */
public interface IGetPatientInfoService {

    BaseResponse<PatientEntity> getPatientInfo(GetCardIdInforReqVO reqVO);
}
