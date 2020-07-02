package com.xgs.hisystem.service.impl;

import com.xgs.hisystem.config.HisConstants;
import com.xgs.hisystem.pojo.bo.BaseResponse;
import com.xgs.hisystem.pojo.entity.*;
import com.xgs.hisystem.pojo.vo.outpatient.*;
import com.xgs.hisystem.pojo.vo.register.GetCardIdInforReqVO;
import com.xgs.hisystem.repository.*;
import com.xgs.hisystem.service.IGetPatientInfoService;
import com.xgs.hisystem.service.IOutpatientService;
import com.xgs.hisystem.util.DateUtil;
import org.apache.shiro.SecurityUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import javax.persistence.criteria.Predicate;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

/**
 * @author xgs
 * @date 2019-5-6
 * @description:
 */
@Service
public class OutpatientServiceImpl implements IOutpatientService {

    @Autowired
    private IPatientRepository iPatientRepository;
    @Autowired
    private IRegisterRepository iRegisterRepository;
    @Autowired
    private IOutpatientQueueRepository iOutpatientQueueRepository;
    @Autowired
    private IMedicalRecordRepository iMedicalRecordRepository;
    @Autowired
    private IDrugRepository iDrugRepository;
    @Autowired
    private IMedicalExaminationRepository iMedicalExaminationRepository;
    @Autowired
    private IGetPatientInfoService iGetPatientInfoService;

    private static final Logger logger = LoggerFactory.getLogger(OutpatientServiceImpl.class);

    /**
     * 获取就诊卡信息
     *
     * @return
     */
    @Override
    public PatientInforRspVO getCardIdInfor(GetCardIdInforReqVO reqVO) throws Exception {

        PatientInforRspVO rspVO = new PatientInforRspVO();

        UserEntity user = (UserEntity) SecurityUtils.getSubject().getPrincipal();
        if (StringUtils.isEmpty(user)) {
            rspVO.setMessage("登录信息已过期，请重新登录！");
            return rspVO;
        }

        //获取患者信息
        BaseResponse<PatientEntity> baseResponse = iGetPatientInfoService.getPatientInfo(reqVO);

        if (BaseResponse.RESPONSE_FAIL.equals(baseResponse.getStatus())) {
            rspVO.setMessage(baseResponse.getMessage());
            return rspVO;
        }
        PatientEntity patientInfor = baseResponse.getData();


        List<OutpatientQueueEntity> outpatientQueues = iOutpatientQueueRepository.findAll((Specification<OutpatientQueueEntity>) (root, query, cb) -> {
            List<Predicate> filter = new ArrayList<>();
            filter.add(cb.equal(root.get("patient"), patientInfor));
            //不包括过期状态
            filter.add(cb.notEqual(root.get("outpatientQueueStatus"), HisConstants.QUEUE.OVERDUE));
            filter.add(cb.equal(root.get("user"), user));

            //当天
            String nowDate = DateUtil.getCurrentDateSimpleToString();
            filter.add(cb.between(root.get("createDatetime"), nowDate.concat(" 00:00:00"), nowDate.concat(" 23:59:59")));

            return query.where(filter.toArray(new Predicate[filter.size()])).getRestriction();
        });

        if (outpatientQueues == null || outpatientQueues.isEmpty()) {
            rspVO.setMessage("未查询到挂号信息，请与患者核对挂号就诊医生！");
            return rspVO;
        }

        //符合条件的队列状态要么是正常，要么是待处理。不会同时存在
        if (outpatientQueues.size() == 1) {
            OutpatientQueueEntity outpatientQueue = outpatientQueues.get(0);
            if (outpatientQueue.getOutpatientQueueStatus() == HisConstants.QUEUE.LATER) {
                rspVO.setMessage("未完成就诊，请从左侧栏恢复！");
                return rspVO;
            }
            BeanUtils.copyProperties(patientInfor, rspVO);
            rspVO.setDate(DateUtil.getCurrentDateSimpleToString());
            rspVO.setDepartment(outpatientQueue.getRegister().getDepartment());
            rspVO.setAge(DateUtil.getAge(patientInfor.getBirthday()));

            //队列Id
            rspVO.setQueueId(outpatientQueue.getId());

            String registerId = outpatientQueue.getRegister().getId();
            MedicalRecordEntity medicalRecord = iMedicalRecordRepository.findByRegisterId(registerId);
            if (StringUtils.isEmpty(medicalRecord)) {
                rspVO.setPrescriptionNum("O".concat(String.valueOf(System.currentTimeMillis())));
            } else {
                rspVO.setPrescriptionNum(medicalRecord.getPrescriptionNum());
            }
        } else {
            rspVO.setMessage("队列信息异常，请联系管理员！");
            return rspVO;
        }
        return rspVO;
    }

    @Override
    public BaseResponse<String> changePatientInfor(OtherPatientInforReqVO reqVO) {

        try {
            PatientEntity patient = iPatientRepository.findByCardId(reqVO.getCardId());

            patient.setMaritalStatus(reqVO.getMaritalStatus());
            patient.setCareer(reqVO.getCareer());
            patient.setPersonalHistory(reqVO.getPersonalHistory());
            patient.setPastHistory(reqVO.getPastHistory());
            patient.setFamilyHistory(reqVO.getFamilyHistory());


            iPatientRepository.saveAndFlush(patient);
            return BaseResponse.success(HisConstants.USER.SUCCESS);
        } catch (Exception e) {
            e.printStackTrace();
            return BaseResponse.error("信息提交异常！请稍后重试");
        }
    }


    /**
     * 稍后处理
     *
     * @param reqVO
     * @return
     */
    @Override
    public BaseResponse<String> processLaterMedicalRecord(MedicalRecordReqVO reqVO) {

        try {
            Optional<OutpatientQueueEntity> outpatientQueueTemp = iOutpatientQueueRepository.findById(reqVO.getQueueId());

            if (!outpatientQueueTemp.isPresent()) {
                return BaseResponse.error("队列信息异常，请联系管理员！");
            }
            OutpatientQueueEntity outpatientQueue = outpatientQueueTemp.get();

            RegisterEntity register = outpatientQueue.getRegister();

            //就诊记录
            MedicalRecordEntity medicalRecord = iMedicalRecordRepository.findByRegisterId(register.getId());

            if (medicalRecord == null) {

                medicalRecord = new MedicalRecordEntity();

                medicalRecord.setConditionDescription(reqVO.getConditionDescription());
                medicalRecord.setRegister(register);
                medicalRecord.setPrescriptionNum(reqVO.getPrescriptionNum());
                iMedicalRecordRepository.saveAndFlush(medicalRecord);
            } else {
                //检查是否已体检过
                MedicalExaminationEntity medicalExamination = iMedicalExaminationRepository.findByPrescriptionNum(medicalRecord.getPrescriptionNum());

                if (medicalExamination != null) {
                    return BaseResponse.error("该患者已体检过，无需再稍后处理！");
                }
            }
            //更新为稍后处理状态
            outpatientQueue.setOutpatientQueueStatus(HisConstants.QUEUE.LATER);

            iOutpatientQueueRepository.saveAndFlush(outpatientQueue);
            return BaseResponse.success(HisConstants.USER.SUCCESS);
        } catch (Exception e) {
            logger.error("保存就诊记录异常！", e);
            return BaseResponse.error("保存就诊记录异常！");
        }
    }

    @Override
    public PatientInforRspVO restorePatientInfor(String registerId) throws Exception {

        PatientInforRspVO rspVO = new PatientInforRspVO();

        try {
            OutpatientQueueEntity outpatientQueue = iOutpatientQueueRepository.findByRegisterId(registerId);

            if (StringUtils.isEmpty(outpatientQueue) || HisConstants.QUEUE.LATER != outpatientQueue.getOutpatientQueueStatus()) {
                rspVO.setMessage("队列信息异常，请刷新页面或联系管理员后重试！");
                return rspVO;
            }
            MedicalRecordEntity medicalRecord = iMedicalRecordRepository.findByRegisterId(registerId);

            if (StringUtils.isEmpty(medicalRecord)) {
                rspVO.setMessage("就诊信息异常，请刷新页面或联系管理员后重试！");
                return rspVO;
            }
            //检查体检是否已收费
            MedicalExaminationEntity medicalExamination = iMedicalExaminationRepository.findByPrescriptionNum(medicalRecord.getPrescriptionNum());
            if (medicalExamination != null) {

                Optional<RegisterEntity> register = iRegisterRepository.findById(medicalExamination.getRegister().getId());
                if (register.isPresent() && register.get().getChargeStatus() == 0) {

                    rspVO.setMessage("该就诊所关联的体检项目未收费，请收费后再继续就诊！");
                    return rspVO;
                }
            }

            outpatientQueue.setOutpatientQueueStatus(1);
            iOutpatientQueueRepository.saveAndFlush(outpatientQueue);

            PatientEntity patientInfor = outpatientQueue.getPatient();

            BeanUtils.copyProperties(patientInfor, rspVO);
            BeanUtils.copyProperties(medicalRecord, rspVO);
            rspVO.setDate(DateUtil.getCurrentDateSimpleToString());
            rspVO.setDepartment(outpatientQueue.getRegister().getDepartment());
            rspVO.setAge(DateUtil.getAge(patientInfor.getBirthday()));
            rspVO.setQueueId(outpatientQueue.getId());

        } catch (Exception e) {
            rspVO.setMessage("系统异常，请稍后重试！");
        }
        return rspVO;
    }

    @Override
    public List<String> getAllDrug() {

        List<DrugEntity> drugEntityList = iDrugRepository.findAll();

        List<String> drugList = new ArrayList<>();
        drugEntityList.forEach(drug -> {

            drugList.add(drug.getName());
        });
        return drugList;
    }

    @Override
    public DrugRspVO getDrugInfor(String drug) {

        DrugEntity drugEntity = iDrugRepository.findByName(drug);
        if (StringUtils.isEmpty(drugEntity)) {
            return null;
        }
        DrugRspVO drugRspVO = new DrugRspVO();
        drugRspVO.setSpecification(drugEntity.getSpecification().concat("*").concat("1").concat(drugEntity.getUnit()));
        drugRspVO.setPrice(drugEntity.getPrice());
        return drugRspVO;
    }


    /**
     * 就诊完成，保存病历
     *
     * @param reqVO
     * @return
     */
    @Override
    public BaseResponse<String> addMedicalRecord(MedicalRecordReqVO reqVO) {

        try {


            //门诊队列
            Optional<OutpatientQueueEntity> outpatientQueueTemp = iOutpatientQueueRepository.findById(reqVO.getQueueId());

            if (!outpatientQueueTemp.isPresent()) {

                return BaseResponse.error("门诊队列信息异常！");
            }
            OutpatientQueueEntity outpatientQueue = outpatientQueueTemp.get();

            RegisterEntity register = outpatientQueue.getRegister();

            MedicalRecordEntity medicalRecord = iMedicalRecordRepository.findByPrescriptionNum(reqVO.getPrescriptionNum());

            if (medicalRecord == null) {

                medicalRecord = new MedicalRecordEntity();

                medicalRecord.setRegister(register);
                medicalRecord.setPrescriptionNum(reqVO.getPrescriptionNum());
            }

            //更新就诊状态
            register.setTreatmentStatus(1);
            iRegisterRepository.saveAndFlush(register);

            medicalRecord.setConditionDescription(reqVO.getConditionDescription());
            medicalRecord.setDiagnosisResult(reqVO.getDiagnosisResult());
            medicalRecord.setDrugCost(reqVO.getDrugCost());
            medicalRecord.setMedicalOrder(reqVO.getMedicalOrder());
            medicalRecord.setPrescription(reqVO.getPrescription());

            iMedicalRecordRepository.saveAndFlush(medicalRecord);

            //修改队列状态为过期
            outpatientQueue.setOutpatientQueueStatus(HisConstants.QUEUE.OVERDUE);
            iOutpatientQueueRepository.saveAndFlush(outpatientQueue);

            return BaseResponse.success(HisConstants.USER.SUCCESS);

        } catch (Exception e) {
            logger.error("保存就诊记录异常！", e);
            return BaseResponse.error("保存就诊记录异常！");
        }
    }

    @Override
    public medicalExaminationInfoRspVO getMedicalExamination(String prescriptionNum) {

        MedicalExaminationEntity medicalExamination = iMedicalExaminationRepository.findByPrescriptionNum(prescriptionNum);

        medicalExaminationInfoRspVO rspVO = new medicalExaminationInfoRspVO();
        if (StringUtils.isEmpty(medicalExamination)) {
            rspVO.setMessage("未查询到相关体检信息！");
            return rspVO;
        }

        rspVO.setHeartRate(medicalExamination.getHeartRate());
        rspVO.setBodyTemperature(medicalExamination.getBodyTemperature());
        rspVO.setBloodPressure(medicalExamination.getBloodPressure());

        rspVO.setPulse(medicalExamination.getPulse());

        return rspVO;
    }

    @Override
    public List<GetAllOutpatientQueueRspVO> getAllOutpatientQueue() {

        List<GetAllOutpatientQueueRspVO> rsp = new ArrayList<>();

        UserEntity user = (UserEntity) SecurityUtils.getSubject().getPrincipal();

        if (!StringUtils.isEmpty(user)) {

            List<OutpatientQueueEntity> outpatientQueueList = iOutpatientQueueRepository.findByUserIdOrderByCreateDatetimeAsc(user.getId());

            if (outpatientQueueList != null && !outpatientQueueList.isEmpty()) {

                //非当天病人
                List<OutpatientQueueEntity> overdueQueues = new ArrayList<>();

                outpatientQueueList.forEach(outpatientQueue -> {

                    String createDate = DateUtil.DateTimeToDate(outpatientQueue.getCreateDatetime());
                    String nowDate = DateUtil.getCurrentDateSimpleToString();

                    if (nowDate.equals(createDate)) {
                        if (outpatientQueue.getOutpatientQueueStatus() != HisConstants.QUEUE.OVERDUE) {

                            GetAllOutpatientQueueRspVO vo = new GetAllOutpatientQueueRspVO();
                            vo.setCardId(outpatientQueue.getRegister().getPatient().getCardId());
                            vo.setPatientName(outpatientQueue.getRegister().getPatient().getName());
                            vo.setRegisterId(outpatientQueue.getRegister().getId());
                            vo.setStatus(outpatientQueue.getOutpatientQueueStatus());

                            rsp.add(vo);
                        }
                    } else {
                        outpatientQueue.setOutpatientQueueStatus(HisConstants.QUEUE.OVERDUE);
                        overdueQueues.add(outpatientQueue);
                    }
                });
                if (overdueQueues.size() > 0) {
                    iOutpatientQueueRepository.saveAll(overdueQueues);
                }
            }
        }
        return rsp;
    }
}
