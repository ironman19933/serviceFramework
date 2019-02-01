package com.abhinav.learn_spring.controllers;

import com.abhinav.learn_spring.codes.ErrorCodes;
import com.abhinav.learn_spring.codes.SuccessCodes;
import com.abhinav.learn_spring.models.entities.BaseEntity;
import com.abhinav.learn_spring.models.entries.BaseEntry;
import com.abhinav.learn_spring.models.responses.BaseResponse;
import com.abhinav.learn_spring.models.responses.StatusResponse;
import com.abhinav.learn_spring.services.BaseService;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

public abstract class BaseController<R extends BaseResponse, M extends BaseEntity, E extends BaseEntry> {

    protected BaseService<M, E> baseService;

    protected BaseService<M, E> getService() {
        return baseService;
    }

    protected abstract R createResponse(E entry);

    @RequestMapping(value = "/findById/{id}", method = RequestMethod.GET)
    public R findById(@PathVariable Long id) {
        R response = createResponse(null);
        try {
            E entry = getService().find(id);
            response = createResponse(entry);
            response.setStatus(new StatusResponse(SuccessCodes.DATA_RETRIEVED_SUCCESSFULLY, 1L));
        } catch (Exception e) {
            response.setStatus(new StatusResponse(ErrorCodes.GENERIC_ERROR_OCCURRED, 1L));
        }
        return response;
    }

    @RequestMapping(value = "/save", method = RequestMethod.POST)
    public R save(@RequestBody E entry) {
        R response = createResponse(null);
        try {
            E newEntry = getService().save(entry);
            response = createResponse(newEntry);
            response.setStatus(new StatusResponse(SuccessCodes.CREATED, 1L));
        } catch (Exception e) {
            response.setStatus(new StatusResponse(ErrorCodes.GENERIC_ERROR_OCCURRED, 1L));
        }
        return response;
    }

    @RequestMapping(value = "/update/{id}", method = RequestMethod.PUT)
    public R update(@RequestBody E entry, @PathVariable Long id) {
        R response = createResponse(null);
        try {
            E newEntry = getService().update(entry, id);
            response = createResponse(newEntry);
            response.setStatus(new StatusResponse(SuccessCodes.CREATED, 1L));
        } catch (Exception e) {
            response.setStatus(new StatusResponse(ErrorCodes.GENERIC_ERROR_OCCURRED.getCode(), e.getMessage(), StatusResponse.Type.ERROR, 1L));
        }
        return response;
    }
}
