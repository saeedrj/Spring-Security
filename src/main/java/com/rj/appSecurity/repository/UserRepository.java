package com.rj.appSecurity.repository;

import com.rj.appSecurity.entity.UserEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface UserRepository extends JpaRepository<UserEntity, Long> {
    Optional<UserEntity> findByEmailIgnoreCase(String username);
    Optional<UserEntity> findByEmail(String email);
    Optional<UserEntity> findUserEntityByUserId(String id);
}