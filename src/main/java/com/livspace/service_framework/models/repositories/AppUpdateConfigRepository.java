package com.livspace.service_framework.models.repositories;

import com.livspace.service_framework.models.entities.AppUpdateConfigEntity;

public interface AppUpdateConfigRepository extends BaseRepository<AppUpdateConfigEntity> {
    AppUpdateConfigEntity findByVersionNo(Long versionNo);
}
