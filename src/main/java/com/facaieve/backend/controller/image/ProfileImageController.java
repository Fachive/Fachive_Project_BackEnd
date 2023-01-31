package com.facaieve.backend.controller.image;

import com.facaieve.backend.dto.image.ImageEntityDto;
import com.facaieve.backend.entity.image.ImageEntityProfile;
import com.facaieve.backend.service.image.ImageService;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiResponse;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.lang.Nullable;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;

@Slf4j
@RestController
@AllArgsConstructor
@RequestMapping("")
public class ProfileImageController {

    ImageService imageService;

    @ApiResponse(code = 201, message = "사진이 등록되어 이미지 파일 식별자와 파일명 반환")
    @ApiOperation(value = "이미지 등록 api로 사용자 프로필 저장에 활용", notes = "Multipart 타입을 통한 이미지 등록")//대상 api의 대한 설명을 작성하는 어노테이션
    @PostMapping(value ="/post/profile", consumes = {"multipart/form-data"})
    public ResponseEntity postImage(@Nullable @RequestParam("userid") Long userId, @RequestPart MultipartFile profileImg) throws IOException {
        log.info("이미지를 업로드합니다.");

       ImageEntityProfile uploadImage = imageService.uploadImage(userId, profileImg);

       return new ResponseEntity<>(
               ImageEntityDto.ResponseDto.builder().imageEntityId(uploadImage.getImageEntityId()).userEntityId(uploadImage.getProfileImgOwner().getUserEntityId()),//userEntity가 null일 경우 exception 발생하는지 확인 필요
               HttpStatus.CREATED);
    }

    @ApiResponse(code = 200, message = "등록된 사진의 이미지 변경")
    @ApiOperation(value = "이미지 스장 api로 사용자 프로필을 새로 저장하는데 활용", notes = "Multipart 타입을 통한 이미지 수정")//대상 api의 대한 설명을 작성하는 어노테이션
    @PatchMapping("/patch/profile")
    public ResponseEntity patchImage(@Nullable @RequestParam("imgId") Long imgId, @RequestPart MultipartFile profileImg) throws IOException {
        log.info("이미지를 변경합니다.");

        ImageEntityProfile uploadImage = imageService.replaceImage(imgId, profileImg);

        return new ResponseEntity<>(
                ImageEntityDto.ResponseDto.builder().imageEntityId(uploadImage.getImageEntityId()).userEntityId(uploadImage.getProfileImgOwner().getUserEntityId()),//userEntity가 null일 경우 exception 발생하는지 확인 필요
                HttpStatus.CREATED);
    }


    @ApiResponse(code = 200, message = "등록한 이미지 파일을 삭제")
    @ApiOperation(value = "사용자 프로필 이미지 등록 api", notes = "기존에 등록한 이미지 파일을 삭제 ")//대상 api의 대한 설명을 작성하는 어노테이션
    @DeleteMapping("/delete/profile/{userid}")
    public ResponseEntity deleteImage(@Nullable @PathVariable("imageEntityId") Long imageEntityId) throws IOException {
        log.info("기존의 이미지를 삭제합니다.");

        imageService.removeImage(imageEntityId);
        log.info("정삭적으로 이미지를 삭제헀습니다.");
        return new ResponseEntity<>(HttpStatus.CREATED);
    }

}
