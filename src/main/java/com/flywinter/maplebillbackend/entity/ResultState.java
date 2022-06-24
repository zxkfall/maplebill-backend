package com.flywinter.maplebillbackend.entity;

/**
 * Created by IntelliJ IDEA
 * User:Xingkun Zhang
 * Date:2022/6/24 21:22
 * Description:
 */
public enum ResultState {
    SUCCESS(200, "success"),
    FAILURE(400, "failure");

    private final int code;
    private final String message;

    ResultState(int code, String message) {
        this.code = code;
        this.message = message;
    }

    public int getCode() {
        return code;
    }

    public String getMessage() {
        return message;
    }
}
