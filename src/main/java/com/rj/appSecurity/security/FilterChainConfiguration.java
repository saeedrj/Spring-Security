package com.rj.appSecurity.security;

import com.rj.appSecurity.repository.CredentialRepository;
import com.rj.appSecurity.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.ProviderManager;
import org.springframework.security.config.annotation.method.configuration.EnableMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.web.SecurityFilterChain;

@Configuration
@EnableWebSecurity
@EnableMethodSecurity
@RequiredArgsConstructor
public class FilterChainConfiguration {

    private final UserRepository userRepository;
    private final CredentialRepository credentialRepository;

    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
        return http
                .csrf(AbstractHttpConfigurer::disable)
                .authorizeHttpRequests(request ->
                        request.requestMatchers("user/login", "user/register", "user/verify/account", "user/**").permitAll()
                                .anyRequest().authenticated())
                .build();
    }

    @Bean //TODO
    public UserDetailsService userDetailsService() {
        return new MyUserDetailsService(userRepository, credentialRepository);
    }

    @Bean
    public AuthenticationManager authenticationManager(UserDetailsService userDetailsService) {
        ApiAuthenticationProvider apiAuthenticationProvider = new ApiAuthenticationProvider(userDetailsService);
        return new ProviderManager(apiAuthenticationProvider);
    }

}
