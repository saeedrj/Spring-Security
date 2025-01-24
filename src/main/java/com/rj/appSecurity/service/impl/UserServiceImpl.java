package com.rj.appSecurity.service.impl;

import com.rj.appSecurity.entity.ConfirmationEntity;
import com.rj.appSecurity.entity.CredentialEntity;
import com.rj.appSecurity.entity.RoleEntity;
import com.rj.appSecurity.entity.UserEntity;
import com.rj.appSecurity.enumeration.Authority;
import com.rj.appSecurity.enumeration.EventType;
import com.rj.appSecurity.event.UserEvent;
import com.rj.appSecurity.exception.ApiException;
import com.rj.appSecurity.repository.ConfirmationRepository;
import com.rj.appSecurity.repository.CredentialRepository;
import com.rj.appSecurity.repository.RoleRepository;
import com.rj.appSecurity.repository.UserRepository;
import com.rj.appSecurity.service.UserService;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.stereotype.Service;

import java.util.Map;

import static com.rj.appSecurity.utils.UserUtils.createUserEntity;

@Service
@Transactional(rollbackOn = Exception.class)
@RequiredArgsConstructor
@Slf4j
public class UserServiceImpl implements UserService {
    private final UserRepository userRepository;
    private final RoleRepository roleRepository;
    private final CredentialRepository credentialRepository;
    private final ConfirmationRepository confirmationRepository;

    //    private final PasswordEncoder passwordEncoder;
    private final ApplicationEventPublisher publisher;

    @Override
    public void createUser(String firstName, String lastName, String email, String password) {
        UserEntity userEntity = userRepository.save(createNewUser(firstName, lastName, email, password));
        var credentialEntity = new CredentialEntity(userEntity, password);
        credentialRepository.save(credentialEntity);
        var confirmationEntity = new ConfirmationEntity(userEntity);
        confirmationRepository.save(confirmationEntity);
        publisher.publishEvent(new UserEvent(userEntity, EventType.REGISTRATION, Map.of("key", confirmationEntity.getKey())));
    }

    @Override
    public RoleEntity getRoleName(String name) {
        var role = roleRepository.findByNameIgnoreCase(name);
        return role.orElseThrow(() -> new ApiException("Role not found"));
    }

    private UserEntity createNewUser(String firstName, String lastName, String email, String password) {
        var role = getRoleName(Authority.USER.name());
        return createUserEntity(firstName, lastName, email, role);
    }
}
