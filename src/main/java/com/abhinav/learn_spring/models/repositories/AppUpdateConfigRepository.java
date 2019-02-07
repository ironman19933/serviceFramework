package com.abhinav.learn_spring.models.repositories;

import com.abhinav.learn_spring.models.entities.AppUpdateConfigEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.repository.CrudRepository;

public interface AppUpdateConfigRepository extends CustomBaseRepository<AppUpdateConfigEntity>{
    AppUpdateConfigEntity findByVersionNo(Long versionNo);
}
