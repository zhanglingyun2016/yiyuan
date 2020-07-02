package com.xgs.hisystem.service.impl;

import com.xgs.hisystem.config.HisConstants;
import com.xgs.hisystem.pojo.bo.BaseResponse;
import com.xgs.hisystem.pojo.entity.*;
import com.xgs.hisystem.pojo.vo.takingdrug.MedicalRecordRspVO;
import com.xgs.hisystem.repository.IMedicalExaminationRepository;
import com.xgs.hisystem.repository.IMedicalRecordRepository;
import com.xgs.hisystem.repository.ITollTakeDrugInfoRepository;
import com.xgs.hisystem.service.ITakingDrugService;
import com.xgs.hisystem.util.DateUtil;
import org.apache.shiro.SecurityUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

/**
 * @author xgs
 * @date 2019-5-15
 * @description:
 */
@Service
public class TakingDrugServiceImpl implements ITakingDrugService {

    @Autowired
    private IMedicalRecordRepository iMedicalRecordRepository;
    @Autowired
    private IMedicalExaminationRepository iMedicalExaminationRepository;
    @Autowired
    private ITollTakeDrugInfoRepository iTollTakeDrugInfoRepository;

    private static final Logger logger = LoggerFactory.getLogger(TakingDrugServiceImpl.class);

    @Override
    public MedicalRecordRspVO getMedicalRecord(String prescriptionNum) throws Exception {

        MedicalRecordEntity medicalRecord = iMedicalRecordRepository.findByPrescriptionNum(prescriptionNum);
        MedicalRecordRspVO recordRspVO = new MedicalRecordRspVO();

        if (StringUtils.isEmpty(medicalRecord)) {
            recordRspVO.setMessage("该处方号未查询到任何信息！");
            return recordRspVO;
        }
        TollTakeDrugInfoEntity tollTakeDrugInfo = iTollTakeDrugInfoRepository.findByPrescriptionNumAndTakingDrugStatus(medicalRecord.getPrescriptionNum(), 0);

        if (tollTakeDrugInfo == null) {
            recordRspVO.setMessage("该处方未查询到最新划价收费信息！");
            return recordRspVO;
        }
        RegisterEntity register = medicalRecord.getRegister();
        PatientEntity patient = medicalRecord.getRegister().getPatient();

        recordRspVO.setAge(DateUtil.getAge(patient.getBirthday()));
        recordRspVO.setCreateDate(DateUtil.DateTimeToDate(medicalRecord.getCreateDatetime()));
        recordRspVO.setDiagnosisResult(medicalRecord.getDiagnosisResult());
        recordRspVO.setDrugCost(medicalRecord.getDrugCost());
        recordRspVO.setMedicalOrder(medicalRecord.getMedicalOrder());
        recordRspVO.setName(patient.getName());
        recordRspVO.setNationality(patient.getNationality());
        recordRspVO.setPrescription(medicalRecord.getPrescription());
        recordRspVO.setSex(patient.getSex());
        recordRspVO.setNowDate(DateUtil.getCurrentDateSimpleToString());

        MedicalExaminationEntity medicalExamination = iMedicalExaminationRepository.findByPrescriptionNum(medicalRecord.getPrescriptionNum());
        if (!StringUtils.isEmpty(medicalExamination)) {
            recordRspVO.setExaminationCost(medicalExamination.getExaminationCost());
        }
        recordRspVO.setDoctorName(register.getDoctor());
        recordRspVO.setDepartment(register.getDepartment());

        return recordRspVO;
    }

    @Override
    public BaseResponse<String> saveTakingDrugInfo(String prescriptionNum) {


        if (StringUtils.isEmpty(prescriptionNum)) {

            return BaseResponse.error("请填写处方号！");
        }

        try {
            MedicalRecordEntity medicalRecord = iMedicalRecordRepository.findByPrescriptionNum(prescriptionNum);

            if (StringUtils.isEmpty(medicalRecord)) {

                return BaseResponse.error("未查询到相关就诊记录！");
            }
            TollTakeDrugInfoEntity tollTakeDrugInfo = iTollTakeDrugInfoRepository.findByPrescriptionNumAndTakingDrugStatus(medicalRecord.getPrescriptionNum(), 0);

            if (tollTakeDrugInfo == null) {
                return BaseResponse.error("该处方未查询到最新划价收费信息！");
            }
            UserEntity user = (UserEntity) SecurityUtils.getSubject().getPrincipal();
            if (user == null) {
                return BaseResponse.error("登录信息已过期，请重新登录！");
            }
            tollTakeDrugInfo.setTakingDrugDateTime(DateUtil.getCurrentDateToString());
            tollTakeDrugInfo.setTakingDrugOperator(user.getId());
            tollTakeDrugInfo.setTakingDrugStatus(1);

            iTollTakeDrugInfoRepository.saveAndFlush(tollTakeDrugInfo);
            return BaseResponse.success();
        } catch (Exception e) {
            logger.error("处方号={},保存划价收费—拿药信息异常！", prescriptionNum, e);
            return BaseResponse.error("操作异常，请稍后重试！");
        }

    }
}
