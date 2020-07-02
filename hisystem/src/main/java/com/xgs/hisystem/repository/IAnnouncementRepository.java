package com.xgs.hisystem.repository;

import com.xgs.hisystem.pojo.entity.AnnouncementEntity;

import java.util.List;

/**
 * @author xgs
 * @date 2019/4/11
 * @description:
 */
public interface IAnnouncementRepository extends BaseRepository<AnnouncementEntity> {

    AnnouncementEntity findByTitle(String title);

    List<AnnouncementEntity> findByAnnStatus(int annStatus);
}
