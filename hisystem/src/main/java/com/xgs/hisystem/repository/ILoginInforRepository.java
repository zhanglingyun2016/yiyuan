package com.xgs.hisystem.repository;

import com.xgs.hisystem.pojo.entity.LoginInforEntity;

/**
 * @author xgs
 * @Description:
 * @date 2019/3/23
 */
public interface ILoginInforRepository extends BaseRepository<LoginInforEntity> {

    LoginInforEntity findByLoginIpAndLoginBroswerAndUserId(String ip,String broswer,String id);
}
