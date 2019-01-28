package com.abhinav.learn_spring.models.responses;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class StatusResponse implements Serializable {
    private Integer statusCode;
    private String statusMessage = "";
    private Type statusType = Type.SUCCESS;
    private Long totalCount;

    public enum Type {
        ERROR,
        SUCCESS,
        WARNING
    }
}
