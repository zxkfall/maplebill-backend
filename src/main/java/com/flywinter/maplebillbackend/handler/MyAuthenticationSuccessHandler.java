package com.flywinter.maplebillbackend.handler;

import com.flywinter.maplebillbackend.entity.ResponseResult;
import com.flywinter.maplebillbackend.entity.ResultState;
import com.flywinter.maplebillbackend.service.MyUserService;
import com.flywinter.maplebillbackend.utils.JWTUtil;
import com.flywinter.maplebillbackend.utils.ResponseUtil;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.web.authentication.AuthenticationSuccessHandler;
import org.springframework.stereotype.Component;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

/**
 * Created by IntelliJ IDEA
 * User:Xingkun Zhang
 * Date:2022/6/24 22:28
 * Description:
 */
@Component
public class MyAuthenticationSuccessHandler implements AuthenticationSuccessHandler {

    private final MyUserService myUserService;

    public MyAuthenticationSuccessHandler(MyUserService myUserService) {
        this.myUserService = myUserService;
    }

    @Override
    public void onAuthenticationSuccess(HttpServletRequest request, HttpServletResponse response, Authentication authentication) throws IOException {
        final var user = (UserDetails) authentication.getPrincipal();
        final var userInfo = myUserService.getUserInfoByEmail(user.getUsername());
        final ResponseResult<Object> responseResult = ResponseResult.success(ResultState.LOGIN_SUCCESS, JWTUtil.getToken(userInfo));
        ResponseUtil.writeResponseResult(response, responseResult);
    }
}
