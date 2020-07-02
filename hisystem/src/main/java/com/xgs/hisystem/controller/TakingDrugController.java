package com.xgs.hisystem.controller;

import com.xgs.hisystem.pojo.bo.BaseResponse;
import com.xgs.hisystem.pojo.vo.takingdrug.MedicalRecordRspVO;
import com.xgs.hisystem.service.ITakingDrugService;
import io.swagger.annotations.Api;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

/**
 * @author xgs
 * @date 2019-5-15
 * @description:
 */
@RestController
@RequestMapping(value = "/takingdrug")
@Api(tags = "拿药管理API")
public class TakingDrugController {

    @Autowired
    private ITakingDrugService iTakingDrugService;

    /**
     * 获取处方笺信息
     *
     * @param prescriptionNum
     * @return
     * @throws Exception
     */
    @PostMapping(value = "/getMedicalRecord")
    public MedicalRecordRspVO getMedicalRecord(@RequestParam String prescriptionNum) throws Exception {

        return iTakingDrugService.getMedicalRecord(prescriptionNum);
    }

    /**
     * 保存拿药信息
     *
     * @param prescriptionNum
     * @return
     */
    @PostMapping(value = "/saveTakingDrugInfo")
    public BaseResponse<String> saveTakingDrugInfo(@RequestParam String prescriptionNum) {

        return iTakingDrugService.saveTakingDrugInfo(prescriptionNum);
    }
}
