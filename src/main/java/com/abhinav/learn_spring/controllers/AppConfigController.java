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
public class AppConfigController {
    @Autowired
    private AppConfigService appConfigService;
    @Autowired
    private RabbitService rabbitService;

    @RequestMapping(value = "/create", method = RequestMethod.POST)
    public AppUpdateConfigResponse persistUser(@RequestBody AppUpdateConfigEntry request) {
        AppUpdateConfigResponse finalResponse = new AppUpdateConfigResponse();
        try {
            AppUpdateConfigEntry entry = appConfigService.create(request);
            finalResponse.setAppUpdateConfigEntry(entry);
            finalResponse.setStatus(new StatusResponse(12, "App Config is saved successfully", StatusResponse.Type.SUCCESS, 1L));
        } catch (Exception e) {
            finalResponse.setStatus(new StatusResponse(13, "Error Occurred", StatusResponse.Type.ERROR, 1L));
        }
        return finalResponse;
    }

    @Cacheable(value = "configs")
    @RequestMapping(value = "/getConfig", method = RequestMethod.GET)
    public AppUpdateConfigResponse getConfig() {
        AppUpdateConfigResponse finalResponse = new AppUpdateConfigResponse();
        try {
            AppUpdateConfigEntry entry = appConfigService.getConfig();
            finalResponse.setAppUpdateConfigEntry(entry);
            finalResponse.setStatus(new StatusResponse(12, "App Config is retrieved successfully", StatusResponse.Type.SUCCESS, 1L));
        } catch (Exception e) {
            finalResponse.setStatus(new StatusResponse(13, "Error Occurred", StatusResponse.Type.ERROR, 1L));
        }
        //rabbitService.sendMessage("learn_spring", "learn_spring_routing_key", finalResponse);
        return finalResponse;
    }

    @RequestMapping(value = "/getConfigByClient", method = RequestMethod.GET)
    public AppUpdateConfigResponse getConfigByClient() {
        AppUpdateConfigResponse finalResponse = new AppUpdateConfigResponse();
        try {
            AppUpdateConfigEntry entry = appConfigService.getConfigByEntry();
            finalResponse.setAppUpdateConfigEntry(entry);
            finalResponse.setStatus(new StatusResponse(12, "App Config is retrieved successfully", StatusResponse.Type.SUCCESS, 1L));
        } catch (Exception e) {
            finalResponse.setStatus(new StatusResponse(13, "Error Occurred", StatusResponse.Type.ERROR, 1L));
        }
        //rabbitService.sendMessage("learn_spring", "learn_spring_routing_key", finalResponse);
        return finalResponse;
    }

    @RequestMapping(value = "/getConfigByVersionNo/{versionNo}", method = RequestMethod.GET)
    public AppUpdateConfigEntity getConfigByEntity(@PathVariable Long versionNo) {
        return appConfigService.getConfigByVersionNo(versionNo);
    }
}
