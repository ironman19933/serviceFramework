package com.livspace.service_framework.codes;

public enum SuccessCodes implements StatusCode {
    DATA_RETRIEVED_SUCCESSFULLY(101, "Data retrieved Successfully"),
    APP_CONFIG_CREATED_SUCCESSFULLY(102, "App config created successfully"),
    CREATED(200, "Created Successfully");

    Integer code;
    String message;

    SuccessCodes(Integer code, String message) {
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
