package com.flywinter.maplebillbackend.exception;

/**
 * Created by IntelliJ IDEA
 * User:Xingkun Zhang
 * Date:2022/6/25 14:18
 * Description:
 */
public class RegisterFailedException extends RuntimeException {
    public RegisterFailedException(String message) {
        super(message);
    }
}
