package com.livspace.service_framework.models.responses;

import lombok.Data;

import java.io.Serializable;


@Data
public class BaseResponse implements Serializable {
    private StatusResponse status;
}
