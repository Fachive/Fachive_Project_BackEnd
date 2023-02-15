package com.facaieve.backend.service.user;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.*;

import com.facaieve.backend.dto.UserDto;
import com.facaieve.backend.entity.user.FollowEntity;
import com.facaieve.backend.entity.user.UserEntity;
import com.facaieve.backend.entity.user.WithdrawalEntity;
import com.facaieve.backend.mapper.exception.BusinessLogicException;
import com.facaieve.backend.mapper.exception.ExceptionCode;
import com.facaieve.backend.mapper.exception.swearingFilter.BadWordFiltering;
import com.facaieve.backend.mapper.user.UserMapper;
import com.facaieve.backend.repository.user.FollowRepository;
import com.facaieve.backend.repository.user.UserRepository;
import com.facaieve.backend.repository.user.WithdrawalRepository;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import javax.validation.constraints.NotNull;
import java.util.Optional;
import java.util.stream.Collectors;

import static com.facaieve.backend.Constant.UserActive.DeActive;
import static com.facaieve.backend.Constant.UserActive.Withdrawal;
import static com.facaieve.backend.mapper.exception.ExceptionCode.*;

@Slf4j
@Service
@AllArgsConstructor
public class UserService {
    UserRepository userRepository;
    WithdrawalRepository withdrawalRepository;
    UserMapper userMapper;
    FollowRepository followRepository;
    BadWordFiltering badWordFiltering;



    //입력 값으로 들어온 userEntity 저장 그리고 반환 todo 보안 설정 아직 안함
    public UserEntity createUserEntity(@NotNull final UserEntity userEntity) throws BusinessLogicException {
        checkSwearingWord(userEntity.getDisplayName());
        checkDuplicateDisplayName(userEntity.getDisplayName());
        checkDuplicateEmail(userEntity.getEmail());

        log.info("신규 유저를 생성합니다.");

        return userRepository.save(userEntity);
    }

    public UserEntity updateUserEntity(UserEntity patchUserEntity) {

        checkSwearingWord(patchUserEntity.getDisplayName());
        checkDuplicateDisplayName(patchUserEntity.getDisplayName());
        checkDuplicateEmail(patchUserEntity.getEmail());

        UserEntity patchingUserEntity = findUserEntityById(patchUserEntity.getUserEntityId());//유저 확인

        Optional.ofNullable(patchUserEntity.getEmail())
                .ifPresent(patchingUserEntity::setEmail);
        Optional.ofNullable(patchUserEntity.getState())
                .ifPresent(patchingUserEntity::setState);
        Optional.ofNullable(patchUserEntity.getCity())
                .ifPresent(patchingUserEntity::setCity);
        Optional.ofNullable(patchUserEntity.getUserInfo())
                .ifPresent(patchingUserEntity::setUserInfo);
        Optional.ofNullable(patchUserEntity.getCareer())
                .ifPresent(patchingUserEntity::setCareer);
        Optional.ofNullable(patchUserEntity.getEducation())
                .ifPresent(patchingUserEntity::setEducation);
        Optional.ofNullable(patchUserEntity.getCompany())
                .ifPresent(patchingUserEntity::setCompany);

        return userRepository.save(patchingUserEntity);// 수정된 내용으로 유저 엔티티 저장
    }/*엔티티로 유저 정보 수정하기*/

    public UserEntity findUserEntityById(Long userEntityId) {
        return userRepository.findById(userEntityId).orElseThrow(() -> new BusinessLogicException(MEMBER_NOT_FOUND));
    }/*엔티티 식별자(ID)로 유저 확인 */

    public Optional<UserEntity> findByEmail(String email){
        return  userRepository.findByEmail(email);
    }/* constraint 설정으로 만든 userEntity 의 또다른 식별자인 email을 이용해서 특정 userEntity 를 가지고 오는 메소드*/

    public List<UserDto.ResponseUserDto> findAllUserEntityWithPaginationByUpdateTime(int page) {/*업데이트 순으로 30개씩 유저 정보를 반환 */

        return userRepository.findAll(PageRequest.of(page, 30, Sort.by("updateTime").descending()))
                .stream()
                .map(UserDto.ResponseUserDto::of)
                .collect(Collectors.toList());
    }

    public UserEntity getByCredentials(final String email, final String password, final PasswordEncoder passwordEncoder) {
        final UserEntity originalUser = userRepository.findByEmail(email).orElseThrow();
        if(originalUser != null && passwordEncoder.matches(password,originalUser.getPassword())){
            return originalUser;
        }
        return null;
    }

    public void deleteUserEntity(final UserEntity deleteUserEntity) {

        UserEntity deletingUserEntity = findUserEntityById(deleteUserEntity.getUserEntityId());//유저 존재하는지 체크
        checkIsPasswordCorrect(deleteUserEntity.getPassword(), deletingUserEntity.getPassword());//확인용 비밀번호가 맞는지 체크
        deletingUserEntity.setUserActive(Withdrawal);//탈퇴처리

        userRepository.save(deletingUserEntity);
        log.info("유저를 탈퇴 처리하였습니다 ");
    }/* 엔티티로 유저 삭제*/

    public List<Integer> userPostingCount(UserEntity userEntity) {// 포트폴리오, 패션픽업, 펀딩 순으로 유저가 작성한 게시물의 수를 List로 반환
        List<Integer> userPostCountList = new ArrayList<>();
        UserEntity foundUserEntity = userRepository.findById(userEntity.getUserEntityId()).orElseThrow();

        userPostCountList.add(foundUserEntity.getPortfolioEntities().size());
        userPostCountList.add(foundUserEntity.getFashionPickupEntities().size());
        userPostCountList.add(foundUserEntity.getFundingEntities().size());

        return userPostCountList;
    }/*엔티티의 유저가 지금까지 작성한 게시글 수 반환환*/

    public void withdrawalUser(UserEntity userEntity) {//회원 탈퇴 엔티티 생성

        UserEntity withdrawalingUserEntity = findUserEntityById(userEntity.getUserEntityId());//삭제할 유저 엔티티 확인
        checkIsPasswordCorrect(userEntity.getPassword(), withdrawalingUserEntity.getPassword());//확인용 비밀번호가 맞는지 체크

        WithdrawalEntity newWithdrawalEntity = userMapper.userEntityToWithdrawlENtity(withdrawalingUserEntity);//휴탈 회원 엔티티 생성
        newWithdrawalEntity.setUserActive(Withdrawal);//유저 엔티티 활동 -> 탈퇴처리

        userRepository.deleteById(withdrawalingUserEntity.getUserEntityId());//탈퇴 회원 정보를 기존 userEntity에서 삭제 -> 휴,탈회원 테이블로 이전
        withdrawalRepository.save(newWithdrawalEntity);
    }

    public void deActiveCheckAllUsers() {

        int year = LocalDate.now().getYear();
        int month = LocalDate.now().getMonthValue();
        LocalDateTime activationDate = LocalDateTime.of(year - 2, month, 1, 0, 0);//휴면 기준(2년 설정), 현 시간 기준 2년 전

        List<UserEntity> deActivatingUserEntities = userRepository.findUserEntityByModifiedByGreaterThan(activationDate);
        List<WithdrawalEntity> withdrawalEntities = new ArrayList<>();

        for(UserEntity user:deActivatingUserEntities){
            WithdrawalEntity newDeActiveEntity = userMapper.userEntityToWithdrawlENtity(user);//휴탈 회원 엔티티 생성
            newDeActiveEntity.setUserActive(DeActive);
            withdrawalEntities.add(newDeActiveEntity);
            log.info(" {}번 유저를 비활성화합니다. ", user.getUserEntityId());
        }
        userRepository.deleteAll(deActivatingUserEntities);
        withdrawalRepository.saveAll(withdrawalEntities);
    }

    public Page<UserDto.FollowUserInfoResponseDto> getUserFollowList ( Long myUserEntityId, int pageIndex) {// id에 해당하는 유저가 팔로우하는 사용자 목록 반환 메서드
        List<FollowEntity> followingList =
                followRepository.findByFollowingUserEntity(
                        userRepository.findById(myUserEntityId).orElseThrow(),
                        PageRequest.of(pageIndex-1, 20, Sort.by("modifiedBy").descending()
                        ));

        List<UserDto.FollowUserInfoResponseDto> followList = followingList.stream()
                .map(FollowEntity -> UserDto.FollowUserInfoResponseDto.builder()
                        .userEntityId(FollowEntity.getFollowedUserEntity().getUserEntityId())
                        .displayName(FollowEntity.getFollowedUserEntity().getDisplayName())
                        .build())
                .collect(Collectors.toList());

        return new PageImpl<>(followList);
    }

    public Page<UserDto.FollowUserInfoResponseDto> getUserFollowingList ( Long myUserEntityId, int pageIndex)
    {// id에 해당하는 유저를 팔로우하는 사용자 목록 반환 메서드
        List<FollowEntity> followingList = followRepository.findByFollowedUserEntity(userRepository.findById(myUserEntityId).orElseThrow(), PageRequest.of(pageIndex, 20, Sort.by("modifiedBy").descending()));

        List<UserDto.FollowUserInfoResponseDto> followList = followingList.stream()
                .map(FollowEntity -> UserDto.FollowUserInfoResponseDto.builder()
                        .userEntityId(FollowEntity.getFollowedUserEntity().getUserEntityId())
                        .displayName(FollowEntity.getFollowedUserEntity().getDisplayName())
                        .build())
                .collect(Collectors.toList());

        return new PageImpl<>(followList);
    }

    private void checkIsPasswordCorrect (String inputPassword, String realPassword){
        if (inputPassword.equals(realPassword)) {
            log.info("확인용 비밀번호가 일치합니다.");
        } else {
            log.error("확인용 비밀번호가 불일치합니다.");
            throw new BusinessLogicException(PASSWORD_IS_WRONG);
        }
    }
    private void checkDuplicateEmail(String email) {
        if (userRepository.findByEmail(email).isPresent()) {
            log.error("회원 가입 중 중복된 이메일이 입력되었습니다. ", new BusinessLogicException(ExceptionCode.DUPLICATE_EMAIL));
            throw new BusinessLogicException(ExceptionCode.DUPLICATE_EMAIL);//이메일 중복 확인
        }
    }// 이메일 중복 체크
    private void checkDuplicateDisplayName(String displayName) {
        if (userRepository.findByDisplayName(displayName).isPresent()) {
            log.error("회원 가입 중 중복된 닉네임이 입력되었습니다. ", new BusinessLogicException(ExceptionCode.DUPLICATE_DISPLAY_NAME));
            throw new BusinessLogicException(ExceptionCode.DUPLICATE_DISPLAY_NAME);//닉네임 중복 확인
        }
    } // 닉네임 중복 체크
    private void checkSwearingWord(String displayName) {
        if (badWordFiltering.check(displayName))//욕설이 닉네임이 들어 있다면(contains)
        {   log.error("비속어가 포함된 닉네임입니다.", new BusinessLogicException(ExceptionCode.FORBIDDEN_WORD_USED));
            throw new BusinessLogicException(ExceptionCode.FORBIDDEN_WORD_USED);
        }
    } // 닉네임 금지어 체크




}

