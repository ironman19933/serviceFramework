package com.abhinav.learn_spring.models.responses;

import com.abhinav.learn_spring.codes.StatusCode;
import com.abhinav.learn_spring.codes.SuccessCodes;
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

    public StatusResponse(StatusCode statusCode, Type statusType, Long totalCount) {
        this.statusCode = statusCode.getCode();
        this.statusMessage = statusCode.getMessage();
        this.statusType = statusType;
        if (statusCode instanceof SuccessCodes) {
            this.statusType = Type.SUCCESS;
        } else {
            this.statusType = Type.ERROR;
        }
        this.totalCount = totalCount;
    }

    public StatusResponse(StatusCode statusCode, Long totalCount) {
        this.statusCode = statusCode.getCode();
        this.statusMessage = statusCode.getMessage();
        if (statusCode instanceof SuccessCodes) {
            this.statusType = Type.SUCCESS;
        } else {
            this.statusType = Type.ERROR;
        }
        this.totalCount = totalCount;
    }
}
