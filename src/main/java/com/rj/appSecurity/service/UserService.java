package com.rj.appSecurity.service;

import com.rj.appSecurity.domain.authenticationDto.UserDto;
import com.rj.appSecurity.entity.CredentialEntity;
import com.rj.appSecurity.entity.RoleEntity;
import com.rj.appSecurity.enumeration.LoginType;

public interface UserService {

    void createUser(String firstName, String lastName, String email, String password);

    RoleEntity getRoleName(String name);

    void verifyAccount(String token);

    void updateLoginAttempt(String email, LoginType loginType);

    UserDto getUserByUserId(String userId);

    UserDto getUserByEmail(String email);

    CredentialEntity getUserCredentialById(Long id);
}
