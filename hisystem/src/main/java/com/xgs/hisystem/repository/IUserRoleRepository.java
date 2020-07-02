package com.xgs.hisystem.repository;

import com.xgs.hisystem.pojo.entity.UserRoleEntity;

import java.util.List;

/**
 * @author xgs
 * @Description:
 * @date 2019/3/20
 */
public interface IUserRoleRepository extends BaseRepository<UserRoleEntity> {

    UserRoleEntity findByUIdAndRoleId(String uid,String roleId);

    List<UserRoleEntity> findByUId(String uid);

    UserRoleEntity findByUIdAndRoleStatus(String uid, int roleStatus);

    List<UserRoleEntity> findByRoleStatusOrderByCreateDatetimeDesc(int roleStatus);
}
