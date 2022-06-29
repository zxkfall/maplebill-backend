package com.flywinter.maplebillbackend.entity;

/**
 * Created by IntelliJ IDEA
 * User:Xingkun Zhang
 * Date:2022/6/24 21:22
 * Description:
 */
public enum ResultState {
    SUCCESS(200, "success"),
    LOGIN_SUCCESS(200, "login success"),
    REGISTER_SUCCESS(201, "register success"),
    LOGIN_FAILURE(401, "login failure"),
    NOT_LOGIN(403, "not login"),
    HAS_REGISTERED(405, "has registered"),
    SERVE_ERROR(500, "serve error"),
    BAD_REQUEST(400, "bad request"),
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
