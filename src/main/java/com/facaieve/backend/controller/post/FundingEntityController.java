package com.facaieve.backend.controller.post;


import com.facaieve.backend.entity.crossReference.FundingEntityToTagEntity;
import com.facaieve.backend.entity.image.S3ImageInfo;
import com.facaieve.backend.dto.multi.Multi_ResponseDTO;
import com.facaieve.backend.entity.etc.CategoryEntity;
import com.facaieve.backend.entity.etc.TagEntity;
import com.facaieve.backend.entity.user.UserEntity;
import com.facaieve.backend.mapper.etc.TagMapper;
import com.facaieve.backend.mapper.post.FundingMapper;

import com.facaieve.backend.dto.post.FundingDto;
import com.facaieve.backend.entity.post.FundingEntity;
import com.facaieve.backend.mapper.post.PostImageMapper;
import com.facaieve.backend.service.aswS3.S3FileService;
import com.facaieve.backend.service.etc.CategoryService;
import com.facaieve.backend.service.etc.TagService;
import com.facaieve.backend.service.post.FundingEntityService;
import com.facaieve.backend.service.user.UserService;
import com.facaieve.backend.stubDate.FundingMainPageStubData;
import com.facaieve.backend.stubDate.FundingStubData;
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
import org.springframework.web.bind.annotation.*;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@Slf4j
@RestController
@RequestMapping("/funding")
@AllArgsConstructor
public class FundingEntityController {

    CategoryService categoryService;
    FundingEntityService fundingEntityService;
    FundingMapper fundingMapper;
    PostImageMapper postImageMapper;
    S3FileService s3FileService;
    TagService tagService;
    TagMapper tagMapper;

    UserService userService;

    static final FundingStubData fundingStubData = new FundingStubData();


    //todo 카테고리 정렬순서 그리고 페이지를 파라미터로 가지는 api  구현할 것

    //todo parameter 로 category total, top, outer, one piece, skirt, accessory, suit, dress
    //todo sortway mypick, update, duedate
    @GetMapping("/mainFunding")//test pass
    public ResponseEntity getFundingEntitySortingCategoryConditions(@Parameter(name="category" ,description="카테고리(피그마 참조) 문자열로 명시하면됨 기본값은 total 로 설정 되어있음 나머지 다른 유형의 post 동일함")
                                                                        @RequestParam(required = false, defaultValue = "total") String categoryName,
                                                                    @Parameter(name="sortWay" ,description="정렬 방식: myPick(좋아요 순서), views (조회수),dueDate(생성일) default: myPicks")
                                                                        @RequestParam(required = false, defaultValue = "myPick") String sortWay,
                                                                    @Parameter(name="pageIndex" ,description="페이지 인덱스 기본값 1")
                                                                        @RequestParam(required = false, defaultValue = "1") Integer pageIndex) {

        CategoryEntity categoryEntity = categoryService.getCategoryFromService(categoryName);

        fundingEntityService.setCondition(sortWay);

        Page<FundingEntity> fundingEntityPage = fundingEntityService.findFundingEntitiesByCondition(categoryEntity, pageIndex,30);

//        List<FundingDto.ResponseFundingIncludeURI> fundingEntities = fundingEntityPage.stream()
//                .map(fundingEntity -> fundingMapper.FundingEntityToResponseFundingIncludeURI(fundingEntity))
//                .collect(Collectors.toList());
//


        return new ResponseEntity(HttpStatus.OK);

    }


    //카테고리, 최신순,추천순,조회순,
    //
    @GetMapping("/mainPageGet")
    public ResponseEntity getFundingEntityMainPage(@RequestParam(required = false, defaultValue = "30") int want) {

        Multi_ResponseDTO<FundingMainPageStubData> responseDTO = new Multi_ResponseDTO<>();
        List<FundingMainPageStubData> fundingMainPageStubDataList = new ArrayList<>();
        for (Integer i = 0; i < want; i++) {
            fundingMainPageStubDataList.add(new FundingMainPageStubData());
        }
        responseDTO.setData(fundingMainPageStubDataList);
        return new ResponseEntity(responseDTO, HttpStatus.OK);
    }

    @Operation(summary = "펀딩 게시글 작성 메서드 예제", description = "json 바디값을 통한 펀딩 게시글 POST 요청 메서드")//대상 api의 대한 설명을 작성하는 어노테이션
    @ApiResponses({
            @ApiResponse(responseCode = "201", description = "펀딩 게시글이 정상 등록됨", content = @Content(schema = @Schema(implementation = FundingDto.ResponseFundingDtoForEntity.class))),
            @ApiResponse(responseCode = "400", description = "BAD REQUEST !!"),
            @ApiResponse(responseCode = "404", description = "NOT FOUND !!"),
            @ApiResponse(responseCode = "500", description = "서버에서 에러가 발생하였습니다.")
    })
    @PostMapping("/post")//POST API
    public ResponseEntity postFundingEntity(@ModelAttribute FundingDto.PostDto postDto) {
        List<S3ImageInfo> s3ImageInfoList = s3FileService.uploadMultiFileList(postDto.getMultipartFileList());//저장될 파일 객체가 들어감.\
        log.info("펀딩 이미지 파일 s3업로드 완료");

        List<TagEntity> tagEntityList = new ArrayList<>();
        postDto.getTagList().stream()//dto로 받은 태그 리스트들을 저장하고 이를 게시글 객체에 넣기위해 list로 반환
                .map(tag -> tagEntityList.add(tagService.createTagEntity(tag.getTagName()))).collect(Collectors.toList());
        log.info("태그 데이터 저장 완료");

        CategoryEntity categoryEntity = categoryService.getCategoryFromService(postDto.getCategoryName());
        log.info("카테고리 데이터 확인 완료");

        UserEntity postingUser = userService.findUserEntityById(postDto.getUserId());
        log.info("게시글 작성 유저 정보 확인 완료");

        FundingEntity fundingEntity = FundingEntity.builder().title(postDto.getTitle())//저장할 패션픽업 객체 생성
                .body(postDto.getBody()).categoryEntity(categoryEntity).s3ImgInfo(s3ImageInfoList)
                .userEntity(postingUser)
                .myPick(new ArrayList<>())
                .views(0)
                .targetPrice(postDto.getTargetPrice())
                .fundedPrice(postDto.getFundedPrice())
                .dueDate(LocalDateTime.now())
                .commentList(new ArrayList<>())
                .build();

        List<FundingEntityToTagEntity> tagEntities =tagEntityList.stream().map(tagEntity -> FundingEntityToTagEntity.builder()
                .fundingEntity(fundingEntity)
                .tagEntity(tagEntity).build()).collect(Collectors.toList());
        fundingEntity.setTagEntities(tagEntities);
        log.info("펀딩-태그 중간 엔티티 설정");

        fundingEntity.getS3ImgInfo().forEach(s3ImageInfo ->s3ImageInfo.setFundingEntityPost(fundingEntity));
        log.info("S3ImageInfo 매핑관계 설정");

        FundingEntity createdFundingEntity = fundingEntityService.createFundingEntity(fundingEntity);
        log.info("게시글 저장 완료");

        FundingDto.ResponseFundingDtoForEntity responseFundingDto = fundingMapper.fundingEntityToResponseFundingDto(createdFundingEntity);
        fundingEntityService.calculatingPercentage(responseFundingDto);

        //  todo dto 를 반환해야 수나환참조 문제를 해결이 가능하다.
        return new ResponseEntity(responseFundingDto, HttpStatus.OK);
    }
    @Operation(summary = "펀딩 게시글 수정 메서드 예제", description = "json 바디값을 통한 펀딩 게시글 PATCH 요청 메서드")
//대상 api의 대한 설명을 작성하는 어노테이션
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "펀딩 게시글이 수정되었습니다 ", content = @Content(schema = @Schema(implementation = FundingDto.ResponseFundingDtoForEntity.class))),
            @ApiResponse(responseCode = "400", description = "BAD REQUEST !!"),
            @ApiResponse(responseCode = "404", description = "NOT FOUND !!"),
            @ApiResponse(responseCode = "500", description = "서버에서 에러가 발생하였습니다.")
    })
    @PatchMapping("/patch")//PATCH API
    public ResponseEntity patchFundingEntity(@ModelAttribute FundingDto.PatchRequestDto patchRequestDto) {

        FundingEntity editingFundingEntity = fundingEntityService.findFundingEntity(patchRequestDto.getFundingEntityId());
        log.info("수정할 객체 가져오기 {} ", editingFundingEntity);

        List<String> entityUrlList = editingFundingEntity.getS3ImgInfo().stream().map(S3ImageInfo::getFileURI).collect(Collectors.toList());
        log.info("수정할 객체에 있는 이미지 데이터, s3에서 삭제하기 위해 호출 {} ", entityUrlList);
        s3FileService.deleteMultiFileList(entityUrlList);
        log.info("기존 이미지 데이터 s3에서 삭제 완료 {} ", entityUrlList);

        List<S3ImageInfo> s3ImageInfoList = s3FileService.uploadMultiFileList(patchRequestDto.getChangedMultipartFileList());
        log.info("새로운 이미지 데이터 s3에 저장 완료 {} ", s3ImageInfoList);

        List<TagEntity> tagEntityList = new ArrayList<>();
        patchRequestDto.getChangedTagList().stream()//dto로 받은 태그 리스트들을 저장하고 이를 게시글 객체에 넣기위해 list로 반환
                .map(tag -> tagEntityList.add(tagService.createTagEntity(tag.getTagName()))).collect(Collectors.toList());
        log.info("수정 DTO에서 새로운 태그 데이터 저장완료");

        List<FundingEntityToTagEntity> tagEntities =tagEntityList.stream().map(tagEntity -> FundingEntityToTagEntity.builder()
                .fundingEntity(editingFundingEntity)
                .tagEntity(tagEntity).build()).collect(Collectors.toList());
        editingFundingEntity.setTagEntities(tagEntities);
        log.info("패션픽업-태그 중간 엔티티 설정");

        CategoryEntity categoryEntity = categoryService.getCategoryFromService(patchRequestDto.getChangedCategoryName());
        log.info("카테고리 데이터 확인 완료");


        FundingDto.PatchDto patchDto = FundingDto.PatchDto.builder()
                .changedTitle(patchRequestDto.getChangedTitle())
                .changedBody(patchRequestDto.getChangedBody())
                .changedCategoryEntity(categoryEntity)
                .changedTagList(tagEntities)
                .s3ImgInfo(s3ImageInfoList)
                .fundedPrice(patchRequestDto.getFundedPrice())
                .targetPrice(patchRequestDto.getTargetPrice())
                .build();

        FundingEntity editedFundingEntity = fundingEntityService.editFundingEntity(editingFundingEntity, patchDto);
        log.info("수정된 엔티티 저장 {} ", editedFundingEntity);

        FundingDto.ResponseFundingDtoForEntity responseFundingDto = fundingMapper.fundingEntityToResponseFundingDto(editedFundingEntity);
        fundingEntityService.calculatingPercentage(responseFundingDto);

        return new ResponseEntity(responseFundingDto,HttpStatus.OK);//수정된 entity 를 다시 반환함.
    }
    @Operation(summary = "펀딩 게시글 호출 메서드 예제", description = "json 바디값을 통한 펀딩 게시글 GET 요청 메서드")//대상 api의 대한 설명을 작성하는 어노테이션
    @ApiResponses({@ApiResponse(responseCode = "200", description = "펀딩 게시글이 호출 되었습니다", content = @Content(schema = @Schema(implementation = FundingDto.ResponseFundingDtoForEntity.class))),
                   @ApiResponse(responseCode = "400", description = "BAD REQUEST !!"),
                   @ApiResponse(responseCode = "404", description = "NOT FOUND !!"),
                   @ApiResponse(responseCode = "500", description = "서버에서 에러가 발생하였습니다.")
    })
    @GetMapping("/get/{fundingEntityId}")//GET API
    public ResponseEntity getFundingEntity(@PathVariable("fundingEntityId") Long fundingEntityId) {

        log.info("기존 펀딩 게시글을 가져옵니다.");
        FundingEntity foundFundingEntity = fundingEntityService.findFundingEntity(fundingEntityId);
        FundingDto.ResponseFundingDtoForEntity responseFundingDto = fundingMapper.fundingEntityToResponseFundingDto(foundFundingEntity);

        fundingEntityService.calculatingPercentage(responseFundingDto);

        return new ResponseEntity(responseFundingDto, HttpStatus.OK);

    }
    @Operation(summary = "펀딩 게시글 삭제 메서드 예제", description = "json 바디값을 통한 펀딩 게시글 DELETE 요청 메서드")
//대상 api의 대한 설명을 작성하는 어노테이션
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "펀딩 게시글이 삭제 되었습니다", content = @Content(schema = @Schema(implementation = FundingDto.ResponseFundingDto.class))),
            @ApiResponse(responseCode = "400", description = "BAD REQUEST !!"),
            @ApiResponse(responseCode = "404", description = "NOT FOUND !!"),
            @ApiResponse(responseCode = "500", description = "서버에서 에러가 발생하였습니다.")
    })
    @DeleteMapping()//DELETE API
    public ResponseEntity deleteFundingEntity(@RequestBody FundingDto.DeleteDto deleteDto) {

        fundingEntityService.removeFundingEntity(deleteDto.getFundingEntityId());
        log.info("기존 패션픽업 게시글을 삭제합니다.");
        return new ResponseEntity(HttpStatus.OK);
    }



}
