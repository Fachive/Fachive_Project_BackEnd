package com.facaieve.backend.service.user;

import com.facaieve.backend.entity.user.FollowEntity;
import com.facaieve.backend.entity.user.UserEntity;
import com.facaieve.backend.mapper.exception.BusinessLogicException;
import com.facaieve.backend.mapper.exception.ExceptionCode;
import com.facaieve.backend.repository.user.FollowRepository;
import com.facaieve.backend.repository.user.UserRepository;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;

@Service
@AllArgsConstructor
@Slf4j
public class FollowService {

    FollowRepository followRepository;
    UserRepository userRepository;
    UserService userService;

    @Transactional
    public void saveFollow( Long followingUserEntityId, Long followedUserEntityId){//내가 다른 사람을 팔로우 하기

        FollowEntity newFollowEntity = new FollowEntity();
        UserEntity user1 = userService.findUserEntityById(followingUserEntityId);
        UserEntity user2 =userService.findUserEntityById(followedUserEntityId);
        newFollowEntity.setFollowingUserEntity(user1);// 팔로우를 하려는 나의 id
        newFollowEntity.setFollowedUserEntity(user2);// 내가 팔로우하는 사람

        if(followRepository.findByFollowingUserEntityAndFollowedUserEntity(user1, user2).isPresent()){
            throw new BusinessLogicException(ExceptionCode.ALREADY_FOLLOWED);
        }

        followRepository.save(newFollowEntity);
    }

    @Transactional
    public void unFollow(Long myUserEntityId, Long userEntityIdIFollowed){
        UserEntity user1 = userRepository.findById(myUserEntityId).orElseThrow();
        UserEntity user2 =userRepository.findById(userEntityIdIFollowed).orElseThrow();
        if(followRepository.deleteByFollowingUserEntityAndFollowedUserEntity(user1, user2).equals(1)){
            log.info("정상적으로 삭제되었습니다.");
        }else{
            log.error("잘못된 팔로우 관련 요청입니다. {}",ExceptionCode.NO_FOLLOW_ENTITY_EXIST);
            throw  new BusinessLogicException(ExceptionCode.NO_FOLLOW_ENTITY_EXIST);
        };
    }

    public boolean find(Long myUserEntityId, Long userEntityIdIFollowed) { // 팔로우가 되어있는지를 확인하기위해
        if(followRepository.countByFollowingUserEntityAndFollowedUserEntity(userRepository.findById(myUserEntityId).orElseThrow(), userRepository.findById(userEntityIdIFollowed).orElseThrow()) == 0)
            return false; // 팔로우 안되어있음
        return true; // 되어있음
    }


}
