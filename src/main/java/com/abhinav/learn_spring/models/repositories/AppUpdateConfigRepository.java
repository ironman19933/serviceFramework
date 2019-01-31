package com.abhinav.learn_spring.models.repositories;

import com.abhinav.learn_spring.models.entities.AppUpdateConfigEntity;
import org.springframework.data.repository.CrudRepository;

public interface AppUpdateConfigRepository extends BaseRepository, CrudRepository<AppUpdateConfigEntity, Long> {
    AppUpdateConfigEntity findByVersionNo(Long versionNo);
}
