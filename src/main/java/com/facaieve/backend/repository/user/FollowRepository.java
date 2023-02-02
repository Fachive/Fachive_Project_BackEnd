package com.facaieve.backend.repository.user;

import com.facaieve.backend.entity.user.FollowEntity;
import com.facaieve.backend.entity.user.UserEntity;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import java.util.*;
@Repository
public interface FollowRepository extends JpaRepository<FollowEntity, Long> {

    Integer deleteByFollowingUserEntityAndFollowedUserEntity(UserEntity followdUserEntity, UserEntity followingUserEntity);

    int countByFollowingUserEntityAndFollowedUserEntity(UserEntity userEntity1, UserEntity userEntity2); // 팔로우 되어있는지 count하는 메서드

    Optional<FollowEntity> findByFollowingUserEntityAndFollowedUserEntity(UserEntity followdUserEntity, UserEntity followingUserEntity);
    List<FollowEntity> findByFollowingUserEntity(UserEntity userEntity,
                                                 Pageable pageable);

    List<FollowEntity> findByFollowedUserEntity(UserEntity userEntity,
                                                   Pageable pageable);
}
