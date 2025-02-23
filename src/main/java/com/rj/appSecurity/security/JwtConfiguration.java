package com.rj.appSecurity.security;

import lombok.Getter;
import org.springframework.beans.factory.annotation.Value;


@Getter
public class JwtConfiguration {

    @Value("${jwt.expiration}")
    private Long expiration;

    @Value("${jwt.secret}")
    private String secret;

}
