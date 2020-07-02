package com.xgs.hisystem.service.impl;

import com.xgs.hisystem.config.HisConstants;
import com.xgs.hisystem.pojo.bo.BaseResponse;
import com.xgs.hisystem.pojo.entity.*;
import com.xgs.hisystem.pojo.vo.medicalExamination.medicalExaminationInfoReqVO;
import com.xgs.hisystem.pojo.vo.medicalExamination.PatientInforRspVO;
import com.xgs.hisystem.pojo.vo.register.GetCardIdInforReqVO;
import com.xgs.hisystem.repository.*;
import com.xgs.hisystem.service.IGetPatientInfoService;
import com.xgs.hisystem.service.IMedicalExaminationService;
import com.xgs.hisystem.util.DateUtil;
import org.apache.shiro.SecurityUtils;
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
 * @date 2019-5-18
 * @description:
 */
@Service
public class MedicalExaminationServiceImpl implements IMedicalExaminationService {

    @Autowired
    private IMedicalExaminationRepository iMedicalExaminationRepository;
    @Autowired
    private IOutpatientQueueRepository iOutpatientQueueRepository;
    @Autowired
    private IMedicalRecordRepository iMedicalRecordRepository;
    @Autowired
    private IRegisterRepository iRegisterRepository;
    @Autowired
    private IGetPatientInfoService iGetPatientInfoService;

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
            //医技师患者队列状态只有正常和过期状态
            filter.add(cb.equal(root.get("outpatientQueueStatus"), HisConstants.QUEUE.NORMAL));
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

        if (outpatientQueues.size() == 1) {
            BeanUtils.copyProperties(patientInfor, rspVO);
            rspVO.setAge(DateUtil.getAge(patientInfor.getBirthday()));
            rspVO.setQueueId(outpatientQueues.get(0).getId());
        } else {
            rspVO.setMessage("队列信息异常，请联系管理员！");
            return rspVO;
        }

        return rspVO;
    }

    @Override
    public BaseResponse<String> saveMedicalExaminationInfo(medicalExaminationInfoReqVO reqVO) {

        try {
            UserEntity user = (UserEntity) SecurityUtils.getSubject().getPrincipal();
            if (StringUtils.isEmpty(user)) {
                return BaseResponse.error("登录信息已过期！");
            }

            //体检队列信息
            Optional<OutpatientQueueEntity> outpatientQueueTemp = iOutpatientQueueRepository.findById(reqVO.getQueueId());

            if (!outpatientQueueTemp.isPresent()) {
                return BaseResponse.error("队列信息异常，请联系管理员！");
            }
            OutpatientQueueEntity outpatientQueue = outpatientQueueTemp.get();

            //挂号信息
            RegisterEntity register = outpatientQueue.getRegister();

            //体检信息
            MedicalExaminationEntity medicalExamination = new MedicalExaminationEntity();

            //校验处方号
            String prescriptionNum = reqVO.getPrescriptionNum();
            if (!StringUtils.isEmpty(prescriptionNum)) {

                MedicalRecordEntity medicalRecord = iMedicalRecordRepository.findByPrescriptionNum(prescriptionNum);

                if (medicalRecord == null) {
                    return BaseResponse.error("未查询相关就诊记录，处方号无效！");
                }
                //处方号对应门诊队列信息
                OutpatientQueueEntity queue = iOutpatientQueueRepository.findByRegisterId(medicalRecord.getRegister().getId());

                if (queue == null) {
                    return BaseResponse.error("该处方号未查询相关门诊队列信息，信息异常请联系管理员！");
                }
                if (outpatientQueue.getPatient() != queue.getPatient()) {
                    return BaseResponse.error("该处方号对应患者信息与体检队列患者信息不符！");
                }

                if (HisConstants.QUEUE.LATER != queue.getOutpatientQueueStatus()) {

                    return BaseResponse.error("该患者无待处理的门诊，处方号无效！");
                }

                medicalExamination.setPrescriptionNum(prescriptionNum);
            }
            medicalExamination.setRegister(register);
            medicalExamination.setBloodPressure(reqVO.getBloodPressure());
            medicalExamination.setBodyTemperature(reqVO.getBodyTemperature());
            medicalExamination.setHeartRate(reqVO.getHeartRate());
            medicalExamination.setPulse(reqVO.getPulse());
            medicalExamination.setExaminationCost(Integer.parseInt(reqVO.getExaminationCost()));

            iMedicalExaminationRepository.saveAndFlush(medicalExamination);

            //修改体检队列状态为过期
            outpatientQueue.setOutpatientQueueStatus(HisConstants.QUEUE.OVERDUE);
            iOutpatientQueueRepository.saveAndFlush(outpatientQueue);

            //更新就诊状态
            register.setTreatmentStatus(1);
            iRegisterRepository.saveAndFlush(register);

            return BaseResponse.success(HisConstants.USER.SUCCESS);
        } catch (Exception e) {
            e.printStackTrace();
            return BaseResponse.error("保存体检信息异常，请稍后重试！");
        }
    }
}
