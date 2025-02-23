package com.rj.appSecurity.domain.authenticationDto;


import io.jsonwebtoken.Claims;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;
import org.springframework.security.core.GrantedAuthority;

import java.util.List;

@Setter
@Getter
@Builder
public class TokenDataDto {
    private UserDto user;
    private Claims claims;
    private boolean valid;
    private List<GrantedAuthority> authorities;
}
