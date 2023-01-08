package com.facaieve.backend.repository.user;

import com.facaieve.backend.entity.comment.FundingCommentEntity;
import com.facaieve.backend.entity.user.UserEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface UserRepository extends JpaRepository<UserEntity,Long> {

    UserEntity findUserEntityByUserEntityId(Long userEntityId);
    boolean existsByEmail(String email);
    UserEntity findUserEntityByEmailAndPassword(String email, String password);
}
