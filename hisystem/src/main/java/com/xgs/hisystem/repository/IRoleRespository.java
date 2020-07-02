package com.xgs.hisystem.repository;

import com.xgs.hisystem.pojo.entity.RoleEntity;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

/**
 * @author xgs
 * @Description:
 * @date 2019/3/20
 */
public interface IRoleRespository extends BaseRepository<RoleEntity> {

    RoleEntity findByRole(String role);

    RoleEntity findByRoleValue(Integer value);

    RoleEntity findByDescription(String roleName);

    @Query(nativeQuery = true,value = "SELECT c.* FROM his_user a RIGHT JOIN his_user_role b ON a.id = b.uid LEFT JOIN his_role c ON b.role_id = c.id WHERE a.id = ?1 AND b.role_status = 1")
    List<RoleEntity> findByUserIdAndRoleStatus(String userId);
}
