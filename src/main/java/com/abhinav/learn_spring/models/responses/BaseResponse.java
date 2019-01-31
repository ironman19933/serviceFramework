package com.abhinav.learn_spring.models.responses;

import lombok.Data;

import java.io.Serializable;


@Data
public class BaseResponse implements Serializable {
    private StatusResponse status;
}
