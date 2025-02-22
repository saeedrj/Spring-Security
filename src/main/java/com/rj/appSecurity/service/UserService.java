package com.rj.appSecurity.service;

import com.rj.appSecurity.entity.RoleEntity;
import com.rj.appSecurity.enumeration.LoginType;
import org.springframework.stereotype.Repository;

@Repository
public interface UserService {
    void createUser(String firstName, String lastName, String email, String password);
    RoleEntity getRoleName(String name);
    void verifyAccount(String token);
    void updateLoginAttempt(String email , LoginType loginType);
}
