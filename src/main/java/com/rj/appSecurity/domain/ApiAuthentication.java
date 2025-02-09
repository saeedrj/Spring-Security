package com.rj.appSecurity.domain;

import com.rj.appSecurity.domain.authenticationDto.UserDto;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
@RequestMapping("/user")
public class ApiAuthentication {

    private UserDto user;
    private String email;
    private String password;
    private boolean authenticated;

}
