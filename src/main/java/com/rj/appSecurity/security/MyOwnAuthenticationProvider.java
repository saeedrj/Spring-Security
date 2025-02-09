package com.rj.appSecurity.security;

import lombok.RequiredArgsConstructor;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class MyOwnAuthenticationProvider implements AuthenticationProvider {

    private final UserDetailsService userDetailsService;

    @Override
    public Authentication authenticate(Authentication authentication) throws AuthenticationException {
        var user = (UsernamePasswordAuthenticationToken) authentication;
        var userFromDb = userDetailsService.loadUserByUsername(user.getPrincipal().toString());
        var password =(String) user.getCredentials();
        if (password.equals(userFromDb.getPassword())) {
            return new UsernamePasswordAuthenticationToken(userFromDb, "[PASSWORD PROTECTED]", userFromDb.getAuthorities());
        }
        throw new BadCredentialsException("Unable to authenticate user");
    }

    @Override
    public boolean supports(Class<?> authentication) {
        return UsernamePasswordAuthenticationToken.class.isAssignableFrom(authentication);
    }
}
