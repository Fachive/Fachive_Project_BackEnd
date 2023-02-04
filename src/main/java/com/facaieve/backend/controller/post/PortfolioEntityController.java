package com.facaieve.backend.controller.post;


import com.facaieve.backend.dto.multi.Multi_ResponseDTO;
import com.facaieve.backend.dto.post.FashionPickupDto;
import com.facaieve.backend.dto.post.FundingDto;
import com.facaieve.backend.entity.etc.CategoryEntity;
import com.facaieve.backend.mapper.etc.TagMapper;
import com.facaieve.backend.mapper.post.PortfolioMapper;
import com.facaieve.backend.dto.post.PortfolioDto;
import com.facaieve.backend.entity.post.PortfolioEntity;
import com.facaieve.backend.service.aswS3.S3FileService;
import com.facaieve.backend.service.etc.CategoryService;
import com.facaieve.backend.service.etc.TagService;
import com.facaieve.backend.service.post.PortfolioEntityService;
import com.facaieve.backend.stubDate.PortfolioMagePageStubData;
import com.facaieve.backend.stubDate.PortfolioStubData;
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

@Slf4j
@RestController
@RequestMapping("/portfolio")
@AllArgsConstructor
public class PortfolioEntityController {

    CategoryService categoryService;
    PortfolioEntityService portfolioEntityService;
    PortfolioMapper portfolioMapper;
    S3FileService s3FileService;
    TagMapper tagMapper;
    TagService tagService;

    static final PortfolioStubData portfolioStubData = new PortfolioStubData();
    //todo parameter 로 category total, top, outer, one piece, skirt, accessory, suit, dress
    //todo sortway mypick, update, duedate

    @Operation(summary = "포트폴리오를 카테골와 정렬 조건에 따라서 반환하는 api", description = "Get 을 이용해서 정렬 방법과 카테고리별로 get 기능")//대상 api의 대한 설명을 작성하는 어노테이션
    @ApiResponses({
            @ApiResponse(responseCode = "200" ,description = "카테고리와 정렬 방볍에 따라서 정상적으로 가져옴"),
            @ApiResponse(responseCode = "400", description = "BAD REQUEST !!"),
            @ApiResponse(responseCode = "404", description = "NOT FOUND !!"),
            @ApiResponse(responseCode = "500", description = "서버에서 에러가 발생하였습니다.")
    })

    @GetMapping("/mainportfolio")//test pass
    public ResponseEntity getPortfolioEntitySortingCategoryConditions(@Parameter(name="category" ,description="카테고리(피그마 참조) 문자열로 명시하면됨 기본값은 total 로 설정 되어있음 나머지 다른 유형의 post 동일함")
                                                                          @RequestParam(required = false, defaultValue = "total") String categoryName,
                                                                      @Parameter(name="sortWay" ,description="정렬 방식: myPick(좋아요 순서), views (조회수),dueDate(생성일) default: myPicks")
                                                                          @RequestParam(required = false, defaultValue = "myPick") String sortWay,
                                                                      @Parameter(name="pageIndex" ,description="페이지 인덱스 기본값 1")
                                                                          @RequestParam(required = false, defaultValue = "1") Integer pageIndex){
        CategoryEntity categoryEntity = categoryService
                .getCategory(CategoryEntity.builder().categoryName(categoryName).build());

        portfolioEntityService.setCondition(sortWay);
        Page<PortfolioEntity> portfolioEntityPage =
                portfolioEntityService.findPortfolioEntitiesByCondition(categoryEntity, pageIndex,30);

//        List<PortfolioDto.ResponsePortfolioIncludeURI> portfolioEntities = portfolioEntityPage.stream()
//                .map(portfolioEntity -> portfolioMapper.portfolioEntityToResponsePortfolioIncludeURI(portfolioEntity))
//                .collect(Collectors.toList());
//
//        Multi_ResponseDTO<PortfolioDto.ResponsePortfolioIncludeURI> multi_responseDTO =
//                                                        new Multi_ResponseDTO<PortfolioDto.ResponsePortfolioIncludeURI>(portfolioEntities, portfolioEntityPage);

        return new ResponseEntity(HttpStatus.OK);

    }


//최신순, 추천순
    @GetMapping("/mainPageGet")//test pass
    public ResponseEntity getPortfolioMainPage(@RequestParam(required = false, defaultValue = "30") int want){
        Multi_ResponseDTO<PortfolioMagePageStubData> responseDTO = new Multi_ResponseDTO<>();
        List<PortfolioMagePageStubData> portfolioMagePageStubDataList = new ArrayList<>();
        for(int i = 0; i< want; i++){
            portfolioMagePageStubDataList.add(new PortfolioMagePageStubData());
        }
        responseDTO.setData(portfolioMagePageStubDataList);
        return new ResponseEntity(responseDTO,HttpStatus.OK);
    }

    //todo 수정할것
//    private void configureTagEntityAtPost(List<TagDTO.PostTagDTO> postTagDTOList, PortfolioEntity portfolioEntity){
//
//        List<TagEntity> tagEntities = postTagDTOList.stream().map(tagMapper::postTagDtoToTagEntity)
//                .collect(Collectors.toList());
//
//        for(TagEntity tagEntity: tagEntities){
//            TagEntity savedTagEntity = tagService.createTagEntity(tagEntity);
//            savedTagEntity.setPortfolioEntity(portfolioEntity);
//        }
//
//        portfolioEntity.setTagEntities(tagEntities);
//    }

    private CategoryEntity getCategoryFromService(String categoryName){
        return categoryService.getCategory(CategoryEntity
                .builder().categoryName(categoryName).build());
    }

//    @PostMapping("/multipartPost")//test pass
//    public ResponseEntity postPortfolioEntity(@ModelAttribute PortfolioDto.RequestPortfolioIncludeMultiPartFiles
//                                                          requestPortfolioIncludeMultiPartFiles){
//
//        List<MultipartFile> multipartFileList = requestPortfolioIncludeMultiPartFiles.getMultipartFileList();
//        List<S3ImageInfo> s3ImageInfoList = s3FileService.uploadMultiFileList(multipartFileList);
//
//        CategoryEntity categoryEntity = getCategoryFromService(requestPortfolioIncludeMultiPartFiles
//                                                                                        .getPostCategoryDto()
//                                                                                        .getCategoryName());
//
//        PortfolioDto.ResponsePortfolioIncludeURI responsePortfolioIncludeURI =
//                PortfolioDto.ResponsePortfolioIncludeURI.builder()
//                        .title(requestPortfolioIncludeMultiPartFiles.getTitle())
//                        .body(requestPortfolioIncludeMultiPartFiles.getBody())
//                        .views(requestPortfolioIncludeMultiPartFiles.getViews())
//                        .s3ImageInfoList(s3ImageInfoList)
//                        .build();
//
//        PortfolioEntity portfolio = portfolioMapper
//                .responsePortfolioIncludeURIToPortfolioEntity(responsePortfolioIncludeURI);
//        List<PostImageEntity> postImageEntities = portfolio.getPostImageEntities();
//
//        portfolio.setCategoryEntity(categoryEntity);//category 도 함께 저장함.
//        categoryEntity.getPortfolioEntities().add(portfolio);
//
//        //context 로 foregin key 저장하기 위해서 사용함
//        for(PostImageEntity postImageEntity: postImageEntities){
//            postImageEntity.setPortfolioEntity(portfolio);
//        }
//
//        configureTagEntityAtPost(requestPortfolioIncludeMultiPartFiles.getPostTagDTOList(),portfolio);//tag 를 점검하고 설정하는 함수
//
//        return new ResponseEntity(portfolioMapper
//                .portfolioEntityToResponsePortfolioIncludeURI(
//                        portfolioEntityService.createPortfolioEntity(portfolio))
//                ,HttpStatus.OK);
//    }




    // 서비스 레이어 구현이 안되어 Stub 데이터로 대체(추후 변경 예정)

    @Operation(summary = "포트폴리오 게시글 작성 메서드 예제", description = "json 바디값을 통한 포트폴리오 게시글 POST 요청 메서드")//대상 api의 대한 설명을 작성하는 어노테이션
    @ApiResponses({
            @ApiResponse(responseCode = "201" ,description = "포트폴리오 게시글이 정상 등록됨", content = @Content(schema = @Schema(implementation = FundingDto.ResponseFundingDto.class))),
            @ApiResponse(responseCode = "400", description = "BAD REQUEST !!"),
            @ApiResponse(responseCode = "404", description = "NOT FOUND !!"),
            @ApiResponse(responseCode = "500", description = "서버에서 에러가 발생하였습니다.")
    })
    @PostMapping("/post")
    public ResponseEntity postPortfolioEntity(@RequestBody PortfolioDto.PostPortfolioDtoDto postPortfolioDtoDto) {
//        PortfolioEntity postingPortfolioEntity = portfolioMapper.portfolioPostDtoToPortfolioEntity(postPortfolioDtoDto);
//        PortfolioEntity postedPortfolioEntity = portfolioEntityService.createPortfolioEntity(postingPortfolioEntity);
//        return new ResponseEntity( portfolioMapper.portfolioEntityToResponsePortfolioEntity(postedPortfolioEntity), HttpStatus.OK);

        PortfolioEntity stubdata =  portfolioMapper.portfolioDtoToFashionPickupStubData(portfolioStubData);
        log.info("새로운 포트폴리오 게시물을 등록합니다.");
        return new ResponseEntity( portfolioMapper.portfolioEntityToResponsePortfolioEntity(stubdata), HttpStatus.OK);
    }

    @Operation(summary = "포트폴리오 게시글 수정 메서드 예제", description = "json 바디값을 통한 포트폴리오 Post 메서드")//대상 api의 대한 설명을 작성하는 어노테이션
    @ApiResponses({
            @ApiResponse(responseCode = "200" ,description = "포트폴리오 게시글이 수정됨"),
            @ApiResponse(responseCode = "400", description = "BAD REQUEST !!"),
            @ApiResponse(responseCode = "404", description = "NOT FOUND !!"),
            @ApiResponse(responseCode = "500", description = "서버에서 에러가 발생하였습니다.")
    })
    @PatchMapping("/patch")
    public ResponseEntity patchPortfolioEntity(@RequestBody PortfolioDto.PatchPortfolioDtoDto patchPortfolioDtoDto){
//        PortfolioEntity patchingPortfolioEntity = portfolioMapper.portfolioPatchDtoToPortfolioEntity(patchPortfolioDtoDto);
//        PortfolioEntity patchedPortfolioEntity = portfolioEntityService.editPortfolioEntity(patchingPortfolioEntity);
//        return new ResponseEntity( portfolioMapper.portfolioEntityToResponsePortfolioEntity(patchedPortfolioEntity), HttpStatus.OK);

        PortfolioEntity stubdata =  portfolioMapper.portfolioDtoToFashionPickupStubData(portfolioStubData);
        stubdata.setBody("포트폴리오 게시글 내용 수정완료");
        stubdata.setTitle("포트폴리오 게시글 제목 수정완료");

        log.info("기존 포트폴리오 게시물을 수정합니다.");
        return new ResponseEntity( portfolioMapper.portfolioEntityToResponsePortfolioEntity(stubdata), HttpStatus.OK);
    }


    @Operation(summary = "포트폴리오 게시글 호출 예제", description = "json 바디값을 통한 포트폴리오 GET 메서드")//대상 api의 대한 설명을 작성하는 어노테이션
    @ApiResponses({
            @ApiResponse(responseCode = "200" ,description = "포트폴리오 게시글이 정상적으로 호출됨"),
            @ApiResponse(responseCode = "400", description = "BAD REQUEST !!"),
            @ApiResponse(responseCode = "404", description = "NOT FOUND !!"),
            @ApiResponse(responseCode = "500", description = "서버에서 에러가 발생하였습니다.")
    })
    @GetMapping("/get/{portfolioId}")
    public ResponseEntity getPortfolioEntity(@PathVariable("portfolioId") Long portfolioId){
        log.info("기존 패션픽업 게시글을 가져옵니다.");
        PortfolioEntity portfolio = portfolioEntityService.findPortfolioEntity(portfolioId);
        return new ResponseEntity( /*portfolioMapper.portfolioEntityToResponsePortfolioIncludeURI(portfolio),*/ HttpStatus.OK);

//        PortfolioEntity stubdata =  portfolioMapper.portfolioDtoToFashionPickupStubData(portfolioStubData);
//        return new ResponseEntity( portfolioMapper.portfolioEntityToResponsePortfolioEntity(stubdata), HttpStatus.OK);
    }


    @Operation(summary = "포트폴리오 게시글 삭제 예제", description = "json 바디값을 통한 포트폴리오 DELETE 메서드")//대상 api의 대한 설명을 작성하는 어노테이션
    @ApiResponses({
            @ApiResponse(responseCode = "200" ,description = "포트폴리오 게시글이 정상적으로 호출됨"),
            @ApiResponse(responseCode = "400", description = "BAD REQUEST !!"),
            @ApiResponse(responseCode = "404", description = "NOT FOUND !!"),
            @ApiResponse(responseCode = "500", description = "서버에서 에러가 발생하였습니다.")
    })
    @DeleteMapping("/delete")
    public ResponseEntity deletePortfolioEntity(@RequestBody PortfolioDto.DeletePortfolioDtoDto deletePortfolioDtoDto){

        portfolioEntityService.removePortfolioEntity(deletePortfolioDtoDto.getPortfolioEntityId());
        log.info("기존 패션픽업 게시글을 삭제합니다.");
        return new ResponseEntity(HttpStatus.OK);



    }


}
