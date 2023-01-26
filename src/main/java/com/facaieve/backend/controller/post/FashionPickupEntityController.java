package com.facaieve.backend.controller.post;


import com.facaieve.backend.dto.UserDto;
import com.facaieve.backend.dto.image.PostImageDto;
import com.facaieve.backend.entity.image.PostImageEntity;
import com.facaieve.backend.mapper.post.FashionPickupMapper;

import com.facaieve.backend.dto.post.FashionPickupDto;
import com.facaieve.backend.entity.post.FashionPickupEntity;
import com.facaieve.backend.mapper.post.PostImageMapper;
import com.facaieve.backend.service.aswS3.S3FileService;
import com.facaieve.backend.service.image.PostImageService;
import com.facaieve.backend.service.post.FashionPickupEntityService;
import com.facaieve.backend.stubDate.FashionPuckupStubData;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.sql.ResultSetMetaData;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@Slf4j
@RestController
@RequestMapping("/fashionpickup")
@AllArgsConstructor
public class FashionPickupEntityController {
    //todo 해당 컨트롤러에 너무 과도한 의존성이 들어감 수정 요함.
    FashionPickupEntityService fashionPickupEntityService;
    PostImageService postImageService;
    FashionPickupMapper fashionPickupMapper;
    S3FileService s3FileService;
    PostImageMapper postImageMapper;


    static final FashionPuckupStubData fashionPuckupStubData = new FashionPuckupStubData();

    //todo 추후에 서비스 로직을 전부다 fashionPickupService 레이어 하위에 생성해서 controller 단에서의 의존성을 줄일 예정
    @PostMapping(value = "/multipart/post")
    public ResponseEntity postFashionPickupEntityWithMultipart(
            @ModelAttribute FashionPickupDto.RequestFashionPickupIncludeMultiPartFileDto multiPartFileDto){

        List<MultipartFile> multipartFileList = multiPartFileDto.getMultiPartFileList();
        List<PostImageDto> postImageDtoList = s3FileService.uploadMultiFileList(multipartFileList);//저장될 파일 객체가 들어감.
        //S3에 저장후에 파일 이름과 URI 를 가지고 있음.


        FashionPickupDto.ResponseFashionPickupIncludeURI responseFashionPickupIncludeURI
                 = FashionPickupDto.ResponseFashionPickupIncludeURI.builder()
                .fashionPickupEntityId(multiPartFileDto.getFashionPickupEntityId())
                .title(multiPartFileDto.getTitle())
                .views(multiPartFileDto.getViews())
                .Body(multiPartFileDto.getBody())
                .multiPartFileList(postImageDtoList.stream().collect(Collectors.toList()))
                .build();//todo stream 사용할것

        FashionPickupEntity fashionPickupEntity =
                fashionPickupMapper.fashionPickupIncludeURIToFashionPickupEntity(responseFashionPickupIncludeURI);

        List<PostImageEntity> postImageEntities = fashionPickupEntity.getPostImageEntities();
        for(PostImageEntity postImage:postImageEntities){
            postImage.setFashionPickupEntity(fashionPickupEntity);
        }
        //연관관계의 주인인 이미지에 야무지게 삽입함

    //todo dto 를 반환해야 수나환참조 문제를 해결이 가능하다.
        return new ResponseEntity(fashionPickupMapper
                .fashionPickupEntityToResponseFashionPickupIncludeURI(
                        fashionPickupEntityService.createFashionPickupEntity
                                (fashionPickupEntity)),
                HttpStatus.OK);

    }

    // 사진을 가지고 있는 객체면 그냥 사진 uri랑 같이 반환됨.
    @GetMapping("/multiGet")
    public ResponseEntity getFashionPickupEntityMultipart(@RequestParam("postFashionEntityId") Long FashionEntityId){

        FashionPickupEntity fashionPickupEntity =
                fashionPickupEntityService.findFashionPickupEntity(FashionEntityId);

        //dto 로 변환해서 무한 참조 오류 피함
       return new ResponseEntity(fashionPickupMapper
               .fashionPickupEntityToResponseFashionPickupIncludeURI
                       (fashionPickupEntity)
               ,HttpStatus.OK);
    }



    @PatchMapping("/multiPatch")
    public ResponseEntity patchFashionPickupEntityMultipart(@RequestBody List<MultipartFile> multipartFiles,
                                                            Long fashionPickupEntityId){

        FashionPickupEntity fashionPickupEntity =
                fashionPickupEntityService.findFashionPickupEntity(fashionPickupEntityId);

        List<PostImageEntity> postImageEntities = fashionPickupEntity.getPostImageEntities();
//        List<String> fileNames = postImageEntities.stream().map(PostImageEntity::getFileName).collect(Collectors.toList());
        List<String> fileURIes = postImageEntities
                .stream()
                .map(PostImageEntity::getFileURI)
                .collect(Collectors.toList());

        s3FileService.deleteMultiFileList(fileURIes);//파일 URI 를 이용해서 파일을 삭제함


        List<PostImageDto> postImageDtoList = s3FileService.uploadMultiFileList(multipartFiles);
        fashionPickupEntity
                .setPostImageEntities(postImageDtoList
                        .stream()
                        .map(postImageMapper::postImageDtoToPostImageEntity)
                        .collect(Collectors.toList()));
        List<PostImageEntity> postImageEntityList = fashionPickupEntity.getPostImageEntities();

        for(PostImageEntity postImageEntity: postImageEntityList){
            postImageEntity.setFashionPickupEntity(fashionPickupEntity);
        }
        fashionPickupEntityService.editFashionPickupEntity(fashionPickupEntity);
        //파라미터로 들어온 파일을 받아서 bucket에 새롭게 저장함
        return new ResponseEntity(fashionPickupEntity,HttpStatus.OK);//수정된 entity 를 다시 반환함.


    }












    @Operation(summary = "패션픽업 게시글 등록 메서드 예제", description = "json 바디값을 통한 패션픽업 Post 메서드")//대상 api의 대한 설명을 작성하는 어노테이션
    @ApiResponses({
            @ApiResponse(responseCode = "201" ,description = "패션픽업 게시글이 정상 등록됨", content = @Content(schema = @Schema(implementation = FashionPickupDto.ResponseFashionPickupDto.class))),
            @ApiResponse(responseCode = "400", description = "BAD REQUEST !!"),
            @ApiResponse(responseCode = "404", description = "NOT FOUND !!"),
            @ApiResponse(responseCode = "500", description = "서버에서 에러가 발생하였습니다.")
    })
    @PostMapping("/post")
    public ResponseEntity postFashionPickupEntity(@RequestBody FashionPickupDto.PostFashionPickupDto postFashionPickupDto) {
//        FashionPickupEntity postingFashionPickupEntity = fashionPickupMapper.fashionPickupPostDtoToFashionPickupEntity(postFashionPickupDto);
//        FashionPickupEntity postedFashionPickupEntity = fashionPickupEntityService.createFashionPickupEntity(postingFashionPickupEntity);
//        return new ResponseEntity( fashionPickupMapper.fashionPickupEntityToResponseFashionPickupEntity(postedFashionPickupEntity), HttpStatus.OK);

        FashionPickupEntity stubdata =  fashionPickupMapper.fashionPickupDtoToFashionPickupStubData(fashionPuckupStubData);
        log.info("새로운 패션픽업 게시물을 등록합니다.");
        return new ResponseEntity( fashionPickupMapper.fashionPickupEntityToResponseFashionPickupEntity(stubdata), HttpStatus.OK);
    }



    @Operation(summary = "패션픽업 게시글 수정 메서드 예제", description = "json 바디값을 통한 패션픽업 Post 메서드")//대상 api의 대한 설명을 작성하는 어노테이션
    @ApiResponses({
            @ApiResponse(responseCode = "200" ,description = "패션픽업 게시글이 수정됨", content = @Content(schema = @Schema(implementation = FashionPickupDto.ResponseFashionPickupDto.class))),
            @ApiResponse(responseCode = "400", description = "BAD REQUEST !!"),
            @ApiResponse(responseCode = "404", description = "NOT FOUND !!"),
            @ApiResponse(responseCode = "500", description = "서버에서 에러가 발생하였습니다.")
    })
    @PatchMapping("/patch")
    public ResponseEntity patchFashionPickupEntity(@RequestBody FashionPickupDto.PatchFashionPickupDto patchFashionPickupDto){
//        FashionPickupEntity patchingFashionPickupEntity = fashionPickupMapper.fashionPickupPatchDtoToFashionPickupEntity(patchFashionPickupDto);
//        FashionPickupEntity patchedFashionPickupEntity = fashionPickupEntityService.editFashionPickupEntity(patchingFashionPickupEntity);
//        return new ResponseEntity( fashionPickupMapper.fashionPickupEntityToResponseFashionPickupEntity(patchedFashionPickupEntity), HttpStatus.OK);
//
        FashionPickupEntity stubdata =  fashionPickupMapper.fashionPickupDtoToFashionPickupStubData(fashionPuckupStubData);
        stubdata.setBody("패션픽업 게시글 내용 수정완료");
        stubdata.setTitle("패션픽업 게시글 제목 수정완료");

        log.info("기존 패션픽업 게시물을 수정합니다.");
        return new ResponseEntity( fashionPickupMapper.fashionPickupEntityToResponseFashionPickupEntity(stubdata), HttpStatus.OK);
    }

    @Operation(summary = "패션픽업 게시글 호출 예제", description = "json 바디값을 통한 패션픽업 GET 메서드")//대상 api의 대한 설명을 작성하는 어노테이션
    @ApiResponses({
            @ApiResponse(responseCode = "200" ,description = "패션픽업 게시글이 정상적으로 호출됨", content = @Content(schema = @Schema(implementation = FashionPickupDto.ResponseFashionPickupDto.class))),
            @ApiResponse(responseCode = "400", description = "BAD REQUEST !!"),
            @ApiResponse(responseCode = "404", description = "NOT FOUND !!"),
            @ApiResponse(responseCode = "500", description = "서버에서 에러가 발생하였습니다.")
    })
    @GetMapping("/get")
    public ResponseEntity getFashionPickupEntity(@RequestBody FashionPickupDto.GetFashionPickupDto getFashionPickupDto){
//        FashionPickupEntity foundFashionPickupEntity = fashionPickupEntityService.findFashionPickupEntity(getFashionPickupDto.getFashionPickupEntityId());
//        return new ResponseEntity( fashionPickupMapper.fashionPickupEntityToResponseFashionPickupEntity(foundFashionPickupEntity), HttpStatus.OK);
        log.info("기존 패션픽업 게시글을 가져옵니다.");

        FashionPickupEntity stubdata =  fashionPickupMapper.fashionPickupDtoToFashionPickupStubData(fashionPuckupStubData);
        return new ResponseEntity( fashionPickupMapper.fashionPickupEntityToResponseFashionPickupEntity(stubdata), HttpStatus.OK);
    }

    @Operation(summary = "패션픽업 게시글 삭제 예제", description = "json 바디값을 통한 패션픽업 DELETE 메서드")//대상 api의 대한 설명을 작성하는 어노테이션
    @ApiResponses({
            @ApiResponse(responseCode = "200" ,description = "패션픽업 게시글이 정상적으로 호출됨", content = @Content(schema = @Schema(implementation = FashionPickupDto.ResponseFashionPickupDto.class))),
            @ApiResponse(responseCode = "400", description = "BAD REQUEST !!"),
            @ApiResponse(responseCode = "404", description = "NOT FOUND !!"),
            @ApiResponse(responseCode = "500", description = "서버에서 에러가 발생하였습니다.")
    })
    @DeleteMapping("/delete")
    public ResponseEntity deleteFashionPickupEntity(@RequestBody FashionPickupDto.DeleteFashionPickupDto deleteFashionPickupDto){

//        fashionPickupEntityService.removeFashionPickupEntity(deleteFashionPickupDto.getFashionPickupEntityId());

        log.info("기존 패션픽업 게시글을 삭제합니다.");
        return new ResponseEntity(HttpStatus.OK);



    }



}
