package com.xgs.hisystem.controller;

import com.xgs.hisystem.pojo.bo.BaseResponse;
import com.xgs.hisystem.pojo.bo.PageRspBO;
import com.xgs.hisystem.pojo.vo.register.*;
import com.xgs.hisystem.service.IRegisterService;
import io.swagger.annotations.Api;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * @author xgs
 * @date 2019/4/19
 * @description:
 */
@RestController
@RequestMapping(value = "/register")
@Api(tags = "挂号管理API")
public class RegisterController {

    @Autowired
    private IRegisterService iRegisterService;

    /**
     * 读取就诊卡
     *
     * @return
     */
    @PostMapping(value = "/getCardIdInfor")
    public PatientInforRspVO getCardIdInfor(@RequestBody GetCardIdInforReqVO reqVO) throws Exception {
        return iRegisterService.getCardIdInfor(reqVO);
    }

    /**
     * 读取身份证
     *
     * @return
     */
    @PostMapping(value = "/getIDcardInfor")
    public IDcardRspVO getIDcardInfor() {

        return iRegisterService.getIDcardInfor();
    }

    /**
     * 读取新卡
     *
     * @return
     */
    @PostMapping(value = "/getDefaultGetCardId")
    public BaseResponse<String> getDefaultGetCardId() {

        return iRegisterService.getDefaultGetCardId();
    }

    /**
     * 办就诊卡
     *
     * @param reqVO
     * @return
     * @throws Exception
     */
    @PostMapping(value = "/addPatientInfor")
    public BaseResponse<String> addPatientInfor(@RequestBody @Validated PatientInforReqVO reqVO) throws Exception {

        return iRegisterService.addPatientInfor(reqVO);
    }

    /**
     * 补办就诊卡
     *
     * @param reqVO
     * @return
     */
    @PostMapping(value = "/coverCardId")
    public BaseResponse<String> coverCardId(@RequestBody @Validated PatientInforReqVO reqVO) {

        return iRegisterService.coverCardId(reqVO);
    }

    /**
     * 获取医生
     *
     * @param reqVO
     * @return
     */

    @GetMapping(value = "/getAllRegisterDoctor")
    public List<RegisterDoctorRspVO> getAllRegisterDoctor(RegisterTypeReqVO reqVO) {

        return iRegisterService.getAllRegisterDoctor(reqVO);

    }

    /**
     * 提交挂号信息
     * @param reqVO
     * @return
     */

    @PostMapping(value = "/addRegisterInfor")
    public BaseResponse<String> addRegisterInfor(@RequestBody @Validated RegisterInforReqVO reqVO) {

        return iRegisterService.addRegisterInfor(reqVO);
    }

    /**
     * 挂号记录查询
     *
     * @param reqVO
     * @return
     */
    @GetMapping(value = "/getRegisterRecord")
    public PageRspBO<RegisterRecordRspVO> getRegisterRecord(RegisterRecordSearchReqVO reqVO) {

        return iRegisterService.getRegisterRecord(reqVO);
    }
}
