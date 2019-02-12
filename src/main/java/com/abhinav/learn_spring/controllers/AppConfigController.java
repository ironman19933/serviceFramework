package com.abhinav.learn_spring.controllers;

import com.abhinav.learn_spring.models.entities.AppUpdateConfigEntity;
import com.abhinav.learn_spring.models.entries.AppUpdateConfigEntry;
import com.abhinav.learn_spring.models.responses.AppUpdateConfigResponse;
import com.abhinav.learn_spring.models.responses.StatusResponse;
import com.abhinav.learn_spring.services.AppConfigService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.http.HttpHeaders;
import org.springframework.web.bind.annotation.*;

import java.util.Collections;
import java.util.List;

@RestController
@RequestMapping("/appUpdateConfig")
public class AppConfigController extends BaseController<AppUpdateConfigResponse, AppUpdateConfigEntity, AppUpdateConfigEntry> {
    @Autowired
    public AppConfigController(AppConfigService appConfigService) {
        this.service = appConfigService;
    }

    @Override
    protected AppUpdateConfigResponse createResponse(List<AppUpdateConfigEntry> entryList) {
        AppUpdateConfigResponse response = new AppUpdateConfigResponse();
        response.setAppUpdateConfigEntries(entryList);
        response.setStatus(new StatusResponse(1, "", StatusResponse.Type.SUCCESS, 1));
        return response;
    }

    @Cacheable(value = "configs")
    @RequestMapping(value = "/getConfig", method = RequestMethod.GET)
    public AppUpdateConfigResponse getConfig(@RequestHeader HttpHeaders headers) {
        AppUpdateConfigResponse finalResponse = new AppUpdateConfigResponse();
        try {
            AppUpdateConfigEntry entry = ((AppConfigService) service).getConfig();
            finalResponse.setAppUpdateConfigEntries(Collections.singletonList(entry));
            finalResponse.setStatus(new StatusResponse(12, "App Config is retrieved successfully", StatusResponse.Type.SUCCESS, 1));
        } catch (Exception e) {
            finalResponse.setStatus(new StatusResponse(13, "Error Occurred", StatusResponse.Type.ERROR, 1));
        }
        return finalResponse;
    }

    @Cacheable(value = "getConfigByClient")
    @RequestMapping(value = "/getConfigByClient", method = RequestMethod.GET)
    public AppUpdateConfigResponse getConfigByClient() {
        AppUpdateConfigResponse finalResponse = new AppUpdateConfigResponse();
        try {
            AppUpdateConfigEntry entry = ((AppConfigService) service).getConfigByRestClient();
            finalResponse.setAppUpdateConfigEntries(Collections.singletonList(entry));
            finalResponse.setStatus(new StatusResponse(12, "App Config is retrieved successfully", StatusResponse.Type.SUCCESS, 1));
        } catch (Exception e) {
            finalResponse.setStatus(new StatusResponse(13, "Error Occurred", StatusResponse.Type.ERROR, 1));
        }
        return finalResponse;
    }

    @RequestMapping(value = "/getConfigByVersionNo/{versionNo}", method = RequestMethod.GET)
    public AppUpdateConfigEntity getConfigByEntity(@PathVariable Long versionNo) {
        return ((AppConfigService) service).getConfigByVersionNo(versionNo);
    }
}
