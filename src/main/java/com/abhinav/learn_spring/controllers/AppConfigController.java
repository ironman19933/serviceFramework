package com.abhinav.learn_spring.controllers;

import com.abhinav.learn_spring.models.entities.AppUpdateConfigEntity;
import com.abhinav.learn_spring.models.entries.AppUpdateConfigEntry;
import com.abhinav.learn_spring.models.responses.AppUpdateConfigResponse;
import com.abhinav.learn_spring.models.responses.StatusResponse;
import com.abhinav.learn_spring.services.AppConfigService;
import com.abhinav.learn_spring.services.RabbitService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/appUpdateConfig")
public class AppConfigController extends BaseController<AppUpdateConfigResponse, AppUpdateConfigEntity, AppUpdateConfigEntry> {
    @Autowired
    public AppConfigController(AppConfigService appConfigService, RabbitService rabbitService) {
        this.service = appConfigService;
    }

    @Override
    protected AppUpdateConfigResponse createResponse(AppUpdateConfigEntry entry) {
        AppUpdateConfigResponse response = new AppUpdateConfigResponse();
        response.setAppUpdateConfigEntry(entry);
        response.setStatus(new StatusResponse(12, "", StatusResponse.Type.SUCCESS, 1L));
        return response;
    }

    @Cacheable(value = "configs")
    @RequestMapping(value = "/getConfig", method = RequestMethod.GET)
    public AppUpdateConfigResponse getConfig() {
        AppUpdateConfigResponse finalResponse = new AppUpdateConfigResponse();
        try {
            AppUpdateConfigEntry entry = ((AppConfigService) service).getConfig();
            finalResponse.setAppUpdateConfigEntry(entry);
            finalResponse.setStatus(new StatusResponse(12, "App Config is retrieved successfully", StatusResponse.Type.SUCCESS, 1L));
        } catch (Exception e) {
            finalResponse.setStatus(new StatusResponse(13, "Error Occurred", StatusResponse.Type.ERROR, 1L));
        }
        return finalResponse;
    }

    @Cacheable(value = "getConfigByClient")
    @RequestMapping(value = "/getConfigByClient", method = RequestMethod.GET)
    public AppUpdateConfigResponse getConfigByClient() {
        AppUpdateConfigResponse finalResponse = new AppUpdateConfigResponse();
        try {
            AppUpdateConfigEntry entry = ((AppConfigService) service).getConfigByRestClient();
            finalResponse.setAppUpdateConfigEntry(entry);
            finalResponse.setStatus(new StatusResponse(12, "App Config is retrieved successfully", StatusResponse.Type.SUCCESS, 1L));
        } catch (Exception e) {
            finalResponse.setStatus(new StatusResponse(13, "Error Occurred", StatusResponse.Type.ERROR, 1L));
        }
        return finalResponse;
    }

    @RequestMapping(value = "/getConfigByVersionNo/{versionNo}", method = RequestMethod.GET)
    public AppUpdateConfigEntity getConfigByEntity(@PathVariable Long versionNo) {
        return ((AppConfigService) service).getConfigByVersionNo(versionNo);
    }
}
