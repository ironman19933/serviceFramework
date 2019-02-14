package com.livspace.service_framework.mappers;

import com.livspace.service_framework.models.entities.AppUpdateConfigEntity;
import com.livspace.service_framework.models.entities.AppUpdateDetailEntity;
import com.livspace.service_framework.models.entries.AppUpdateConfigEntry;
import com.livspace.service_framework.models.entries.AppUpdateDetailEntry;
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
