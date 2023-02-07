package com.facaieve.backend.mapper.user;

import com.facaieve.backend.dto.UserDto;
import com.facaieve.backend.entity.user.UserEntity;
import com.facaieve.backend.entity.user.WithdrawalEntity;
import com.facaieve.backend.stubDate.UserStubData;
import javax.annotation.processing.Generated;
import org.springframework.stereotype.Component;

@Generated(
    value = "org.mapstruct.ap.MappingProcessor",
    date = "2023-02-01T22:45:23+0900",
    comments = "version: 1.5.3.Final, compiler: javac, environment: Java 17.0.5 (Oracle Corporation)"
)
@Component
public class UserMapperImpl implements UserMapper {

    @Override
    public UserEntity userStubEntityToToUserStubEntity(UserStubData userStubData) {
        if ( userStubData == null ) {
            return null;
        }

        UserEntity userEntity = new UserEntity();

        userEntity.setRegTime( userStubData.getRegTime() );
        userEntity.setUpdateTime( userStubData.getUpdateTime() );
        userEntity.setUserEntityId( userStubData.getUserEntityId() );
        userEntity.setDisplayName( userStubData.getDisplayName() );
        userEntity.setEmail( userStubData.getEmail() );
        userEntity.setPassword( userStubData.getPassword() );
        userEntity.setState( userStubData.getState() );
        userEntity.setCity( userStubData.getCity() );
        userEntity.setUserInfo( userStubData.getUserInfo() );
        userEntity.setCareer( userStubData.getCareer() );
        userEntity.setEducation( userStubData.getEducation() );
        userEntity.setCompany( userStubData.getCompany() );
        userEntity.setUserActive( userStubData.getUserActive() );

        return userEntity;
    }

    @Override
    public UserEntity userPostDtoToUserEntity(UserDto.PostUserDto postUserDto) {
        if ( postUserDto == null ) {
            return null;
        }

        UserEntity userEntity = new UserEntity();

        userEntity.setDisplayName( postUserDto.getDisplayName() );
        userEntity.setEmail( postUserDto.getEmail() );
        userEntity.setPassword( postUserDto.getPassword() );
        userEntity.setState( postUserDto.getState() );
        userEntity.setCity( postUserDto.getCity() );
        userEntity.setUserInfo( postUserDto.getUserInfo() );
        userEntity.setCareer( postUserDto.getCareer() );
        userEntity.setEducation( postUserDto.getEducation() );
        userEntity.setCompany( postUserDto.getCompany() );

        return userEntity;
    }

    @Override
    public UserEntity userPatchDtoToUserEntity(UserDto.PatchUserDto patchUserDto) {
        if ( patchUserDto == null ) {
            return null;
        }

        UserEntity userEntity = new UserEntity();

        userEntity.setUserEntityId( patchUserDto.getUserEntityId() );
        userEntity.setDisplayName( patchUserDto.getDisplayName() );
        userEntity.setEmail( patchUserDto.getEmail() );
        userEntity.setState( patchUserDto.getState() );
        userEntity.setCity( patchUserDto.getCity() );
        userEntity.setUserInfo( patchUserDto.getUserInfo() );
        userEntity.setCareer( patchUserDto.getCareer() );
        userEntity.setEducation( patchUserDto.getEducation() );
        userEntity.setCompany( patchUserDto.getCompany() );

        return userEntity;
    }

    @Override
    public UserEntity userGetDtoToUserEntity(UserDto.GetUserDto getUserDto) {
        if ( getUserDto == null ) {
            return null;
        }

        UserEntity userEntity = new UserEntity();

        userEntity.setUserEntityId( getUserDto.getUserEntityId() );

        return userEntity;
    }

    @Override
    public UserEntity userDeleteDtoToUserEntity(UserDto.DeleteUserDto deleteUserDto) {
        if ( deleteUserDto == null ) {
            return null;
        }

        UserEntity userEntity = new UserEntity();

        userEntity.setUserEntityId( deleteUserDto.getUserEntityId() );
        userEntity.setPassword( deleteUserDto.getPassword() );

        return userEntity;
    }

    @Override
    public WithdrawalEntity userEntityToWithdrawlENtity(UserEntity userEntity) {
        if ( userEntity == null ) {
            return null;
        }

        WithdrawalEntity withdrawalEntity = new WithdrawalEntity();

        withdrawalEntity.setRegTime( userEntity.getRegTime() );
        withdrawalEntity.setUpdateTime( userEntity.getUpdateTime() );
        withdrawalEntity.setUserActive( userEntity.getUserActive() );
        withdrawalEntity.setDisplayName( userEntity.getDisplayName() );
        withdrawalEntity.setEmail( userEntity.getEmail() );
        withdrawalEntity.setPassword( userEntity.getPassword() );
        withdrawalEntity.setState( userEntity.getState() );
        withdrawalEntity.setCity( userEntity.getCity() );
        withdrawalEntity.setUserInfo( userEntity.getUserInfo() );
        withdrawalEntity.setCareer( userEntity.getCareer() );
        withdrawalEntity.setEducation( userEntity.getEducation() );
        withdrawalEntity.setCompany( userEntity.getCompany() );

        return withdrawalEntity;
    }

    @Override
    public UserDto.ResponseUserDto userEntityToResponseDto(UserEntity userEntity) {
        if ( userEntity == null ) {
            return null;
        }

        UserDto.ResponseUserDto responseUserDto = new UserDto.ResponseUserDto();

        responseUserDto.setDisplayName( userEntity.getDisplayName() );
        responseUserDto.setEmail( userEntity.getEmail() );

        return responseUserDto;
    }
}
