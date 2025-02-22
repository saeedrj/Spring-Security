package com.rj.appSecurity.security;

import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.rj.appSecurity.domain.ApiAuthentication;
import com.rj.appSecurity.domain.userDto.LoginRequestDto;
import com.rj.appSecurity.enumeration.LoginType;
import com.rj.appSecurity.service.UserService;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.authentication.AbstractAuthenticationProcessingFilter;
import org.springframework.security.web.util.matcher.AntPathRequestMatcher;

import java.io.IOException;

import static com.rj.appSecurity.utils.RequestUtils.handleErrorResponse;
import static org.springframework.http.HttpMethod.POST;

@Slf4j
public class AuthenticationFilter extends AbstractAuthenticationProcessingFilter {

    private final UserService userService;
    private final JwtService jwtService;


    public AuthenticationFilter(AuthenticationManager authenticationManager , UserService userService, JwtService jwtService) {
        super(new AntPathRequestMatcher("user/login",POST.name()), authenticationManager);
        this.userService = userService;
        this.jwtService = jwtService;
    }

    @Override
    public Authentication attemptAuthentication(HttpServletRequest request, HttpServletResponse response) throws AuthenticationException, IOException, ServletException {
       try {
         var user = new ObjectMapper().configure(JsonParser.Feature.AUTO_CLOSE_SOURCE,true).readValue(request.getInputStream(), LoginRequestDto.class);
         userService.updateLoginAttempt(user.getEmail(), LoginType.LOGIN_ATTEMPT);
         var authentication = ApiAuthentication.unauthenticated(user.getPassword(),user.getPassword());
         return getAuthenticationManager().authenticate(authentication);
       }catch (Exception e) {
           log.error(e.getMessage());
           handleErrorResponse(request,response,e);
           return null;
       }
    }

    @Override
    protected void successfulAuthentication(HttpServletRequest request, HttpServletResponse response, FilterChain chain, Authentication authentication) throws IOException, ServletException {
        super.successfulAuthentication(request, response, chain, authentication);
    }


}
