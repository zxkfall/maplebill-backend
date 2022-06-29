package com.flywinter.maplebillbackend.handler;

import com.flywinter.maplebillbackend.entity.ResponseResult;
import com.flywinter.maplebillbackend.entity.ResultState;
import com.flywinter.maplebillbackend.exception.RegisterFailedException;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import java.util.stream.Collectors;

/**
 * Created by IntelliJ IDEA
 * User:Xingkun Zhang
 * Date:2022/6/25 13:44
 * Description:
 */
@RestControllerAdvice
public class GlobalExceptionHandler {

    @ExceptionHandler(MethodArgumentNotValidException.class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    public ResponseResult<String> handle(MethodArgumentNotValidException e) {
        final var collect = e.getBindingResult().getFieldErrors()
                .stream()
                .map(fieldError -> fieldError.getField()
                        + ": " + fieldError.getDefaultMessage()).collect(Collectors.joining());
        return ResponseResult.failure(400, collect);
    }

    @ExceptionHandler(RegisterFailedException.class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    public ResponseResult<String> handle(RegisterFailedException e) {
        return ResponseResult.failure(ResultState.HAS_REGISTERED, e.getMessage());
    }

    @ExceptionHandler(IllegalArgumentException.class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    public ResponseResult<String> handle(IllegalArgumentException e) {
        return ResponseResult.failure(ResultState.BAD_REQUEST, e.getMessage());
    }


    @ExceptionHandler(Exception.class)
    @ResponseStatus(HttpStatus.INTERNAL_SERVER_ERROR)
    public ResponseResult<String> handle(Exception e) {
        return ResponseResult.failure(ResultState.SERVE_ERROR, e.getMessage());
    }
}
