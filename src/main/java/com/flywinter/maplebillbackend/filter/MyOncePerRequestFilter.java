package com.flywinter.maplebillbackend.filter;

import com.flywinter.maplebillbackend.utils.JWTUtil;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.lang.NonNull;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;
import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

/**
 * Created by IntelliJ IDEA
 * User:Xingkun Zhang
 * Date:2022/6/24 22:17
 * Description:
 */
@Slf4j
@Component
public class MyOncePerRequestFilter extends OncePerRequestFilter {

    @Value("${user-define.token-name}")
    private String tokenName;

    private final UserDetailsService userDetailsService;

    public MyOncePerRequestFilter(UserDetailsService userDetailsService) {
        this.userDetailsService = userDetailsService;
    }

    @Override
    protected void doFilterInternal(HttpServletRequest request, @NonNull HttpServletResponse response,@NonNull FilterChain filterChain) throws ServletException, IOException {
        //不能含有下划线等特殊值
        final var token = request.getHeader(tokenName);
        if (token != null) {
            final var email = JWTUtil.getEmail(token);
            final var user = userDetailsService.loadUserByUsername(email);
            if (user != null) {
                JWTUtil.verify(token, user.getPassword());
                final var authenticationToken = new UsernamePasswordAuthenticationToken(user, null, user.getAuthorities());
                authenticationToken.setDetails(new WebAuthenticationDetailsSource().buildDetails(request));
                SecurityContextHolder.getContext().setAuthentication(authenticationToken);
            }
        }
        filterChain.doFilter(request, response);
    }
}
