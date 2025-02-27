package com.rj.appSecurity.utils;

import com.rj.appSecurity.domain.authenticationDto.UserDto;
import com.rj.appSecurity.entity.CredentialEntity;
import com.rj.appSecurity.entity.RoleEntity;
import com.rj.appSecurity.entity.UserEntity;
import org.springframework.beans.BeanUtils;

import java.time.LocalDateTime;
import java.util.UUID;

import static com.rj.appSecurity.constant.Constants.LIMITATIONS;
import static java.time.LocalDateTime.now;
import static org.apache.commons.lang3.StringUtils.EMPTY;

public class UserUtils {

    public static UserEntity createUserEntity(String firstName , String lastName , String  email, RoleEntity role) {
        return UserEntity.builder()
                .userId(UUID.randomUUID().toString())
                .firstName(firstName)
                .lastName(lastName)
                .email(email)
                .lastLogin(now())
                .accountNonExpired(true)
                .accountNonLocked(true)
                .enabled(false)
                .loginAttempts(0)
                .qrCodeSecret(EMPTY)
                .phone(EMPTY)
                .bio(EMPTY)
                .imageUrl("https://cdn-icons-png.flaticon.com/512/149/149071.png")
                .role(role)
                .mfa(false)
                .build();
    }

    public static UserDto fromUserEntity(UserEntity userEntity, RoleEntity role, CredentialEntity userCredentialById) {
       UserDto user = new UserDto();
        BeanUtils.copyProperties(userEntity, user);
        user.setLastLogin(userEntity.getLastLogin().toString());
        user.setCredentialsNonExpired(isCredentialNonExpired(userCredentialById));
        user.setCreateAt(userEntity.getCreatedAt().toString());
        user.setUpdateAt(userEntity.getUpdatedAt().toString());
        user.setRole(role.getName());
        user.setAuthorities(role.getAuthorities().getValue());
        return user;
    }

    private static boolean isCredentialNonExpired(CredentialEntity credentialEntity) {
        return credentialEntity.getUpdatedAt().plusDays(LIMITATIONS).isBefore(now());
    }
}
