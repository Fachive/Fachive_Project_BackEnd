package com.facaieve.backend.service.user;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.*;

import com.facaieve.backend.dto.UserDto;
import com.facaieve.backend.entity.user.user.FollowEntity;
import com.facaieve.backend.entity.user.UserEntity;
import com.facaieve.backend.entity.user.WithdrawalEntity;
import com.facaieve.backend.exception.BusinessLogicException;
import com.facaieve.backend.exception.ExceptionCode;
import com.facaieve.backend.repository.user.FollowRepository;
import com.facaieve.backend.repository.user.UserRepository;
import com.facaieve.backend.repository.user.WithdrawalRepository;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import javax.validation.constraints.NotNull;
import java.util.Optional;
import java.util.stream.Collectors;

import static com.facaieve.backend.Constant.UserActive.UnActive;
import static com.facaieve.backend.Constant.UserActive.Withdrawal;
import static com.facaieve.backend.exception.ExceptionCode.*;

@Slf4j
@Service
@AllArgsConstructor
public class UserService {
    UserRepository userRepository;
    WithdrawalRepository withdrawalRepository;

    FollowRepository followRepository;


    //입력 값으로 들어온 userEntity 저장 그리고 반환 todo 보안 설정 아직 안함
    public UserEntity createUserEntity(@NotNull final UserEntity userEntity) throws BusinessLogicException{


      Set<String> forbiddenWords = new LinkedHashSet<String>();// 금지어, 비속어 필터링 테스트 따로 처리할 필요 있음
        forbiddenWords.add("씨발");
        forbiddenWords.add("개새끼");
        forbiddenWords.add("병신");

      for(String swearWord : forbiddenWords) {
          if(userEntity.getDisplayName().contains(swearWord))
              throw new BusinessLogicException(ExceptionCode.FORBIDDEN_WORD_USED);
      }

        if(userRepository.findByDisplayName(userEntity.getDisplayName()).isPresent()) {
            log.error("회원 가입 중 중복된 이메일이 입력되었습니다. ", new BusinessLogicException(ExceptionCode.DUPLICATE_DISPLAY_NAME));
            throw new BusinessLogicException(ExceptionCode.DUPLICATE_DISPLAY_NAME);//닉네임 중복 확인
        }
        if(userRepository.findByEmail(userEntity.getEmail()).isPresent()) {
            log.error("회원 가입 중 중복된 이메일이 입력되었습니다. ", new BusinessLogicException(ExceptionCode.DUPLICATE_EMAIL));
            throw new BusinessLogicException(ExceptionCode.DUPLICATE_EMAIL);//이메일 중복 확인
        }

        return userRepository.save(userEntity);

    }
    public UserEntity findUserEntityById(long userEntityId){
        return userRepository.findById(userEntityId).orElseThrow(() -> new BusinessLogicException(MEMBER_NOT_FOUND));
    }/*엔티티 식별자(ID)로 유저 확인 */

    public List<UserDto.ResponseUserDto> findAllUserEntityWithPaginationByUpdateTime(int page){/*업데이트 순으로 30개씩 유저 정보를 반환 */

        return userRepository.findAll(PageRequest.of(page, 30, Sort.by("updateTime").descending()))
                .stream()
                .map(UserDto.ResponseUserDto::of)
                .collect(Collectors.toList());
    }

    public UserEntity getByCredentials(final String email, final String password){
        return userRepository.findUserEntityByEmailAndPassword(email, password);
    }
    public void deleteUserEntity(final UserEntity deleteUserEntity){

        UserEntity deletingUserEntity = findUserEntityById(deleteUserEntity.getUserEntityId());//유저 존재하는지 체크
        checkIsPasswordCorrect(deleteUserEntity.getPassword(), deletingUserEntity.getPassword());//확인용 비밀번호가 맞는지 체크
        deletingUserEntity.setUserActive(Withdrawal);//탈퇴처리

        userRepository.save(deletingUserEntity);
        log.info("유저를 탈퇴 처리하였습니다 ");
    }/* 엔티티로 유저 삭제*/
    public UserEntity updateUserEntity(UserEntity patchUserEntity){

        UserEntity newUserEntity = new UserEntity();
        Optional.ofNullable(patchUserEntity.getEmail())
                .ifPresent(newUserEntity::setEmail);
        Optional.ofNullable(patchUserEntity.getState())
                .ifPresent(newUserEntity::setState);
        Optional.ofNullable(patchUserEntity.getCity())
                .ifPresent(newUserEntity::setCity);
        Optional.ofNullable(patchUserEntity.getUserInfo())
                .ifPresent(newUserEntity::setUserInfo);
        Optional.ofNullable(patchUserEntity.getCareer())
                .ifPresent(newUserEntity::setCareer);
        Optional.ofNullable(patchUserEntity.getEducation())
                .ifPresent(newUserEntity::setEducation);
        Optional.ofNullable(patchUserEntity.getCompany())
                .ifPresent(newUserEntity::setCompany);

        return newUserEntity;
    }/*엔티티로 유저 정보 수정하기*/

    public List<Integer> userPostingCount(UserEntity userEntity){// 포트폴리오, 패션픽업, 펀딩 순으로 유저가 작성한 게시물의 수를 List로 반환
        List<Integer> userPostCountList = new ArrayList<>();
        UserEntity foundUserEntity = userRepository.findById(userEntity.getUserEntityId()).orElseThrow();

        userPostCountList.add(foundUserEntity.getPortfolioEntities().size());
        userPostCountList.add(foundUserEntity.getFashionPickupEntities().size());
        userPostCountList.add(foundUserEntity.getFundingEntities().size());

        return userPostCountList;
    }/*엔티티의 유저가 지금까지 작성한 게시글 수 반환환*/

    public void withdrawalUser(UserEntity userEntity){//회원 탈퇴 엔티티 생성
        WithdrawalEntity newWithdrawalEntity = new WithdrawalEntity();
        newWithdrawalEntity.setWithdrawalUserEntity(userEntity);
        newWithdrawalEntity.setUserActive(Withdrawal);

        UserEntity foundUserEntity = findUserEntity(userEntity);
        foundUserEntity.setUserActive(Withdrawal);//유저 엔티티 활동 -> 탈퇴처리

        userRepository.save(foundUserEntity);//탈퇴처리 정보 저장
        withdrawalRepository.save(newWithdrawalEntity);// 휴탈 회원 엔티티 새로 생성
    }

    public void deActivateUser(UserEntity userEntity) throws BusinessLogicException{//휴면 회원 엔티티 생성
        long userID =  userEntity.getUserEntityId();

        WithdrawalEntity newWithdrawalEntity = new WithdrawalEntity();
        newWithdrawalEntity.setWithdrawalUserEntity(userEntity);
        newWithdrawalEntity.setUserActive(Withdrawal);
        /* 휴면 처리 절차 확인 필요  */

        UserEntity foundUserEntity = findUserEntityById(userID);//휴면처리할 유저의 정보 가져오기

        int year = LocalDate.now().getYear();
        int month =  LocalDate.now().getMonthValue();
        LocalDateTime activationDate = LocalDateTime.of(year-2,month,1,0,0 );

        if(foundUserEntity.getUpdateTime().isBefore(activationDate)) {//최근활동 이력 > 현날짜 -2년 -> 휴면처리
            foundUserEntity.setUserActive(UnActive);//유저 엔티티 활동 -> 휴면처리
        }
        else {
            throw new BusinessLogicException(DORMANCY_DURATION_UNDER_2Y);// 휴면 기준이 되지 않는다는 에러 발생(2년)
        }

        userRepository.save(foundUserEntity);//탈퇴처리 정보 저장
        withdrawalRepository.save(newWithdrawalEntity);// 휴탈 회원 엔티티 새로 생성
    }


    public Page<UserDto.FollowUserInfoResponseDto> getUserFollowList(long myUserEntityId, int pageIndex) {// id에 해당하는 유저가 팔로우하는 사용자 목록 반환 메서드
        List<FollowEntity> followingList = followRepository.findByFollowingUserEntity(userRepository.findById(myUserEntityId).orElseThrow(), PageRequest.of(pageIndex, 20, Sort.by("modifiedBy").descending()));

        List<UserDto.FollowUserInfoResponseDto> followList =  followingList.stream()
                        .map(FollowEntity -> UserDto.FollowUserInfoResponseDto.builder()
                                .userEntityId(FollowEntity.getFollowedUserEntity().getUserEntityId())
                                .displayName(FollowEntity.getFollowedUserEntity().getDisplayName())
                                .build())
                .collect(Collectors.toList());

        return new PageImpl<>(followList);
    }
    public Page<UserDto.FollowUserInfoResponseDto> getUserFollowingList(long myUserEntityId, int pageIndex) {// id에 해당하는 유저를 팔로우하는 사용자 목록 반환 메서드
        List<FollowEntity> followingList = followRepository.findByFollowedUserEntity(userRepository.findById(myUserEntityId).orElseThrow(), PageRequest.of(pageIndex, 20, Sort.by("modifiedBy").descending()));

        List<UserDto.FollowUserInfoResponseDto> followList =  followingList.stream()
                .map(FollowEntity -> UserDto.FollowUserInfoResponseDto.builder()
                        .userEntityId(FollowEntity.getFollowedUserEntity().getUserEntityId())
                        .displayName(FollowEntity.getFollowedUserEntity().getDisplayName())
                        .build())
                .collect(Collectors.toList());

        return new PageImpl<>(followList);
    }


    public void checkIsPasswordCorrect(String inputPassword, String realPassword){
        if(inputPassword.equals(realPassword)){
            log.info("확인용 비밀번호가 일치합니다.");
        }
        else {
            log.error("확인용 비밀번호가 불일치합니다.");
           throw  new BusinessLogicException(PASSWORD_IS_WRONG);
        }
    }
}
