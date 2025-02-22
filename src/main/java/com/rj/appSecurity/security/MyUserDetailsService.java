package com.rj.appSecurity.security;


import com.rj.appSecurity.repository.CredentialRepository;
import com.rj.appSecurity.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class MyUserDetailsService implements UserDetailsService {

    private final UserRepository userRepository;
    private final CredentialRepository credentialRepository;

    @Override   //TODO
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        return userRepository.findByEmail(username)
                .map(user -> {
                    String password = credentialRepository.findPasswordByUserEntityId(user.getId());
                    if (password == null) {
                        throw new UsernameNotFoundException("Password not found for user: " + username);
                    }
                    return User.withUsername(user.getEmail())
                            .password(password)
                            .roles(user.getRole().getAuthorities().toString())
                            .build();
                })
                .orElseThrow(() -> new UsernameNotFoundException("User not found: " + username));
    }

}
