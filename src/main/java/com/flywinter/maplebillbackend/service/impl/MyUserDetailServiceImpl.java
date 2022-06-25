package com.flywinter.maplebillbackend.service.impl;

import com.flywinter.maplebillbackend.service.MyUserService;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by IntelliJ IDEA
 * User:Zhang Xingkun
 * Date:2022/6/22 11:27
 * Description:
 */
@Service
public class MyUserDetailServiceImpl implements UserDetailsService {

    private final MyUserService myUserService;

    public MyUserDetailServiceImpl(MyUserService myUserService) {
        this.myUserService = myUserService;
    }

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        final var userInfo = myUserService.getUserInfoByEmail(username);
        if (userInfo == null) {
            throw new UsernameNotFoundException("用户不存在");
        }
        return new User(userInfo.getEmail(), userInfo.getPassword(), createAuthorities(userInfo.getRoles()));
    }

    private List<SimpleGrantedAuthority> createAuthorities(String roleStr) {
        String[] roles = roleStr.split(",");
        List<SimpleGrantedAuthority> simpleGrantedAuthorities = new ArrayList<>();
        for (String role : roles) {
            simpleGrantedAuthorities.add(new SimpleGrantedAuthority(role));
        }
        return simpleGrantedAuthorities;
    }
}
