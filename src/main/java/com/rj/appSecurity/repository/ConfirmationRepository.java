package com.rj.appSecurity.repository;

import com.rj.appSecurity.entity.ConfirmationEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface ConfirmationRepository extends JpaRepository<ConfirmationEntity, Long> {
  Optional<ConfirmationEntity> findByKey(String key);
  Optional<ConfirmationEntity> findByUserEntity_Id(Long userEntity_Id);
}