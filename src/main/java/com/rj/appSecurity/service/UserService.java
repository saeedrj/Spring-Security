package com.rj.appSecurity.service;

import com.rj.appSecurity.entity.RoleEntity;
import org.springframework.stereotype.Repository;

@Repository
public interface UserService {
    void createUser(String firstName, String lastName, String email, String password);
    RoleEntity getRoleName(String name);


}
