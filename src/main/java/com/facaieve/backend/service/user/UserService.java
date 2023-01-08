package com.facaieve.backend.service.user;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.*;

import com.facaieve.backend.dto.UserDto;
import com.facaieve.backend.entity.user.UserEntity;
import com.facaieve.backend.entity.user.WithdrawalEntity;
import com.facaieve.backend.repository.user.UserRepository;
import com.facaieve.backend.repository.user.WithdrawalRepository;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import java.util.Optional;
import java.util.stream.Collectors;

import static com.facaieve.backend.Constant.UserActive.UnActive;
import static com.facaieve.backend.Constant.UserActive.Withdrawal;

@Slf4j
@Service
@AllArgsConstructor
public class UserService {
    UserRepository userRepository;
    WithdrawalRepository withdrawalRepository;

    //입력 값으로 들어온 userEntity 저장 그리고 반환 todo 보안 설정 아직 안함
    public UserEntity createUserEntity(final UserEntity userEntity){

        if(userEntity ==null || userEntity.getUserEntityId()== null){
            throw new RuntimeException("Invalid arguments");
        }
        final String email = userEntity.getEmail();

        if(userRepository.existsByEmail(email)){//todo email 로 중복 검사 실행함.
            log.warn("Email already exists {}", email);
            throw new RuntimeException("Email already exists");
        }
        return userRepository.save(userEntity);
    }


    public UserEntity findUserEntity(UserEntity userEntity){
        checkIsUserExist(userEntity);

        return userRepository.findById(userEntity.getUserEntityId()).orElseThrow();//에러 추가 예정

    }

    public List<UserDto.ResponseUserDto> findAllUserEntityWithPagination(int page){

        return userRepository.findAll(PageRequest.of(page, 20, Sort.by("updateTime").descending()))
                .stream()
                .map(UserDto.ResponseUserDto::of)
                .collect(Collectors.toList());
    }



    //credentials 반환함.
    public UserEntity getByCredentials(final String email, final String password){
        return userRepository.findUserEntityByEmailAndPassword(email, password);
    }

    public UserEntity deleteUserEntity(final UserEntity deleteUserEntity){

        long deletingUserId = deleteUserEntity.getUserEntityId();

        checkIsUserExist(deleteUserEntity);
        checkIsPasswordCorrect(deleteUserEntity);

        userRepository.deleteById(deletingUserId);
        return null;
    }

    public UserEntity updateUserEntity(UserEntity patchUserEntity){
        checkIsUserExist(userRepository.findById(patchUserEntity.getUserEntityId()).orElseThrow());

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
    }

    public List<Integer> userPostingCount(UserEntity userEntity){// 포트폴리오, 패션픽업, 펀딩 순으로 유저가 작성한 게시물의 수를 List로 반환
        List<Integer> userPostCountList = new ArrayList<>();
        UserEntity foundUserEntity = userRepository.findById(userEntity.getUserEntityId()).orElseThrow();

        userPostCountList.add(foundUserEntity.getPortfolioEntities().size());
        userPostCountList.add(foundUserEntity.getFashionPickupEntities().size());
        userPostCountList.add(foundUserEntity.getFundingEntities().size());

        return userPostCountList;
    }

    public void withdrawalUser(UserEntity userEntity){//회원 탈퇴 엔티티 생성
        WithdrawalEntity newWithdrawalEntity = new WithdrawalEntity();
        newWithdrawalEntity.setWithdrawalUserEntity(userEntity);
        newWithdrawalEntity.setUserActive(Withdrawal);

        UserEntity foundUserEntity = findUserEntity(userEntity);
        foundUserEntity.setUserActive(Withdrawal);//유저 엔티티 활동 -> 탈퇴처리

        userRepository.save(foundUserEntity);//탈퇴처리 정보 저장
        withdrawalRepository.save(newWithdrawalEntity);// 휴탈 회원 엔티티 새로 생성
    }

    public void deActivateUser(UserEntity userEntity){//휴면 회원 엔티티 생성
        WithdrawalEntity newWithdrawalEntity = new WithdrawalEntity();
        newWithdrawalEntity.setWithdrawalUserEntity(userEntity);
        newWithdrawalEntity.setUserActive(Withdrawal);
        /* 휴면 처리 절차 확인 필요  */

        UserEntity foundUserEntity = findUserEntity(userEntity);//휴면처리할 유저 정보 가져오기

        int year = LocalDate.now().getYear();
        int month =  LocalDate.now().getMonthValue();
        LocalDateTime activationDate = LocalDateTime.of(year-2,month,1,0,0 );

        if(foundUserEntity.getUpdateTime().isBefore(activationDate)) {//최근활동 이력 > 현날짜 -2년 -> 휴면처리
            foundUserEntity.setUserActive(UnActive);//유저 엔티티 활동 -> 휴면처리
        }
        else {
            new RuntimeException();// 휴면 기준이 되지 않는다는 에러 발생(추가 예정)
        }

        userRepository.save(foundUserEntity);//탈퇴처리 정보 저장
        withdrawalRepository.save(newWithdrawalEntity);// 휴탈 회원 엔티티 새로 생성
    }




    public void checkIsUserExist(UserEntity userEntity){
        userRepository.findById(userEntity.getUserEntityId()).orElseThrow(/* 런타임 에러 발생 */);
    }

    public void checkIsPasswordCorrect(UserEntity userEntity){
        if(userEntity.getPassword().equals(userEntity.getPassword())){
            log.info("확인용 비밀번호가 일치합니다.");
        }
        else {
            log.error("확인용 비밀번호가 불일치합니다.");
            new RuntimeException();//추후 수정 예정
        }
    }
}
