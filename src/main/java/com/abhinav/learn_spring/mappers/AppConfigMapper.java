package com.abhinav.learn_spring.mappers;

import com.abhinav.learn_spring.models.entities.AppUpdateConfigEntity;
import com.abhinav.learn_spring.models.entries.AppUpdateConfigEntry;
import org.mapstruct.Mapper;
import org.mapstruct.MappingTarget;
import org.mapstruct.NullValueCheckStrategy;

@Mapper(nullValueCheckStrategy = NullValueCheckStrategy.ALWAYS)
public interface AppConfigMapper {
    AppUpdateConfigEntry toEntry(AppUpdateConfigEntity entity, @MappingTarget AppUpdateConfigEntry entry);

    AppUpdateConfigEntity toEntity(AppUpdateConfigEntry entry, @MappingTarget AppUpdateConfigEntity entity);
}
