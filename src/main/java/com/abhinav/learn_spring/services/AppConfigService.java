package com.abhinav.learn_spring.services;

import com.abhinav.learn_spring.mappers.AppConfigMapper;
import com.abhinav.learn_spring.models.entities.AppUpdateConfigEntity;
import com.abhinav.learn_spring.models.entries.AppUpdateConfigEntry;
import com.abhinav.learn_spring.models.repositories.AppUpdateConfigRepository;
import com.abhinav.learn_spring.models.responses.AppUpdateConfigResponse;
import org.mapstruct.factory.Mappers;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.CollectionUtils;
import org.springframework.web.client.RestTemplate;

import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.StreamSupport;

@Service
@Transactional
public class AppConfigService extends BaseService<AppUpdateConfigEntity, AppUpdateConfigEntry> {
    private final AppUpdateConfigRepository appUpdateConfigRepository;
    private final RabbitService rabbitService;
    private final RestTemplate restTemplate;

    private AppConfigMapper appConfigMapper = Mappers.getMapper(AppConfigMapper.class);

    @Autowired
    public AppConfigService(AppUpdateConfigRepository appUpdateConfigRepository, RabbitService rabbitService, RestTemplate restTemplate) {
        this.appUpdateConfigRepository = appUpdateConfigRepository;
        this.rabbitService = rabbitService;
        this.restTemplate = restTemplate;
        this.baseRepository = appUpdateConfigRepository;
    }

    public AppUpdateConfigEntry create(AppUpdateConfigEntry request) {
        AppUpdateConfigEntity entity = convertToEntity(request, null);
        return convertToEntry(appUpdateConfigRepository.save(entity), null);
    }

    public AppUpdateConfigEntry getConfig() {
        List<AppUpdateConfigEntity> configs = StreamSupport.stream(appUpdateConfigRepository.findAll().spliterator(), false).collect(Collectors.toList());
        if (CollectionUtils.isEmpty(configs)) {
            return null;
        } else {
            rabbitService.sendMessage("learn_spring", "learn_spring_routing_key", convertToEntry(configs.get(0), null));
            return convertToEntry(configs.get(0), null);
        }
    }

    public AppUpdateConfigEntry getConfigByEntry() {
        return restTemplate.getForObject("http://localhost:7076/getConfig", AppUpdateConfigResponse.class).getAppUpdateConfigEntry();
    }

    public AppUpdateConfigEntity getConfigByVersionNo(Long versionNo) {
        return appUpdateConfigRepository.findByVersionNo(versionNo);
    }

    @Override
    protected AppUpdateConfigEntry convertToEntry(AppUpdateConfigEntity entity, AppUpdateConfigEntry entry) {
        if (entry == null) {
            entry = new AppUpdateConfigEntry();
        }
        return appConfigMapper.toEntry(entity, entry);
    }

    @Override
    protected AppUpdateConfigEntity convertToEntity(AppUpdateConfigEntry entry, AppUpdateConfigEntity entity) {
        if (entity == null) {
            entity = new AppUpdateConfigEntity();
        }
        return appConfigMapper.toEntity(entry, entity);
    }
}
