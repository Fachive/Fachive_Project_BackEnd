package com.facaieve.backend.repository;

import com.facaieve.backend.entity.FollowEntity;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.stereotype.Repository;

@Repository
public interface FollowRepository extends JpaRepository<FollowEntity, Long> {

    void deleteByFollowingUserEntityIdAndFollowedUserEntityId(long followingUserEntityId, long followedUserEntityId);

    int countByFollowingUserEntityIdAndFollowedUserEntityId(long id, long userId); // 팔로우 되어있는지 count하는 메서드


    Page<FollowEntity> findAllByFollowingUserEntityId(long followingUserEntityId,
                                                      Pageable pageable);

    Page<FollowEntity> findAllByFollowedUserEntityId(long followedUserEntityId,
                                                      Pageable pageable);
}
