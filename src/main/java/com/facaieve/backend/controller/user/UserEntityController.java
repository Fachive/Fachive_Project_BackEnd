package com.facaieve.backend.controller.user;


import com.facaieve.backend.dto.UserDto;
import com.facaieve.backend.dto.UserDto.PostUserDto;
import com.facaieve.backend.entity.user.UserEntity;
import com.facaieve.backend.mapper.user.UserMapper;
import com.facaieve.backend.service.user.UserService;
import com.facaieve.backend.stubDate.UserStubData;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@Slf4j
@RestController
@RequestMapping("/user")
@AllArgsConstructor
//@Tag(name = "UserEntity", description = "사용자와 관련된 api")
public class UserEntityController {

    UserService userService;
    UserMapper userMapper;

//    @ApiResponse(responseCode = "200",
//            description = "Provides single property json with a boolean which is only true if the key was found and the entry was deleted",
//            content = @Content(schema = @Schema(implementation = HttpModels.DeleteResponse.class))),
    @Operation(summary = "유저 등록 메서드 예제", description = "json 바디값을 통한 회원가입 메서드")//대상 api의 대한 설명을 작성하는 어노테이션
    @ApiResponses({
            @ApiResponse(responseCode = "201" ,description = "사용자가 정상 등록됨", content = @Content(schema = @Schema(implementation = UserDto.ResponseUserDto.class))),
            @ApiResponse(responseCode = "400", description = "BAD REQUEST !!"),
            @ApiResponse(responseCode = "404", description = "NOT FOUND !!"),
            @ApiResponse(responseCode = "500", description = "서버에서 에러가 발생하였습니다.")
    })
    @io.swagger.annotations.ApiResponses(
            @io.swagger.annotations.ApiResponse(
                    response = UserEntity.class, message = "ok", code=201)
    )
    @PostMapping("/post")// 유저 등록
    public ResponseEntity postUserEntity(@Parameter(description = "POST DTO", required = true, example = "문서 참고") @RequestBody PostUserDto postUserDto){

//       UserEntity postingUserEntity= userMapper.userPostDtoToUserEntity(postUserDto);
//       UserEntity postedUserEntity = userService.create(postingUserEntity);
//        return new ResponseEntity(userMapper.userEntityToResponseDto(postedUserEntity), HttpStatus.CREATED);
        log.info("신규 유저를 등록합니다.");
        UserStubData testStub = new UserStubData();
        // 컨트롤러단만 작성하기 위해 Stub 데이터로 대체
        return new ResponseEntity(userMapper.userStubEntityToToUserStubEntity(testStub), HttpStatus.CREATED);
    }

    @Operation(summary = "유저 정보 수정 메서드 예제", description = "json 바디값을 통한 회원 정보 수정 메서드")//대상 api의 대한 설명을 작성하는 어노테이션
    @ApiResponses({
            @ApiResponse(responseCode = "200" ,description = "사용자가 정상적으로 수정되었습니다 ", content = @Content(schema = @Schema(implementation = UserDto.ResponseUserDto.class))),
            @ApiResponse(responseCode = "500", description = "서버에서 에러가 발생하였습니다.")
    })
    @PatchMapping("/patch")//유저 정보 수정
    public ResponseEntity patchUserEntity(@RequestBody UserDto.PatchUserDto patchUserDto){
        UserEntity editingUserEntity= userMapper.userPatchDtoToUserEntity(patchUserDto);
//        UserEntity editedUserEntity = userService.updateUserEntity(editingUserEntity);
//        editingUserEntity.setDisplayName(editingUserEntity.getDisplayName()+"TEST");
        log.info("기존 유저 정보를 수정합니다.");
//        return new ResponseEntity( userMapper.userEntityToResponseDto(editedUserEntity), HttpStatus.OK);

        UserStubData testStub = new UserStubData();

        return new ResponseEntity( userMapper.userStubEntityToToUserStubEntity(testStub), HttpStatus.OK);
    }

    @Operation(summary = "유저 정보 요청 메서드 예제", description = "json 바디값을 통한 회원 정보 요청 메서드")//대상 api의 대한 설명을 작성하는 어노테이션
    @ApiResponses({
            @ApiResponse(responseCode = "200" ,description = "사용자 정보를 불러왔습니다 ", content = @Content(schema = @Schema(implementation = UserDto.ResponseUserDto.class))),
            @ApiResponse(responseCode = "500", description = "서버에서 에러가 발생하였습니다.")
    })
    @GetMapping("/get")//유저 정보(1인) 요청
    public ResponseEntity getUserEntity(@RequestBody UserDto.GetUserDto getUserDto){
//        UserEntity foundUserEntity = userService.findUserEntity(getUserDto.getUserEntityId());

//        return new ResponseEntity( userMapper.userEntityToResponseDto(foundUserEntity), HttpStatus.OK);
        UserStubData testStub = new UserStubData();
        // 컨트롤러단만 작성하기 위해 Stub 데이터로 대체
        return new ResponseEntity(userMapper.userStubEntityToToUserStubEntity(testStub), HttpStatus.OK);

    }

    @Operation(summary = "유저 정보 요청 삭제 메서드 예제", description = "json 바디값을 통한 회원 정보 삭제 요청 메서드")//대상 api의 대한 설명을 작성하는 어노테이션
    @ApiResponses({
            @ApiResponse(responseCode = "200" ,description = "사용자 정보를 삭제했습니다 ", content = @Content(schema = @Schema(implementation = UserDto.ResponseUserDto.class))),
            @ApiResponse(responseCode = "500", description = "서버에서 에러가 발생하였습니다.")
    })
    @DeleteMapping("/delete")//유저 정보(1인) 요청
    public ResponseEntity deleteUserEntity(@RequestBody UserDto.DeleteUserDto deleteUserDto){
//        UserEntity deletingUserEntity= userMapper.userDeleteDtoToUserEntity(deleteUserDto);
//        UserEntity deletedUserEntity = userService.deleteUserEntity(deletingUserEntity);
        log.info("기존 유저를 삭제합니다.");
        // 컨트롤러단만 작성하기 위해 Stub 데이터로 대체
        return new ResponseEntity(HttpStatus.OK);
    }



}
