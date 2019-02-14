package com.abhinav.learn_spring.mappers;

import com.abhinav.learn_spring.models.entities.AppUpdateConfigEntity;
import com.abhinav.learn_spring.models.entities.AppUpdateDetailEntity;
import com.abhinav.learn_spring.models.entries.AppUpdateConfigEntry;
import com.abhinav.learn_spring.models.entries.AppUpdateDetailEntry;
import org.mapstruct.*;

@Mapper(nullValueCheckStrategy = NullValueCheckStrategy.ALWAYS, unmappedTargetPolicy = ReportingPolicy.IGNORE)
public interface AppConfigMapper {
    AppUpdateConfigEntry toEntry(AppUpdateConfigEntity entity, @MappingTarget AppUpdateConfigEntry entry);

    AppUpdateConfigEntity toEntity(AppUpdateConfigEntry entry, @MappingTarget AppUpdateConfigEntity entity);

    @Mapping(target = "appUpdateId", source = "entity.appUpdateConfigEntity.id")
    AppUpdateDetailEntry entryToEntity(AppUpdateDetailEntity entity);

    @Mapping(target = "appUpdateConfigEntity.id", source = "entry.appUpdateId")
    AppUpdateDetailEntity entityToEntry                                                                                                             (AppUpdateDetailEntry entry);
}
