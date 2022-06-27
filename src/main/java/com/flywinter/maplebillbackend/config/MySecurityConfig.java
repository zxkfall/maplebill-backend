package com.flywinter.maplebillbackend.config;

import com.flywinter.maplebillbackend.filter.MyOncePerRequestFilter;
import com.flywinter.maplebillbackend.filter.MyAuthenticationProcessingFilter;
import com.flywinter.maplebillbackend.handler.MyAuthenticationEntryPoint;
import com.flywinter.maplebillbackend.handler.MyAuthenticationFailureHandler;
import com.flywinter.maplebillbackend.handler.MyAuthenticationSuccessHandler;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

/**
 * Created by IntelliJ IDEA
 * User:Zhang Xingkun
 * Date:2022/6/22 11:12
 * Description:
 */
@Configuration
@EnableWebSecurity
public class MySecurityConfig {

    private final UserDetailsService userDetailsService;

    public MySecurityConfig(UserDetailsService userDetailsService) {
        this.userDetailsService = userDetailsService;
    }

    @Bean
    public AuthenticationManager authenticationManager(AuthenticationConfiguration authenticationConfiguration) throws Exception {
        return authenticationConfiguration.getAuthenticationManager();
    }

    @Bean
    public MyAuthenticationProcessingFilter myAuthenticationProcessingFilter(HttpSecurity httpSecurity,
                                                                             MyAuthenticationFailureHandler myAuthenticationFailureHandler,
                                                                             MyAuthenticationSuccessHandler myAuthenticationSuccessHandler) throws Exception {
        final var authenticationManager = getAuthenticationManager(httpSecurity);
        final var filter = new MyAuthenticationProcessingFilter();
        filter.setUsernameParameter("email");
        filter.setPasswordParameter("password");
        filter.setAuthenticationManager(authenticationManager);
        filter.setAuthenticationSuccessHandler(myAuthenticationSuccessHandler);
        filter.setAuthenticationFailureHandler(myAuthenticationFailureHandler);
        return filter;
    }

    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity httpSecurity,
                                                   MyOncePerRequestFilter myOncePerRequestFilter,
                                                   MyAuthenticationEntryPoint myAuthenticationEntryPoint) throws Exception {
        httpSecurity.addFilterBefore(myOncePerRequestFilter, UsernamePasswordAuthenticationFilter.class)
                .cors().and().csrf().disable()
                .httpBasic()
                .authenticationEntryPoint(myAuthenticationEntryPoint)
                .and()
                .authorizeRequests()
                .antMatchers("/login", "/signup",
                        "/swagger-ui.html", "/swagger-ui/**",
                        "/v2/api-docs", "/v2/api-docs/**",
                        "/swagger-resources/**", "/webjars/**"
                )
                .permitAll()
                .antMatchers("/**")
                .authenticated()
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
