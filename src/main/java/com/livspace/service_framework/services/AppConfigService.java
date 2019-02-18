package com.livspace.service_framework.services;

import com.livspace.service_framework.mappers.AppConfigMapper;
import com.livspace.service_framework.models.entities.AppUpdateConfigEntity;
import com.livspace.service_framework.models.entities.QAppUpdateConfigEntity;
import com.livspace.service_framework.models.entries.AppUpdateConfigEntry;
import com.livspace.service_framework.models.repositories.AppUpdateConfigRepository;
import com.livspace.service_framework.models.responses.AppUpdateConfigResponse;
import org.mapstruct.factory.Mappers;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.CollectionUtils;
import org.springframework.web.client.RestTemplate;
import com.mysema.query.types.expr.BooleanExpression;

import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;
import java.util.stream.StreamSupport;

@Service
@Transactional
public class AppConfigService extends BaseService<AppUpdateConfigEntity, AppUpdateConfigEntry> {
    private final RabbitService rabbitService;
    private final RestTemplate restTemplate;

    private AppConfigMapper appConfigMapper = Mappers.getMapper(AppConfigMapper.class);

    @Autowired
    public AppConfigService(AppUpdateConfigRepository appUpdateConfigRepository, RabbitService rabbitService, RestTemplate restTemplate) {
        this.rabbitService = rabbitService;
        this.restTemplate = restTemplate;
        this.repository = appUpdateConfigRepository;
        this.baseRepository = appUpdateConfigRepository;
    }

    public AppUpdateConfigEntry getConfig() {

        List<AppUpdateConfigEntity> configs = StreamSupport.stream(repository.findAll().spliterator(), false).collect(Collectors.toList());
        if (CollectionUtils.isEmpty(configs)) {
            return null;
        } else {
            rabbitService.sendMessage("learn_spring", "learn_spring_routing_key", convertToEntry(configs.get(0), null));
            return convertToEntry(configs.get(0), null);
        }
    }

    public AppUpdateConfigEntry getConfigByRestClient() {
        return restTemplate.getForObject("http://localhost:7076/appUpdateConfig/getConfig", AppUpdateConfigResponse.class).getAppUpdateConfigEntries().get(0);
    }

    public AppUpdateConfigEntry abhinav(Long id, String createdBy) {
        QAppUpdateConfigEntity appUpdateConfigEntity = QAppUpdateConfigEntity.appUpdateConfigEntity;
        BooleanExpression predicate = appUpdateConfigEntity.id.eq(1L).and(appUpdateConfigEntity.createdBy.eq(createdBy));
        //AppUpdateConfigEntity entity = ((AppUpdateConfigRepository) repository).findOne(predicate);
        return null;
    }

    public AppUpdateConfigEntity getConfigByVersionNo(Long versionNo) {
        return ((AppUpdateConfigRepository) repository).findByVersionNo(versionNo);
    }

    @Override
    protected AppUpdateConfigEntry convertToEntry(AppUpdateConfigEntity entity, AppUpdateConfigEntry entry) {
        if (Objects.isNull(entry)) {
            entry = new AppUpdateConfigEntry();
        }
        return appConfigMapper.toEntry(entity, entry);
    }

    @Override
    protected AppUpdateConfigEntity convertToEntity(AppUpdateConfigEntry entry, AppUpdateConfigEntity entity) {
        if (Objects.isNull(entity)) {
            entity = new AppUpdateConfigEntity();
        }
        return appConfigMapper.toEntity(entry, entity);
    }
}
