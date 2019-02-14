package com.livspace.service_framework.codes;

public enum ErrorCodes implements StatusCode {
    GENERIC_ERROR_OCCURRED(101, "Error Occurred!"),
    NOT_FOUND(102, "Data not found");
    Integer code;
    String message;

    ErrorCodes(Integer code, String message) {
        this.code = code;
        this.message = message;
    }

    public Integer getCode() {
        return code;
    }

    public String getMessage() {
        return message;
    }
}
