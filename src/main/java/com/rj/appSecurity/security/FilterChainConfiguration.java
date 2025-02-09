package com.rj.appSecurity.security;

import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.ProviderManager;
import org.springframework.security.config.annotation.method.configuration.EnableMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.provisioning.InMemoryUserDetailsManager;
import org.springframework.security.web.SecurityFilterChain;

@Configuration
@EnableWebSecurity
@EnableMethodSecurity
@RequiredArgsConstructor
public class FilterChainConfiguration {

    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
        return http
                .csrf(AbstractHttpConfigurer::disable)
                .authorizeHttpRequests(request ->
                        request.requestMatchers("user/login").permitAll()
                                .anyRequest().authenticated())
                .build();
    }

    @Bean
    public UserDetailsService userDetailsService() {
        var user1 = User.withDefaultPasswordEncoder()
                .username("user1")
                .password("password1")
                .roles("USER")
                .build();
        var user2 = User.withDefaultPasswordEncoder()
                .username("user2")
                .password("password2")
                .roles("USER")
                .build();
        return new InMemoryUserDetailsManager(user1, user2);
    }

//    @Bean
//    public InMemoryUserDetailsManager inMemoryUserDetailsManager() {
//        return new InMemoryUserDetailsManager(
//                User.withUsername("saeed").password("123").roles("USER").build(),
//                User.withUsername("saeed2").password("good").roles("USER").build()
//        );
//    }


    @Bean
    public AuthenticationManager authenticationManager(UserDetailsService userDetailsService) {
        MyOwnAuthenticationProvider myOwnAuthenticationProvider = new MyOwnAuthenticationProvider(userDetailsService);
        return new ProviderManager(myOwnAuthenticationProvider);
    }

}
