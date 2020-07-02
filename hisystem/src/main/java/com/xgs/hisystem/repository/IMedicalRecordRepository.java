package com.xgs.hisystem.repository;

import com.xgs.hisystem.pojo.entity.MedicalRecordEntity;

/**
 * @author xgs
 * @date 2019-5-10
 * @description:
 */
public interface IMedicalRecordRepository extends BaseRepository<MedicalRecordEntity> {

    MedicalRecordEntity findByRegisterId(String registerId);

    MedicalRecordEntity findByPrescriptionNum(String prescriptionNum);
}
