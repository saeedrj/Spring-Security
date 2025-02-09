package com.rj.appSecurity.utils;

import com.rj.appSecurity.entity.RoleEntity;
import com.rj.appSecurity.entity.UserEntity;
import java.time.LocalDateTime;
import java.util.UUID;
import static org.apache.commons.lang3.StringUtils.EMPTY;

public class UserUtils {

    public static UserEntity createUserEntity(String firstName , String lastName , String  email, RoleEntity role) {
        return UserEntity.builder()
                .userId(UUID.randomUUID().toString())
                .firstName(firstName)
                .lastName(lastName)
                .email(email)
                .lastLogin(LocalDateTime.now())
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
}
