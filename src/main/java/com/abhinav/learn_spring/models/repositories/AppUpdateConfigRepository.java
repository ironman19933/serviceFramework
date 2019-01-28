package com.abhinav.learn_spring.models.repositories;

import com.abhinav.learn_spring.models.entities.AppUpdateConfigEntity;

public interface AppUpdateConfigRepository extends BaseRepository<AppUpdateConfigEntity, Integer> {
    AppUpdateConfigEntity findByVersionNo(Long versionNo);
}
