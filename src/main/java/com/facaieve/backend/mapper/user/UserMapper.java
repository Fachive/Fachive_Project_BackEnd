
package com.facaieve.backend.mapper.user;
import com.facaieve.backend.dto.UserDto;
import com.facaieve.backend.entity.user.UserEntity;
import com.facaieve.backend.entity.user.WithdrawalEntity;
import com.facaieve.backend.stubDate.UserStubData;
import org.mapstruct.Mapper;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

@Mapper(componentModel = "spring")
public interface UserMapper {

    UserEntity userStubEntityToToUserStubEntity(UserStubData userStubData);

    default public UserEntity userPostDtoToUserEntity(UserDto.PostUserDto postUserDto){
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
            userEntity.setProfileImg(ServletUriComponentsBuilder.fromCurrentContextPath()
                    .path("/img/download/")
                    .path(postUserDto.getMultipartFileList().get(0).getName() )
                    .toUriString());
            return userEntity;
    };

    UserEntity userPatchDtoToUserEntity(UserDto.PatchUserDto patchUserDto);

    UserEntity userGetDtoToUserEntity(UserDto.GetUserDto getUserDto);

    UserEntity userDeleteDtoToUserEntity(UserDto.DeleteUserDto deleteUserDto);

    WithdrawalEntity userEntityToWithdrawlENtity(UserEntity userEntity);

    UserDto.ResponseUserDto userEntityToResponseDto(UserEntity userEntity);

    UserDto.ResponseUserDto2 userEntityToResponseDto2(UserEntity userEntity);


}
