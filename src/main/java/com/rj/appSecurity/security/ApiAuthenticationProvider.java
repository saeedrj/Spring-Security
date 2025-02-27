package com.rj.appSecurity.security;

import com.rj.appSecurity.domain.ApiAuthentication;
import com.rj.appSecurity.domain.authenticationDto.UserPrincipalDto;
import com.rj.appSecurity.exception.ApiException;
import com.rj.appSecurity.service.UserService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.authentication.*;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Component;

import java.util.function.Consumer;
import java.util.function.Function;

import static com.rj.appSecurity.constant.Constants.LIMITATIONS;
import static com.rj.appSecurity.domain.ApiAuthentication.authentication;
import static java.time.LocalDateTime.now;

@Component
@RequiredArgsConstructor
@Slf4j
public class ApiAuthenticationProvider implements AuthenticationProvider {


    private final UserService userService;
    private final BCryptPasswordEncoder encoder;

    @Override
    public Authentication authenticate(Authentication authentication) throws AuthenticationException {
        var apiAuthentication = authenticationToApiAuthentication.apply(authentication);
        var user = userService.getUserByEmail(apiAuthentication.getEmail());
        if (user != null) {
            var userCredential = userService.getUserCredentialById(user.getId());
//            if (userCredential.getUpdatedAt().minusDays(LIMITATIONS).isAfter(now()))
            if (user.isCredentialsNonExpired())
                throw new ApiException("Credential are expired. Please restart the password.");


            var userPrincipal = new UserPrincipalDto(user, userCredential);
            validAccount.accept(userPrincipal);
            if (encoder.matches(apiAuthentication.getPassword(), userCredential.getPassword())) {
                return authentication(userPrincipal.getAuthorities(), user);
            } else throw new BadCredentialsException("Email or password is incorrect. please try again.");
        }
        throw new ApiException("Unable to authenticate ");

    }


    @Override
    public boolean supports(Class<?> authentication) {
        return ApiAuthentication.class.isAssignableFrom(authentication);
    }

    private final Function<Authentication, ApiAuthentication> authenticationToApiAuthentication = authentication -> (ApiAuthentication) authentication;

    private final Consumer<UserPrincipalDto> validAccount = userPrincipal -> {
        if (userPrincipal.isAccountNonLocked()) throw new LockedException("Account is locked");

        if (userPrincipal.isEnabled()) throw new DisabledException("your account is currently disabled");

        if (userPrincipal.isCredentialsNonExpired())
            throw new CredentialsExpiredException("Credentials are expired. please contact administrator");

        if (userPrincipal.isAccountNonExpired())
            throw new DisabledException("your account is expired. please contact administrator");

    };


}
