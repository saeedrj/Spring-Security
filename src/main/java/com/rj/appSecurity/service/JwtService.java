package com.rj.appSecurity.service;

import com.rj.appSecurity.domain.authenticationDto.TokenDataDto;
import com.rj.appSecurity.domain.authenticationDto.TokenDto;
import com.rj.appSecurity.domain.authenticationDto.UserDto;
import com.rj.appSecurity.enumeration.TokenType;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import java.util.Optional;
import java.util.function.Function;

public interface JwtService {

    String createToken(UserDto user, Function<TokenDto, String> tokenFunction);

    Optional<String> extracetToken(HttpServletRequest request, String tokenType);

    void addCookie(HttpServletResponse response, UserDto user, TokenType type);

    <T> T getTokenData(String token, Function<TokenDataDto, T> tokenFunction);

    void removeCookie(HttpServletRequest request, HttpServletResponse response, String cookieName);
}
