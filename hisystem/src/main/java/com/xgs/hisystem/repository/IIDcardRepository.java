package com.xgs.hisystem.repository;

import com.xgs.hisystem.pojo.entity.IDcardEntity;

/**
 * @author xgs
 * @date 2019/4/26
 * @description:
 */
public interface IIDcardRepository extends BaseRepository<IDcardEntity> {

    IDcardEntity findByCardId(String cardId);
}
