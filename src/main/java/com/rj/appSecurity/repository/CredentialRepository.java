package com.rj.appSecurity.repository;

import com.rj.appSecurity.entity.CredentialEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface CredentialRepository extends JpaRepository<CredentialEntity, Long> {
    //todo
    @Query("SELECT c.password FROM CredentialEntity c WHERE c.userEntity.id = :userId")
    String findPasswordByUserEntityId(@Param("userId") Long userId);

    Optional<CredentialEntity> getCredentialEntityByUserEntityId(Long userId);
}