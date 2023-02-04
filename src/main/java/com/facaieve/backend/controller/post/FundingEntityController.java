package com.facaieve.backend.controller.post;


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

    private CategoryEntity getCategoryFromService(String categoryName){
        return categoryService.getCategory(CategoryEntity
                .builder().categoryName(categoryName).build());
    }
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

        CategoryEntity categoryEntity = getCategoryFromService(categoryName);

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


    //todo 수정할것
//    private void validateSaveTagAtPost(List<TagDTO.PostTagDTO> postTagDTOList, FundingEntity fundingEntity){
//        List<TagEntity> tagEntities = postTagDTOList.stream().map(tagMapper::postTagDtoToTagEntity)
//                .collect(Collectors.toList());
//        for(TagEntity tagEntity: tagEntities){
//            TagEntity savedTagEntity = tagService.createTagEntity(tagEntity);
//            savedTagEntity.setFundingEntity(fundingEntity);
//        }
//        fundingEntity.setTagEntities(tagEntities);
//    }


    @PostMapping("/multipartPost")//test pass
    public ResponseEntity postFundingEntityWithMultiPart(@ModelAttribute FundingDto.PostDto postDto) {

        List<S3ImageInfo> s3ImageInfoList = s3FileService.uploadMultiFileList(postDto.getMultipartFileList());//저장될 파일 객체가 들어감.\
        log.info("펀딩 이미지 파일 s3업로드 완료");


        List<TagEntity> tagEntityList = new ArrayList<>();
        postDto.getTagList().stream()//dto로 받은 태그 리스트들을 저장하고 이를 게시글 객체에 넣기위해 list로 반환
                .map(tag -> tagEntityList.add(tagService.createTagEntity(tag.getTagName()))).collect(Collectors.toList());
        log.info("태그 데이터 저장 완료");


        CategoryEntity categoryEntity = getCategoryFromService(postDto.getCategoryName());
        log.info("카테고리 데이터 확인 완료");

        UserEntity postingUser = userService.findUserEntityById(postDto.getUserId());
        log.info("게시글 작성 유저 정보 확인 완료");


        FundingEntity fundingEntity = FundingEntity.builder()
                .title(postDto.getTitle())//저장할 패션픽업 객체 생성
                .body(postDto.getBody()).categoryEntity(categoryEntity)
                .s3ImgInfo(s3ImageInfoList)
                .build();

        fundingEntityService.createFundingEntity(fundingEntity);

        return new ResponseEntity( HttpStatus.OK);//percentage를 계산하고 설정한 후에 반환
    }

    // 서비스 레이어 구현이 안되어 Stub 데이터로 대체(추후 변경 예정)

    @Operation(summary = "펀딩 게시글 작성 메서드 예제", description = "json 바디값을 통한 펀딩 게시글 POST 요청 메서드")//대상 api의 대한 설명을 작성하는 어노테이션
    @ApiResponses({
            @ApiResponse(responseCode = "201", description = "펀딩 게시글이 정상 등록됨", content = @Content(schema = @Schema(implementation = FundingDto.ResponseFundingDto.class))),
            @ApiResponse(responseCode = "400", description = "BAD REQUEST !!"),
            @ApiResponse(responseCode = "404", description = "NOT FOUND !!"),
            @ApiResponse(responseCode = "500", description = "서버에서 에러가 발생하였습니다.")
    })
    @PostMapping("/post")
    public ResponseEntity postFundingEntity(@RequestBody FundingDto.PostFundingDto postFundingDto) {
//        FundingEntity postingFundingEntity = fundingMapper.fundingPostDtoToFundingEntity(postFundingDto);
//        FundingEntity postedFundingEntity = fundingEntityService.createFundingEntity(postingFundingEntity);
//        return new ResponseEntity( fundingMapper.fundingEntityToResponseFundingEntity(postedFundingEntity), HttpStatus.OK);

        FundingEntity stubdata = fundingMapper.fundingDtoToFundingEntityStubData(fundingStubData);
        log.info("새로운 펀딩 게시물을 등록합니다.");
        return new ResponseEntity(fundingMapper.fundingEntityToResponseFundingEntity(stubdata), HttpStatus.OK);
    }


    @Operation(summary = "펀딩 게시글 수정 메서드 예제", description = "json 바디값을 통한 펀딩 게시글 PATCH 요청 메서드")
//대상 api의 대한 설명을 작성하는 어노테이션
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "펀딩 게시글이 수정되었습니다 ", content = @Content(schema = @Schema(implementation = FundingDto.ResponseFundingDto.class))),
            @ApiResponse(responseCode = "400", description = "BAD REQUEST !!"),
            @ApiResponse(responseCode = "404", description = "NOT FOUND !!"),
            @ApiResponse(responseCode = "500", description = "서버에서 에러가 발생하였습니다.")
    })
    @PatchMapping("/patch")
    public ResponseEntity patchFundingEntity(@RequestBody FundingDto.PatchFundingDto patchFundingDto) {
//        FundingEntity patchingFundingEntity = fundingMapper.fundingPatchDtoToFundingEntity(patchFundingDto);
//        FundingEntity patchedFundingEntity = fundingEntityService.createFundingEntity(patchingFundingEntity);
//        return new ResponseEntity( fundingMapper.fundingEntityToResponseFundingEntity(patchedFundingEntity), HttpStatus.OK);

        FundingEntity stubdata = fundingMapper.fundingDtoToFundingEntityStubData(fundingStubData);
        stubdata.setTitle("펀딩 게시글 제목 수정완료");
        stubdata.setBody("펀딩 게시글 내용 수정완료");

        log.info("기존 펀딩 게시물을 수정합니다.");
        return new ResponseEntity(fundingMapper.fundingEntityToResponseFundingEntity(stubdata), HttpStatus.OK);
    }


//    @Operation(summary = "펀딩 게시글 호출 메서드 예제", description = "json 바디값을 통한 펀딩 게시글 GET 요청 메서드")//대상 api의 대한 설명을 작성하는 어노테이션
//    @ApiResponses({
//            @ApiResponse(responseCode = "200", description = "펀딩 게시글이 호출 되었습니다", content = @Content(schema = @Schema(implementation = FundingDto.ResponseFundingDto.class))),
//            @ApiResponse(responseCode = "400", description = "BAD REQUEST !!"),
//            @ApiResponse(responseCode = "404", description = "NOT FOUND !!"),
//            @ApiResponse(responseCode = "500", description = "서버에서 에러가 발생하였습니다.")
//    })
//    @GetMapping("/get/{fundingEntityId}")
//    public ResponseEntity getFundingEntity(@PathVariable("fundingEntityId") Long fundingEntityId) {
//
//        log.info("기존 펀딩 게시글을 가져옵니다.");
//        FundingEntity foundFundingEntity = fundingEntityService.getFundingEntityById(fundingEntityId);
//        FundingDto.ResponseFundingIncludeURI responseFundingIncludeURI = fundingMapper.FundingEntityToResponseFundingIncludeURI(foundFundingEntity);
//        fundingEntityService.calculatingPercentage(responseFundingIncludeURI);
//        return new ResponseEntity( responseFundingIncludeURI, HttpStatus.OK);
//
//        FundingEntity stubdata = fundingMapper.fundingDtoToFundingEntityStubData(fundingStubData);
//        return new ResponseEntity(fundingMapper.fundingEntityToResponseFundingEntity(stubdata), HttpStatus.OK);
//    }

    @Operation(summary = "펀딩 게시글 삭제 메서드 예제", description = "json 바디값을 통한 펀딩 게시글 DELETE 요청 메서드")
//대상 api의 대한 설명을 작성하는 어노테이션
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "펀딩 게시글이 삭제 되었습니다", content = @Content(schema = @Schema(implementation = FundingDto.ResponseFundingDto.class))),
            @ApiResponse(responseCode = "400", description = "BAD REQUEST !!"),
            @ApiResponse(responseCode = "404", description = "NOT FOUND !!"),
            @ApiResponse(responseCode = "500", description = "서버에서 에러가 발생하였습니다.")
    })
    @DeleteMapping()
    public ResponseEntity deleteFundingEntity(@RequestBody FundingDto.DeleteFundingDto deleteFundingDto) {

        fundingEntityService.removeFundingEntity(deleteFundingDto.getFundingEntityId());
        log.info("기존 패션픽업 게시글을 삭제합니다.");
        return new ResponseEntity(HttpStatus.OK);


    }


}
