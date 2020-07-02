package com.xgs.hisystem.repository;

import com.xgs.hisystem.pojo.entity.RegisterEntity;

import java.util.List;

/**
 * @author xgs
 * @date 2019/4/26
 * @description:
 */
public interface IRegisterRepository extends BaseRepository<RegisterEntity> {

    List<RegisterEntity> findByPatientId(String id);

    RegisterEntity findByRegisteredNum(String registerNum);

}
