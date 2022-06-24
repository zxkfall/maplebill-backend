package com.flywinter.maplebillbackend.config;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.flywinter.maplebillbackend.service.MyUserService;
import com.flywinter.maplebillbackend.utils.JWTUtil;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;
import org.springframework.web.filter.OncePerRequestFilter;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.HashMap;
import java.util.Map;

/**
 * Created by IntelliJ IDEA
 * User:Zhang Xingkun
 * Date:2022/6/22 11:12
 * Description:
 */
@Slf4j
@Configuration
@EnableWebSecurity
public class MySecurityConfig {

    @Value("${user-define.token-name}")
    private String tokenName;

    @Autowired
    private UserDetailsService userDetailsService;

    @Autowired
    private MyUserService myUserService;

    @Bean
    public AuthenticationManager authenticationManager(AuthenticationConfiguration authenticationConfiguration) throws Exception {
        return authenticationConfiguration.getAuthenticationManager();
    }

    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity httpSecurity) throws Exception {
        final AuthenticationManager authenticationManager = getAuthenticationManager(httpSecurity);
        httpSecurity
                .addFilterBefore(new OncePerRequestFilter() {
                    @Override
                    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain) throws ServletException, IOException {
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
                                log.info("token:" + token);
                            }
                        }
                        filterChain.doFilter(request, response);
                    }
                }, UsernamePasswordAuthenticationFilter.class)


                .cors().and().csrf().disable()
                .httpBasic()
                .authenticationEntryPoint((request, response, authException) -> {
                    response.setContentType("application/json;charset=utf-8");
                    response.setStatus(HttpServletResponse.SC_FORBIDDEN);
                    PrintWriter out = response.getWriter();
                    Map<String, Object> map = new HashMap<>();
                    map.put("code", 403);
                    map.put("message", "未登录");
                    out.write(new ObjectMapper().writeValueAsString(map));
                    out.flush();
                    out.close();
                }).and()
                .authorizeRequests()
                .antMatchers("/login", "/register")
                .permitAll()
                .antMatchers("/*")
                .authenticated()
                .and()
                .authenticationManager(authenticationManager)
                .formLogin()
                .loginProcessingUrl("/login")
                .usernameParameter("email")
                .passwordParameter("password")
                .successHandler((request, response, authentication) -> {
                    final var user = (UserDetails) authentication.getPrincipal();
                    final var userInfo = myUserService.getUserInfoByEmail(user.getUsername());
                    response.setContentType("application/json;charset=utf-8");
                    HashMap<String, Object> map = new HashMap<>(4);
                    map.put("code", 200);
                    map.put("messsage", "登陆成功");
                    map.put("token", JWTUtil.getToken(userInfo));
                    ObjectMapper objectMapper = new ObjectMapper();
                    String s = objectMapper.writeValueAsString(map);
                    PrintWriter writer = response.getWriter();
                    writer.write(s);
                    writer.flush();
                    writer.close();
                }).failureHandler((request, response, exception) -> {
                    response.setContentType("application/json;charset=utf-8");
                    HashMap<String, Object> map = new HashMap<>(4);
                    map.put("code", 400);
                    map.put("messsage", "登陆失败");
                    ObjectMapper objectMapper = new ObjectMapper();
                    String s = objectMapper.writeValueAsString(map);
                    PrintWriter writer = response.getWriter();
                    writer.write(s);
                    writer.flush();
                    writer.close();
                })
                .and()
                .sessionManagement().disable();
        return httpSecurity.build();
    }

    private AuthenticationManager getAuthenticationManager(HttpSecurity httpSecurity) throws Exception {
        AuthenticationManagerBuilder authenticationManagerBuilder = httpSecurity.getSharedObject(AuthenticationManagerBuilder.class);
        authenticationManagerBuilder.userDetailsService(userDetailsService).passwordEncoder(new BCryptPasswordEncoder());
        return authenticationManagerBuilder.build();
    }

    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }
}
