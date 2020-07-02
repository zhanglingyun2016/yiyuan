package com.xgs.hisystem.repository;

import com.xgs.hisystem.pojo.entity.DrugTypeEntity;

/**
 * @author xgs
 * @date 2019-5-28
 * @description:
 */
public interface IDrugTypeRepository extends BaseRepository<DrugTypeEntity> {

    DrugTypeEntity findByDrugType(String drugType);
}
