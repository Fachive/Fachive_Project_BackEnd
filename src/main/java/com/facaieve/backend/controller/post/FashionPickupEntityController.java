package com.facaieve.backend.controller.post;


import com.facaieve.backend.entity.crossReference.FashionPickupEntityToTagEntity;
import com.facaieve.backend.entity.image.S3ImageInfo;
import com.facaieve.backend.entity.etc.CategoryEntity;
import com.facaieve.backend.entity.etc.TagEntity;
import com.facaieve.backend.entity.user.UserEntity;
import com.facaieve.backend.mapper.etc.TagMapper;
import com.facaieve.backend.mapper.post.FashionPickupMapper;

import com.facaieve.backend.dto.post.FashionPickupDto;
import com.facaieve.backend.entity.post.FashionPickupEntity;
import com.facaieve.backend.mapper.post.PostImageMapper;
import com.facaieve.backend.service.aswS3.S3FileService;
import com.facaieve.backend.service.etc.CategoryService;
import com.facaieve.backend.service.etc.TagService;
import com.facaieve.backend.service.image.PostImageService;
import com.facaieve.backend.service.post.FashionPickupEntityService;
import com.facaieve.backend.service.user.UserService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.lang.Nullable;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;
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
    CategoryService categoryService;
    TagService tagService;
    TagMapper tagMapper;
    UserService userService;


    @Operation(summary = "N 개 반환하는 메소드",
            description = "프론트 엔드 요구사항이었던 param을 이용해서 카테고리 정렬 방식, 페이지 인덱스, 페이지당 객체 갯수를 지정해서 객체를 가져올 수 있는 api")//대상 api의 대한 설명을 작성하는 어노테이션
    @ApiResponses({
            @ApiResponse(responseCode = "201" ,description = "패션픽업 게시글이 정상 호출되었습니다."),
            @ApiResponse(responseCode = "400", description = "BAD REQUEST !!"),
            @ApiResponse(responseCode = "404", description = "NOT FOUND !!"),
            @ApiResponse(responseCode = "500", description = "서버에서 에러가 발생하였습니다.")
    })
    @GetMapping("/mainfasionpickup")//test pass
    public ResponseEntity getFashionEntitySortingCategoryConditions(@Parameter(name="categoryName" ,description="카테고리(total, 상의, 아우터, 바지,원피스, 스커트, 액세서리, 정장, 드레스) 문자열로 명시하면됨 기본값은 total 로 설정 되어있음 나머지 다른 유형의 post 동일함")
                                                                        @RequestParam(required = false, defaultValue = "total") String categoryName,
                                                                    @Parameter(name="sortWay" ,description="정렬 방식: myPick(좋아요 순서), views (조회수),dueDate(생성일) default: myPicks")
                                                                        @RequestParam(required = false, defaultValue = "myPick") String sortWay,
                                                                    @Parameter(name="pageIndex" ,description="페이지 인덱스 기본값 1")
                                                                        @RequestParam(required = false, defaultValue = "1") Integer pageIndex,
                                                                    @Parameter(name="contentNumByPage" ,description="페이지당 게시글 개수")
                                                                        @RequestParam(required = false, defaultValue = "20") Integer contentNumByPage) {

        //저장된 카테고리 객체 가져옴
        CategoryEntity categoryEntity = categoryService.getCategoryFromService(categoryName);

        fashionPickupEntityService.setCondition(sortWay);//condition 객체 만드는 부분 수정함

        Page<FashionPickupEntity> fashionPickupEntityPage =
                fashionPickupEntityService.findFashionPickupEntitiesByCondition(categoryEntity, pageIndex,contentNumByPage);

        List<FashionPickupDto.ResponseFashionPickupDtoForEntity> list = fashionPickupEntityPage.stream().map(entity -> fashionPickupMapper.fashionPickupEntityToResponseFashionPickupDto(entity)).collect(Collectors.toList());


        return new ResponseEntity(list, HttpStatus.OK);
    }


    @Operation(summary = "form-data 를 이용해서 post 객체를 등록하고 등록된 객체를 반환하는 api",
            description = "@modelAttribute를 사용해서 MultipartFile 과 json을 한번에 객체를 이용해서 받는 것으로 구현함")//대상 api의 대한 설명을 작성하는 어노테이션
    @ApiResponses({@ApiResponse(responseCode = "201" ,description = "패션픽업 게시글이 정상 등록됨", content = @Content(schema = @Schema(implementation = FashionPickupDto.ResponseFashionPickupDtoForEntity.class))),
                   @ApiResponse(responseCode = "400", description = "BAD REQUEST !!"),
                   @ApiResponse(responseCode = "404", description = "NOT FOUND !!"),
                   @ApiResponse(responseCode = "500", description = "서버에서 에러가 발생하였습니다.")})
    @PostMapping(value = "/post")//POST API
    public ResponseEntity postFashionPickupEntity(@ModelAttribute FashionPickupDto.PostDto postDto){

        List<S3ImageInfo> s3ImageInfoList = s3FileService.uploadMultiFileList(postDto.getMultipartFileList());//저장될 파일 객체가 들어감.\
        log.info("패션픽업 이미지 파일 s3업로드 완료");

        List<TagEntity> tagEntityList = new ArrayList<>();
        postDto.getTagList().stream()//dto로 받은 태그 리스트들을 저장하고 이를 게시글 객체에 넣기위해 list로 반환
                .map(tag -> tagEntityList.add(tagService.createTagEntity(tag.getTagName()))).collect(Collectors.toList());
        log.info("태그 데이터 저장 완료");

        CategoryEntity categoryEntity = categoryService.getCategoryFromService(postDto.getCategoryName());
        log.info("카테고리 데이터 확인 완료");

        UserEntity postingUser = userService.findUserEntityById(postDto.getUserId());
        log.info("게시글 작성 유저 정보 확인 완료");



        FashionPickupEntity fashionPickupEntity = FashionPickupEntity.builder().title(postDto.getTitle())//저장할 패션픽업 객체 생성
                .body(postDto.getBody())
                .myPick(new ArrayList<>())
                .views(0)
                .categoryEntity(categoryEntity)
                .s3ImgInfo(s3ImageInfoList)
                .commentList(new ArrayList<>())
                .userEntity(postingUser)
                .build();

        List<FashionPickupEntityToTagEntity> tagEntities =tagEntityList.stream().map(tagEntity -> FashionPickupEntityToTagEntity.builder()
                .fashionPickupEntity(fashionPickupEntity)
                .tagEntity(tagEntity).build()).collect(Collectors.toList());

        fashionPickupEntity.setTagEntities(tagEntities);
        log.info("패션픽업-태그 중간 엔티티 설정");

        fashionPickupEntity.getS3ImgInfo().forEach(s3ImageInfo ->s3ImageInfo.setFashionPickupEntityPost(fashionPickupEntity));
        log.info("S3ImageInfo 매핑관계 설정");


        FashionPickupEntity createdFashionPickupEntity = fashionPickupEntityService.createFashionPickupEntity(fashionPickupEntity);
              log.info("게시글 저장 완료");



    //  todo dto 를 반환해야 수나환참조 문제를 해결이 가능하다.
        return new ResponseEntity(fashionPickupMapper.fashionPickupEntityToResponseFashionPickupDto(createdFashionPickupEntity), HttpStatus.CREATED);
    }
    @Operation(summary = "패션픽업 게시글 반환", description = "게시글을 id를 이용해서 사진을 포함하는 패션픽업 객체를 반환하는 api")//대상 api의 대한 설명을 작성하는 어노테이션
    @ApiResponses({@ApiResponse(responseCode = "201" ,description = "패션픽업 게시글이 정상 등록됨", content = @Content(schema = @Schema(implementation = FashionPickupDto.ResponseFashionPickupDtoForEntity.class))),
                   @ApiResponse(responseCode = "400", description = "BAD REQUEST !!"),
                   @ApiResponse(responseCode = "404", description = "NOT FOUND !!"),
                   @ApiResponse(responseCode = "500", description = "서버에서 에러가 발생하였습니다.")})
    @GetMapping("/get/{fashionPickupId}")//GET API
    public ResponseEntity getFashionPickupEntityMultipart(@PathVariable("fashionPickupId") Long fashionPickupId ){

        FashionPickupEntity fashionPickupEntity = fashionPickupEntityService.findFashionPickupEntity(fashionPickupId);

       return new ResponseEntity(fashionPickupMapper.fashionPickupEntityToResponseFashionPickupDto(fashionPickupEntity), HttpStatus.OK);
    }
    @Operation(summary = "패션픽업 게시글 수정", description = "패션 픽업의 이미지를 수정하는 메소드")//대상 api의 대한 설명을 작성하는 어노테이션
    @ApiResponses({ @ApiResponse(responseCode = "201" ,description = "패션픽업 게시글이 정상 수정됨", content = @Content(schema = @Schema(implementation = FashionPickupDto.ResponseFashionPickupDtoForEntity.class))),
                    @ApiResponse(responseCode = "400", description = "BAD REQUEST !!"),
                    @ApiResponse(responseCode = "404", description = "NOT FOUND !!"),
                    @ApiResponse(responseCode = "500", description = "서버에서 에러가 발생하였습니다.")})
    @PatchMapping("/patch")//PATCH API
    public ResponseEntity patchFashionPickupEntityMultipart(@ModelAttribute FashionPickupDto.PatchRequestDto patchRequestDto){

        FashionPickupEntity editingFashionPickupEntity = fashionPickupEntityService.findFashionPickupEntity(patchRequestDto.getFashionPickupEntityId());
            log.info("수정할 객체 가져오기 {} ", editingFashionPickupEntity);

        List<String> entityUrlList = editingFashionPickupEntity.getS3ImgInfo().stream().map(S3ImageInfo::getFileName).collect(Collectors.toList());
            log.info("수정할 객체에 있는 이미지 데이터, s3에서 삭제하기 위해 호출 {} ", entityUrlList);
        s3FileService.deleteMultiFileList(entityUrlList);
            log.info("기존 이미지 데이터 s3에서 삭제 완료 {} ", entityUrlList);

        List<S3ImageInfo> s3ImageInfoList = s3FileService.uploadMultiFileList(patchRequestDto.getChangedMultipartFileList());
            log.info("새로운 이미지 데이터 s3에 저장완료 {} ", s3ImageInfoList);

        List<TagEntity> tagEntityList = new ArrayList<>();
        patchRequestDto.getChangedTagList().stream()//dto로 받은 태그 리스트들을 저장하고 이를 게시글 객체에 넣기위해 list로 반환
                .map(tag -> tagEntityList.add(tagService.createTagEntity(tag.getTagName()))).collect(Collectors.toList());
            log.info("수정 DTO에서 새로운 태그 데이터 저장 완료");

        List<FashionPickupEntityToTagEntity> tagEntities =tagEntityList.stream().map(tagEntity -> FashionPickupEntityToTagEntity.builder()
                .fashionPickupEntity(editingFashionPickupEntity)
                .tagEntity(tagEntity).build()).collect(Collectors.toList());
        editingFashionPickupEntity.setTagEntities(tagEntities);
            log.info("패션픽업-태그 중간 엔티티 설정");

        CategoryEntity categoryEntity = categoryService.getCategoryFromService(patchRequestDto.getChangedCategoryName());
            log.info("카테고리 데이터 확인 완료");


        FashionPickupDto.PatchDto patchDto = FashionPickupDto.PatchDto.builder()
                .changedTitle(patchRequestDto.getChangedTitle())
                .changedBody(patchRequestDto.getChangedBody())
                .categoryEntity(categoryEntity)
                .tagEntities(tagEntities)
                .s3ImgInfo(s3ImageInfoList)
                .build();

        patchDto.getS3ImgInfo().forEach(s3ImageInfo ->s3ImageInfo.setFashionPickupEntityPost(editingFashionPickupEntity));


        FashionPickupEntity editedFashionPickupEntity = fashionPickupEntityService.editFashionPickupEntity(editingFashionPickupEntity, patchDto);
             log.info("수정된 엔티티 저장 {} ", editedFashionPickupEntity);

        return new ResponseEntity(fashionPickupMapper.fashionPickupEntityToResponseFashionPickupDto(editedFashionPickupEntity),HttpStatus.OK);//수정된 entity 를 다시 반환함.
    }
    @Operation(summary = "패션픽업 게시글 삭제 예제", description = "json 바디값을 통한 패션픽업 DELETE 메서드")//대상 api의 대한 설명을 작성하는 어노테이션
    @ApiResponses({
            @ApiResponse(responseCode = "200" ,description = "패션픽업 게시글이 정상적으로 호출됨"),
            @ApiResponse(responseCode = "400", description = "BAD REQUEST !!"),
            @ApiResponse(responseCode = "404", description = "NOT FOUND !!"),
            @ApiResponse(responseCode = "500", description = "서버에서 에러가 발생하였습니다.")
    })
    @DeleteMapping("/delete")//DELETE API
    public ResponseEntity deleteFashionPickupEntity(@RequestBody FashionPickupDto.DeleteFashionPickupDto deleteFashionPickupDto){

        FashionPickupEntity deletingFashionPickupEntity = fashionPickupEntityService.findFashionPickupEntity(deleteFashionPickupDto.getFashionPickupEntityId());
        log.info("수정할 객체 가져오기 {} ", deletingFashionPickupEntity);

        List<String> entityUrlList = deletingFashionPickupEntity.getS3ImgInfo().stream().map(S3ImageInfo::getFileName).collect(Collectors.toList());
        log.info("수정할 객체에 있는 이미지 데이터, s3에서 삭제하기 위해 호출 {} ", entityUrlList);
        s3FileService.deleteMultiFileList(entityUrlList);

        fashionPickupEntityService.removeFashionPickupEntity(deletingFashionPickupEntity);

        log.info("기존 패션픽업 게시글을 삭제합니다.");
        return new ResponseEntity(HttpStatus.OK);
    }



}
