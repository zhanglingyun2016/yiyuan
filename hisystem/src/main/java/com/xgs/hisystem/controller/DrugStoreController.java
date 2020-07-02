package com.xgs.hisystem.controller;

import com.xgs.hisystem.pojo.bo.BaseResponse;
import com.xgs.hisystem.pojo.bo.PageRspBO;
import com.xgs.hisystem.pojo.vo.drugStorage.DrugReqVO;
import com.xgs.hisystem.pojo.vo.drugStorage.DrugRspVO;
import com.xgs.hisystem.pojo.vo.drugStorage.DrugSearchReqVO;
import com.xgs.hisystem.service.IDrugStoreService;
import io.swagger.annotations.Api;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * @author xgs
 * @date 2019-5-12
 * @description:
 */
@RestController
@RequestMapping(value = "/drugstore")
@Api(tags = "药品管理API")
public class DrugStoreController {

    @Autowired
    private IDrugStoreService iDrugStoreService;



    /**
     * 新增药品
     *
     * @param reqVO
     * @return
     */
    @PostMapping(value = "/addNewDrug")
    public BaseResponse<String> addNewDrug(@RequestBody @Validated DrugReqVO reqVO) {
        return iDrugStoreService.addNewDrug(reqVO);
    }

    /**
     * 新增剂型
     *
     * @param drugType
     * @return
     */
    @PostMapping(value = "/addDrugType")
    public BaseResponse<String> addDrugType(@RequestParam String drugType) {

        return iDrugStoreService.addDrugType(drugType);
    }

    /**
     * 新增功效分类
     *
     * @param efficacyClassification
     * @return
     */
    @PostMapping(value = "/addEfficacyClassification")
    public BaseResponse<String> addEfficacyClassification(@RequestParam String efficacyClassification) {

        return iDrugStoreService.addEfficacyClassification(efficacyClassification);
    }

    /**
     * 获取所有剂型
     *
     * @return
     */
    @PostMapping(value = "/getAllDrugType")
    public List<String> getAllDrugType() {
        return iDrugStoreService.getAllDrugType();
    }

    /**
     * 获取所有功效
     *
     * @return
     */
    @PostMapping(value = "/getAllEfficacyClassification")
    public List<String> getAllEfficacyClassification() {
        return iDrugStoreService.getAllEfficacyClassification();
    }

    /**
     * 获取药品信息
     *
     * @param drug
     * @return
     */
    @PostMapping(value = "/getDrugInfor")
    public DrugRspVO getDrugInfor(@RequestParam String drug) {
        return iDrugStoreService.getDrugInfor(drug);
    }

    /**
     * 已有药品入库
     *
     * @param drug
     * @param addStorageQuantity
     * @return
     */
    @PostMapping(value = "/addStorageQuantity")
    public BaseResponse<String> addStorageQuantity(@RequestParam String drug, @RequestParam String addStorageQuantity) {

        return iDrugStoreService.addStorageQuantity(drug, addStorageQuantity);
    }

    /**
     * 查询药品
     *
     * @param reqVO
     * @return
     */
    @GetMapping(value = "/getAllDrug")
    public PageRspBO<DrugRspVO> getAllDrug(DrugSearchReqVO reqVO) {

        return iDrugStoreService.getAllDrug(reqVO);
    }

    /**
     * 修改药品信息
     *
     * @param reqVO
     * @return
     */
    @PostMapping(value = "/updateDrug")
    public BaseResponse<String> updateDrug(@RequestBody @Validated DrugReqVO reqVO) {

        return iDrugStoreService.updateDrug(reqVO);
    }

    /**
     * 删除药品
     *
     * @param drugName
     * @return
     */
    @PostMapping(value = "/deleteDrug")
    public BaseResponse<String> deleteDrug(@RequestParam String drugName) {

        return iDrugStoreService.deleteDrug(drugName);
    }


}
