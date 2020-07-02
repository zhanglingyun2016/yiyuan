package com.xgs.hisystem.repository;

import com.xgs.hisystem.pojo.entity.UserEntity;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface IUserRepository extends BaseRepository<UserEntity> {

    UserEntity findByEmail(String email);

    List<UserEntity> findByDepartmentAndDepartmentType(Integer department, Integer departmentType);
}
