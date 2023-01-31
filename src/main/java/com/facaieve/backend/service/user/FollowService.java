package com.facaieve.backend.service.user;

import com.facaieve.backend.entity.user.FollowEntity;
import com.facaieve.backend.repository.user.FollowRepository;
import com.facaieve.backend.repository.user.UserRepository;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

@Service
@AllArgsConstructor
@Slf4j
public class FollowService {

    FollowRepository followRepository;
    UserRepository userRepository;
    UserService userService;


    public void saveFollow( Long toFollowUserEntityId, Long followedUserEntityId){//내가 다른 사람을 팔로우 하기

        FollowEntity newFollowEntity = new FollowEntity();
        newFollowEntity.setFollowingUserEntity(userService.findUserEntityById(followedUserEntityId));// 팔로우를 하려는 나의 id
        newFollowEntity.setFollowedUserEntity(userService.findUserEntityById(toFollowUserEntityId));// 내가 팔로우하는 사람

        followRepository.save(newFollowEntity);
    }


    public void unFollow(Long myUserEntityId, Long userEntityIdIFollowed){
        followRepository.deleteByFollowingUserEntityAndFollowedUserEntity(userRepository.findById(myUserEntityId).orElseThrow(), userRepository.findById(userEntityIdIFollowed).orElseThrow());
    }

    public boolean find(Long myUserEntityId, Long userEntityIdIFollowed) { // 팔로우가 되어있는지를 확인하기위해
        if(followRepository.countByFollowingUserEntityAndFollowedUserEntity(userRepository.findById(myUserEntityId).orElseThrow(), userRepository.findById(userEntityIdIFollowed).orElseThrow()) == 0)
            return false; // 팔로우 안되어있음
        return true; // 되어있음
    }


}
