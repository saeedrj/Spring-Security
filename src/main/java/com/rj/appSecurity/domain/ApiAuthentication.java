package com.rj.appSecurity.domain;

import com.rj.appSecurity.domain.authenticationDto.UserDto;
import com.rj.appSecurity.enumeration.Authority;
import com.rj.appSecurity.exception.ApiException;
import lombok.Data;
import lombok.RequiredArgsConstructor;
import org.springframework.security.authentication.AbstractAuthenticationToken;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.AuthorityUtils;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Collection;

public class ApiAuthentication extends AbstractAuthenticationToken {

    private static final String PASSWORD_PROTECTED = "[PASSWORD PROTECTED]";
    private static final String EMAIL_PROTECTED ="[EMAIL PROTECTED]" ;
    private UserDto user;
    private String email;
    private String password;
    private boolean authenticated;

    public ApiAuthentication(Collection<? extends GrantedAuthority> authorities, UserDto user) {
        super(authorities);
        this.user = user;
        this.email= EMAIL_PROTECTED;
        this.password= PASSWORD_PROTECTED;
        this.authenticated = true;
    }


    private ApiAuthentication(String email , String password) {
        super(AuthorityUtils.NO_AUTHORITIES);
        this.email = email;
        this.password = password;
        this.authenticated = false;
    }


    public static ApiAuthentication unauthenticated(String email , String password) {
    return new ApiAuthentication(email , password);
    }

    public static ApiAuthentication authentication(Collection<? extends GrantedAuthority> authorities, UserDto user) {
        return new ApiAuthentication(authorities,user);
    }

    @Override
    public boolean isAuthenticated() {
        return this.authenticated;
    }

    @Override
    public void setAuthenticated(boolean authenticated) {
        throw new ApiException("you cannot set authentication");
    }


    @Override
    public Object getCredentials() {
        return PASSWORD_PROTECTED;
    }

    @Override
    public Object getPrincipal() {
        return this.user;
    }

    public String getPassword() {
        return this.password;
    }
    public String getEmail() {
        return this.email;
    }
}
