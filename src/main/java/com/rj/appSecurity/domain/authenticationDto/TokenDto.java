package com.rj.appSecurity.domain.authenticationDto;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

@Builder
@Getter
@Setter
public class TokenDto {
    private String access;
    private String refresh;

}
