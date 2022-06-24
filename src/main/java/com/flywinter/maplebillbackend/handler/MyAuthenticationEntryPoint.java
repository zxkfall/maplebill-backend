package com.flywinter.maplebillbackend.handler;

import com.flywinter.maplebillbackend.entity.ResponseResult;
import com.flywinter.maplebillbackend.entity.ResultState;
import com.flywinter.maplebillbackend.utils.ResponseUtil;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.AuthenticationEntryPoint;
import org.springframework.stereotype.Component;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

/**
 * Created by IntelliJ IDEA
 * User:Xingkun Zhang
 * Date:2022/6/24 22:30
 * Description:
 */
@Component
public class MyAuthenticationEntryPoint implements AuthenticationEntryPoint {

    @Override
    public void commence(HttpServletRequest request, HttpServletResponse response, AuthenticationException authException) throws IOException {
        response.setStatus(HttpServletResponse.SC_FORBIDDEN);
        final var responseResult = ResponseResult.failure(ResultState.NOT_LOGIN);
        ResponseUtil.writeResponseResult(response, responseResult);
    }
}
