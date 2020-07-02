package com.xgs.hisystem.service.impl;

import com.xgs.hisystem.config.HisConstants;
import com.xgs.hisystem.pojo.bo.BaseResponse;
import com.xgs.hisystem.pojo.entity.PatientEntity;
import com.xgs.hisystem.pojo.vo.register.GetCardIdInforReqVO;
import com.xgs.hisystem.repository.IPatientRepository;
import com.xgs.hisystem.service.IGetPatientInfoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import static com.xgs.hisystem.util.card.Card.defaultGetCardId;

/**
 * @author XueGuiSheng
 * @date 2020/4/9
 * @description:
 */
@Service
public class GetPatientInfoServiceImpl implements IGetPatientInfoService {

    @Autowired
    private IPatientRepository iPatientRepository;

    @Override
    public BaseResponse<PatientEntity> getPatientInfo(GetCardIdInforReqVO reqVO) {

        String myCardId = reqVO.getCardId();
        String command = reqVO.getCommand();

        //手动输入卡号
        if (HisConstants.COMMON_STATUS_ONE.equals(command)) {
            if (StringUtils.isEmpty(myCardId)) {
                return BaseResponse.error("请输入就诊卡号！");
            }
        }
        //读卡器输入
        if (HisConstants.COMMON_STATUS_ZERO.equals(command)) {

            //读卡
            String message = defaultGetCardId();

            if (HisConstants.IC_READ_FAIIL.equals(message)) {
                return BaseResponse.error("读卡失败！请刷新页面重试！");

            } else if (HisConstants.IC_READ_NONE.equals(message)) {
                return BaseResponse.error("未识别到有效就诊卡，请重试！");

            } else {
                myCardId = message;
            }
        }
        PatientEntity patientInfor = iPatientRepository.findByCardId(myCardId);

        if (patientInfor == null) {
            return BaseResponse.error("未从该就诊卡识别到有效信息！");
        }

        return BaseResponse.success(patientInfor);
    }
}
