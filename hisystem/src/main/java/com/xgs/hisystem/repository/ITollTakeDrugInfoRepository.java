package com.xgs.hisystem.repository;

import com.xgs.hisystem.pojo.entity.TollTakeDrugInfoEntity;

/**
 * @author XueGuiSheng
 * @date 2020/3/10
 * @description:
 */
public interface ITollTakeDrugInfoRepository extends BaseRepository<TollTakeDrugInfoEntity>{

    TollTakeDrugInfoEntity findByPatientIdAndTakingDrugStatus(String patientId,int takingDrugStatus);

    TollTakeDrugInfoEntity findByPrescriptionNumAndTakingDrugStatus(String prescriptionNum,int takingDrugStatus);
}
