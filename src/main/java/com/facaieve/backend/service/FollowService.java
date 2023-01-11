package com.facaieve.backend.service;

import com.facaieve.backend.entity.FollowEntity;
import com.facaieve.backend.repository.FollowRepository;
import com.facaieve.backend.service.user.UserService;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

@Service
@AllArgsConstructor
@Slf4j
public class FollowService {

    FollowRepository followRepository;
    UserService userService;


    public void saveFollow(long toFollowUserEntityId, long followedUserEntityId){//내가 다른 사람을 팔로우 하기

        FollowEntity newFollowEntity = new FollowEntity();
        newFollowEntity.setUserFollower(userService.findUserEntityById(followedUserEntityId));// 내가 팔로우하려는 유저의 ID
        newFollowEntity.setUserFollowing(userService.findUserEntityById(toFollowUserEntityId));// 팔로우를 하는 나의 유저 ID

        followRepository.save(newFollowEntity);
    }


    public void unFollow(long myUserEntityId, long userEntityIdIFollowed){
        followRepository.deleteByFollowingUserEntityIdAndFollowedUserEntityId(myUserEntityId, userEntityIdIFollowed);
    }

    public boolean find(long myUserEntityId, long userEntityIdIFollowed) { // 팔로우가 되어있는지를 확인하기위해
        if(followRepository.countByFollowingUserEntityIdAndFollowedUserEntityId(myUserEntityId, userEntityIdIFollowed) == 0)
            return false; // 팔로우 안되어있음
        return true; // 되어있음
    }


}
