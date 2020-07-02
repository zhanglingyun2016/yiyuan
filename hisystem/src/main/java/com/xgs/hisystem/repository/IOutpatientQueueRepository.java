package com.xgs.hisystem.repository;

import com.xgs.hisystem.pojo.entity.OutpatientQueueEntity;

import java.util.List;

/**
 * @author xgs
 * @date 2019-5-6
 * @description:
 */
public interface IOutpatientQueueRepository extends BaseRepository<OutpatientQueueEntity> {

    OutpatientQueueEntity findByPatientId(String id);

    List<OutpatientQueueEntity> findByUserIdOrderByCreateDatetimeAsc(String userid);

    OutpatientQueueEntity findByRegisterId(String registerId);
}
