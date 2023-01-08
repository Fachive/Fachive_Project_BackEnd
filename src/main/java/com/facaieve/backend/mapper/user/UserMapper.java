
package com.facaieve.backend.mapper.user;
import com.facaieve.backend.dto.UserDto;
import com.facaieve.backend.entity.user.UserEntity;
import com.facaieve.backend.stubDate.UserStubData;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface UserMapper {

    UserEntity userStubEntityToToUserStubEntity(UserStubData userStubData);

    UserEntity userPostDtoToUserEntity(UserDto.PostUserDto postUserDto);

    UserEntity userPatchDtoToUserEntity(UserDto.PatchUserDto patchUserDto);

    UserEntity userGetDtoToUserEntity(UserDto.GetUserDto getUserDto);

    UserEntity userDeleteDtoToUserEntity(UserDto.DeleteUserDto deleteUserDto);

    UserDto.ResponseUserDto userEntityToResponseDto(UserEntity userEntity);

}
