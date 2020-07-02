package com.xgs.hisystem.repository;

import com.xgs.hisystem.pojo.entity.DrugEntity;

/**
 * @author xgs
 * @date 2019-5-12
 * @description:
 */
public interface IDrugRepository extends BaseRepository<DrugEntity> {

    DrugEntity findByName(String name);
}
