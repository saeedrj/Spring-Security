package com.rj.appSecurity.service.impl;

import com.rj.appSecurity.cache.CacheStore;
import com.rj.appSecurity.domain.RequestContext;
import com.rj.appSecurity.domain.authenticationDto.UserDto;
import com.rj.appSecurity.entity.ConfirmationEntity;
import com.rj.appSecurity.entity.CredentialEntity;
import com.rj.appSecurity.entity.RoleEntity;
import com.rj.appSecurity.entity.UserEntity;
import com.rj.appSecurity.enumeration.Authority;
import com.rj.appSecurity.enumeration.EventType;
import com.rj.appSecurity.enumeration.LoginType;
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

import java.time.LocalDateTime;
import java.util.Map;

import static com.rj.appSecurity.utils.UserUtils.createUserEntity;
import static com.rj.appSecurity.utils.UserUtils.fromUserEntity;

@Service
@Transactional(rollbackOn = Exception.class)
@RequiredArgsConstructor
@Slf4j
public class UserServiceImpl implements UserService {
    private final UserRepository userRepository;
    private final RoleRepository roleRepository;
    private final CredentialRepository credentialRepository;
    private final ConfirmationRepository confirmationRepository;
    private final CacheStore<String, Integer> userCache;
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
    public void verifyAccount(String key) {
        ConfirmationEntity confirmEntity = getUserConfirm(key);
        UserEntity userEntity = getUserEntityByEmail(confirmEntity.getUserEntity().getEmail());
        userEntity.setEnabled(true);
        userRepository.save(userEntity);
        confirmationRepository.delete(confirmEntity);
    }

    private UserEntity getUserEntityByEmail(String email) {
        var userByEmail = userRepository.findByEmailIgnoreCase(email);
        return userByEmail.orElseThrow(() -> new ApiException("User not found"));
    }

    private ConfirmationEntity getUserConfirm(String key) {
        return confirmationRepository.findByKey(key).orElse(null);
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

    @Override
    public void updateLoginAttempt(String email, LoginType loginType) {
        var userEntity = getUserEntityByEmail(email);
        RequestContext.setUserId(userEntity.getId());
        switch (loginType) {
            case LOGIN_ATTEMPT -> {
                if (userCache.get(userEntity.getEmail()) == null) {
                    userEntity.setLoginAttempts(0);
                    userEntity.setAccountNonLocked(false);
                }
                userEntity.setLoginAttempts(userEntity.getLoginAttempts() + 1);
                userCache.put(userEntity.getEmail(), userEntity.getLoginAttempts());
                if (userCache.get(userEntity.getEmail()) > 5) {
                    userEntity.setAccountNonLocked(false);
                }
            }
            case LOGOUT_SUCCESS -> {
                userEntity.setLoginAttempts(0);
                userEntity.setAccountNonLocked(true);
                userEntity.setLastLogin(LocalDateTime.now());
                userCache.evict(userEntity.getEmail());
            }
        }
        userRepository.save(userEntity);
    }

    @Override
    public UserDto getUserByUserId(String userId) {
        var userEntity = userRepository.findUserEntityByUserId(userId).orElseThrow(() -> new ApiException("User not found"));
        return fromUserEntity(userEntity,userEntity.getRole(),getUserCredentialById(userEntity.getId()));
    }

    @Override
    public UserDto getUserByEmail(String email) {
        UserEntity userEntity = getUserEntityByEmail(email);
        return fromUserEntity(userEntity,userEntity.getRole(),getUserCredentialById(userEntity.getId()));
    }



    @Override
    public CredentialEntity getUserCredentialById(Long userID) {
      var CredentialBy  = credentialRepository.getCredentialEntityByUserEntityId(userID);
        return CredentialBy.orElseThrow(() -> new ApiException("Unable to find user Credential"));
    }
}
