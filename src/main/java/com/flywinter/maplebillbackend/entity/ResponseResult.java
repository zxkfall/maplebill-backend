package com.flywinter.maplebillbackend.entity;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * Created by IntelliJ IDEA
 * User:Xingkun Zhang
 * Date:2022/6/24 21:03
 * Description:
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class ResponseResult<T> {

    private int code;
    private String message;
    private T data;

    public static <T> ResponseResult<T> success() {
        return new ResponseResult<>(ResultState.SUCCESS.getCode(), ResultState.SUCCESS.getMessage(), null);
    }

    public static <T> ResponseResult<T> success(T data) {
        return new ResponseResult<>(ResultState.SUCCESS.getCode(), ResultState.SUCCESS.getMessage(), data);
    }

    public static <T> ResponseResult<T> success(ResultState resultState, T data) {
        return new ResponseResult<>(resultState.getCode(), resultState.getMessage(), data);
    }

    public static <T> ResponseResult<T> failure() {
        return new ResponseResult<>(ResultState.FAILURE.getCode(), ResultState.FAILURE.getMessage(), null);
    }

    public static <T> ResponseResult<T> failure(ResultState resultState) {
        return new ResponseResult<>(resultState.getCode(), resultState.getMessage(), null);
    }

    public static <T> ResponseResult<T> failure(ResultState resultState, T data) {
        return new ResponseResult<>(resultState.getCode(), resultState.getMessage(), data);
    }
}
