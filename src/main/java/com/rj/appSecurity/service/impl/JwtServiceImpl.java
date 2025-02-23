package com.rj.appSecurity.service.impl;

import com.rj.appSecurity.constant.Constants;
import com.rj.appSecurity.domain.authenticationDto.TokenDataDto;
import com.rj.appSecurity.domain.authenticationDto.TokenDto;
import com.rj.appSecurity.domain.authenticationDto.UserDto;
import com.rj.appSecurity.enumeration.TokenType;
import com.rj.appSecurity.security.JwtConfiguration;
import com.rj.appSecurity.service.JwtService;
import com.rj.appSecurity.service.UserService;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.io.Decoders;
import io.jsonwebtoken.security.Keys;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.stereotype.Service;

import javax.crypto.SecretKey;
import java.util.List;
import java.util.Optional;
import java.util.StringJoiner;
import java.util.function.Function;
import java.util.function.Supplier;

import static com.rj.appSecurity.constant.Constants.*;
import static org.springframework.security.core.authority.AuthorityUtils.commaSeparatedStringToAuthorityList;

@Service
@RequiredArgsConstructor
@Slf4j
public class JwtServiceImpl extends JwtConfiguration implements JwtService {

    private final UserService userService;
    private final Supplier<SecretKey> key = () -> Keys.hmacShaKeyFor(Decoders.BASE64.decode(getSecret()));
    private final Function<String , Claims> claimsFunction = token ->
            Jwts.parser()
                    .verifyWith(key.get())
                    .build()
                    .parseSignedClaims(token)
                    .getPayload();

    private final Function<String ,String > subject = token -> getClaimsValue(token, Claims::getSubject);

    public Function<String , List<GrantedAuthority>> atuhorities = token -> commaSeparatedStringToAuthorityList(
            new StringJoiner(Constants.AUTHORITY_DELIMITER).add(claimsFunction.apply(token).get(AUTHORITIES , String.class)).add(
                    ROLE_PREFIX + claimsFunction.apply(token).get(ROLE , String.class)).toString());

    @Override
    public String createToken(UserDto user, Function<TokenDto, String> tokenFunction) {
        return "";
    }

    @Override
    public Optional<String> extracetToken(HttpServletRequest request, String tokenType) {
        return Optional.empty();
    }

    @Override
    public void addCookie(HttpServletResponse response, UserDto user, TokenType type) {

    }

    @Override
    public <T> T getTokenData(String token, Function<TokenDataDto, T> tokenFunction) {
        return null;
    }
}
