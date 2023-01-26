package com.facaieve.backend.repository.user;

import java.time.LocalDateTime;
import java.util.*;
import com.facaieve.backend.entity.user.UserEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface UserRepository extends JpaRepository<UserEntity,Long> {

    UserEntity findUserEntityByUserEntityId(Long userEntityId);
    UserEntity findUserEntityByEmailAndPassword(String email, String password);

    List<UserEntity> findUserEntityByModifiedByGreaterThan(LocalDateTime updateTime);

    Optional<UserEntity> findByDisplayName(String displayName);
    Optional<UserEntity> findByEmail(String email);
    boolean existsByDisplayName(String displayName);
    boolean existsByEmail(String email);
}
