package com.xgs.hisystem.controller;

import com.xgs.hisystem.pojo.bo.BaseResponse;
import com.xgs.hisystem.pojo.vo.medicalExamination.medicalExaminationInfoReqVO;
import com.xgs.hisystem.pojo.vo.medicalExamination.PatientInforRspVO;
import com.xgs.hisystem.pojo.vo.register.GetCardIdInforReqVO;
import com.xgs.hisystem.service.IMedicalExaminationService;
import io.swagger.annotations.Api;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * @author xgs
 * @date 2019-5-18
 * @description:
 */
@RestController
@RequestMapping(value = "medicalExamination")
@Api(tags = "体检管理API")
public class MedicalExaminationController {

    @Autowired
    private IMedicalExaminationService iMedicalExaminationService;

    @PostMapping(value = "/getCardIdInfor")
    public PatientInforRspVO getCardIdInfor(@RequestBody GetCardIdInforReqVO reqVO) throws Exception {

        return iMedicalExaminationService.getCardIdInfor(reqVO);
    }

    @PostMapping(value = "/saveMedicalExaminationInfo")
    public BaseResponse<String> saveMedicalExaminationInfo(@RequestBody @Validated medicalExaminationInfoReqVO reqVO) {

        return iMedicalExaminationService.saveMedicalExaminationInfo(reqVO);
    }
}
