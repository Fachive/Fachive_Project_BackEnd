package com.facaieve.backend.repository.user;

import com.facaieve.backend.entity.comment.FundingCommentEntity;
import com.facaieve.backend.entity.user.UserEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface UserRepository extends JpaRepository<UserEntity,Long> {

    UserEntity findUserEntityByUserEntityId(Long userEntityId);
    UserEntity findUserEntityByEmailAndPassword(String email, String password);

    Optional<UserEntity> findByDisplayName(String displayName);
    Optional<UserEntity> findByEmail(String email);
    boolean existsByDisplayName(String displayName);
    boolean existsByEmail(String email);
}
