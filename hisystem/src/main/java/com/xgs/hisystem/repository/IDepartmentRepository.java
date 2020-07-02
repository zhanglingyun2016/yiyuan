package com.xgs.hisystem.repository;

import com.xgs.hisystem.pojo.entity.DepartmentEntity;

/**
 * @author XueGuiSheng
 * @date 2020/3/23
 * @description:
 */
public interface IDepartmentRepository extends BaseRepository<DepartmentEntity>{

    DepartmentEntity findByNameAndAddress(String name,String address);

    DepartmentEntity findByCode(int code);
}
