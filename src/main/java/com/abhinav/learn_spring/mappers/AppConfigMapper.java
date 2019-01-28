package com.abhinav.learn_spring.mappers;

import com.abhinav.learn_spring.models.entities.AppUpdateConfigEntity;
import com.abhinav.learn_spring.models.entries.AppUpdateConfigEntry;
import org.mapstruct.Mapper;

@Mapper
public interface AppConfigMapper {
    AppUpdateConfigEntry toEntry(AppUpdateConfigEntity entity);

    AppUpdateConfigEntity toEntity(AppUpdateConfigEntry entity);
}
