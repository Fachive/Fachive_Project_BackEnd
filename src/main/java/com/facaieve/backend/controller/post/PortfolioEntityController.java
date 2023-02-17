package com.facaieve.backend.controller.post;


import com.facaieve.backend.dto.multi.Multi_ResponseDTO;
import com.facaieve.backend.dto.post.FashionPickupDto;
import com.facaieve.backend.dto.post.FundingDto;
import com.facaieve.backend.entity.crossReference.PortfolioEntityToTagEntity;
import com.facaieve.backend.entity.etc.CategoryEntity;
import com.facaieve.backend.entity.etc.TagEntity;
import com.facaieve.backend.entity.image.S3ImageInfo;
import com.facaieve.backend.entity.post.FashionPickupEntity;
import com.facaieve.backend.entity.user.UserEntity;
import com.facaieve.backend.mapper.etc.TagMapper;
import com.facaieve.backend.mapper.post.PortfolioMapper;
import com.facaieve.backend.dto.post.PortfolioDto;
import com.facaieve.backend.entity.post.PortfolioEntity;
import com.facaieve.backend.service.aswS3.S3FileService;
import com.facaieve.backend.service.etc.CategoryService;
import com.facaieve.backend.service.etc.TagService;
import com.facaieve.backend.service.post.PortfolioEntityService;
import com.facaieve.backend.service.user.UserService;
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
import java.util.stream.Collectors;

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
    UserService userService;

    static final PortfolioStubData portfolioStubData = new PortfolioStubData();
    //todo parameter 로 category total, top, outer, one piece, skirt, accessory, suit, dress
    //todo sortway mypick, update, duedate

    @Operation(summary = "포트폴리오를 카테고리와 정렬 조건에 따라서 반환하는 api", description = "프론트 엔드 요구사항이었던 param을 이용해서 카테고리 정렬 방식, 페이지 인덱스, 페이지당 객체 갯수를 지정해서 객체를 가져올 수 있는 api")//대상 api의 대한 설명을 작성하는 어노테이션
    @ApiResponses({
            @ApiResponse(responseCode = "200" ,description = "포트폴리오 게시글이 정상 호출되었습니다."),
            @ApiResponse(responseCode = "400", description = "BAD REQUEST !!"),
            @ApiResponse(responseCode = "404", description = "NOT FOUND !!"),
            @ApiResponse(responseCode = "500", description = "서버에서 에러가 발생하였습니다.")
    })

    @GetMapping("auth/mainportfolio")//test pass
    public ResponseEntity getPortfolioEntitySortingCategoryConditions(@Parameter(name="categoryName" ,description="카테고리(total, 상의, 아우터, 바지,원피스, 스커트, 액세서리, 정장, 드레스) 문자열로 명시하면됨 기본값은 total 로 설정 되어있음 나머지 다른 유형의 post 동일함")
                                                                          @RequestParam(required = false, defaultValue = "total") String categoryName,
                                                                      @Parameter(name="sortWay" ,description="정렬 방식: myPick(좋아요 순서), views (조회수),dueDate(생성일) default: myPicks")
                                                                          @RequestParam(required = false, defaultValue = "myPick") String sortWay,
                                                                      @Parameter(name="pageIndex" ,description="페이지 인덱스 기본값 1")
                                                                          @RequestParam(required = false, defaultValue = "1") Integer pageIndex,
                                                                      @Parameter(name="contentNumByPage" ,description="페이지당 게시글 개수")
                                                                          @RequestParam(required = false, defaultValue = "20") Integer contentNumByPage){
        CategoryEntity categoryEntity = categoryService
                .getCategory(CategoryEntity.builder().categoryName(categoryName).build());

        portfolioEntityService.setCondition(sortWay);
        Page<PortfolioEntity> portfolioEntityPage =
                portfolioEntityService.findPortfolioEntitiesByCondition(categoryEntity, pageIndex,contentNumByPage);

        List<PortfolioDto.ResponsePortfolioDtoForEntity> list = portfolioEntityPage.stream().map(entity -> portfolioMapper.fundingEntityToResponseFundingDto(entity)).collect(Collectors.toList());


        return new ResponseEntity(list, HttpStatus.OK);

    }


//최신순, 추천순
    @GetMapping("auth/mainPageGet")//test pass
    public ResponseEntity getPortfolioMainPage(@RequestParam(required = false, defaultValue = "30") int want){
        Multi_ResponseDTO<PortfolioMagePageStubData> responseDTO = new Multi_ResponseDTO<>();
        List<PortfolioMagePageStubData> portfolioMagePageStubDataList = new ArrayList<>();
        for(int i = 0; i< want; i++){
            portfolioMagePageStubDataList.add(new PortfolioMagePageStubData());
        }
        responseDTO.setData(portfolioMagePageStubDataList);
        return new ResponseEntity(responseDTO,HttpStatus.OK);
    }


    @Operation(summary = "포트폴리오 게시글 작성 메서드 예제", description = "json 바디값을 통한 포트폴리오 게시글 POST 요청 메서드")//대상 api의 대한 설명을 작성하는 어노테이션
    @ApiResponses({
            @ApiResponse(responseCode = "201" ,description = "포트폴리오 게시글이 정상 등록됨", content = @Content(schema = @Schema(implementation = FundingDto.ResponseFundingDto.class))),
            @ApiResponse(responseCode = "400", description = "BAD REQUEST !!"),
            @ApiResponse(responseCode = "404", description = "NOT FOUND !!"),
            @ApiResponse(responseCode = "500", description = "서버에서 에러가 발생하였습니다.")
    })
    @PostMapping("/post")//POST API
    public ResponseEntity postPortfolioEntity(@ModelAttribute PortfolioDto.PostDto postDto) {
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

        PortfolioEntity portfolioEntity = PortfolioEntity.builder().title(postDto.getTitle())//저장할 패션픽업 객체 생성
                .body(postDto.getBody()).categoryEntity(categoryEntity).s3ImgInfo(s3ImageInfoList)
                .userEntity(postingUser)
                .myPick(new ArrayList<>())
                .views(0)
                .myPicks(0)
                .myPick(new ArrayList<>())
                .s3ImgInfo(s3ImageInfoList)
                .commentList(new ArrayList<>())
                .build();

        List<PortfolioEntityToTagEntity> tagEntities =tagEntityList.stream().map(tagEntity -> PortfolioEntityToTagEntity.builder()
                .portfolioEntity(portfolioEntity)
                .tagEntity(tagEntity).build()).collect(Collectors.toList());
        portfolioEntity.setTagEntities(tagEntities);
        log.info("펀딩-태그 중간 엔티티 설정");

        portfolioEntity.getS3ImgInfo().forEach(s3ImageInfo ->s3ImageInfo.setPortfolioEntityPost(portfolioEntity));
        log.info("S3ImageInfo 매핑관계 설정");


        PortfolioEntity createdPortfolioEntity = portfolioEntityService.createPortfolioEntity(portfolioEntity);
        log.info("게시글 저장 완료");

        PortfolioDto.ResponsePortfolioDtoForEntity responsePortfolioDto = portfolioMapper.fundingEntityToResponseFundingDto(createdPortfolioEntity);

        return new ResponseEntity(responsePortfolioDto, HttpStatus.OK);
    }
    @Operation(summary = "포트폴리오 게시글 수정 메서드 예제", description = "json 바디값을 통한 포트폴리오 Post 메서드")//대상 api의 대한 설명을 작성하는 어노테이션
    @ApiResponses({
            @ApiResponse(responseCode = "200" ,description = "포트폴리오 게시글이 수정됨"),
            @ApiResponse(responseCode = "400", description = "BAD REQUEST !!"),
            @ApiResponse(responseCode = "404", description = "NOT FOUND !!"),
            @ApiResponse(responseCode = "500", description = "서버에서 에러가 발생하였습니다.")
    })
    @PatchMapping("/patch")//PATCH API
    public ResponseEntity patchPortfolioEntity(@ModelAttribute PortfolioDto.PatchRequestDto patchRequestDto){
        PortfolioEntity editingPortfolioEntity = portfolioEntityService.findPortfolioEntity(patchRequestDto.getPortfolioEntityId());
        log.info("수정할 객체 가져오기 {} ", editingPortfolioEntity);

        List<String> entityUrlList = editingPortfolioEntity.getS3ImgInfo().stream().map(S3ImageInfo::getFileURI).collect(Collectors.toList());
        log.info("수정할 객체에 있는 이미지 데이터, s3에서 삭제하기 위해 호출 {} ", entityUrlList);
        s3FileService.deleteMultiFileList(entityUrlList);
        log.info("기존 이미지 데이터 s3에서 삭제 완료 {} ", entityUrlList);

        List<S3ImageInfo> s3ImageInfoList = s3FileService.uploadMultiFileList(patchRequestDto.getMultipartFileList());
        log.info("새로운 이미지 데이터 s3에 저장 완료 {} ", s3ImageInfoList);

        List<TagEntity> tagEntityList = new ArrayList<>();
        patchRequestDto.getTagList().stream()//dto로 받은 태그 리스트들을 저장하고 이를 게시글 객체에 넣기위해 list로 반환
                .map(tag -> tagEntityList.add(tagService.createTagEntity(tag.getTagName()))).collect(Collectors.toList());
        log.info("수정 DTO에서 새로운 태그 데이터 저장완료");

        List<PortfolioEntityToTagEntity> tagEntities =tagEntityList.stream().map(tagEntity -> PortfolioEntityToTagEntity.builder()
                .portfolioEntity(editingPortfolioEntity)
                .tagEntity(tagEntity).build()).collect(Collectors.toList());
        editingPortfolioEntity.setTagEntities(tagEntities);
        log.info("패션픽업-태그 중간 엔티티 설정");

        CategoryEntity categoryEntity = categoryService.getCategoryFromService(patchRequestDto.getChangedCategoryName());
        log.info("카테고리 데이터 확인 완료");


        PortfolioDto.PatchDto patchDto = PortfolioDto.PatchDto.builder()
                .changedTitle(patchRequestDto.getChangedTitle())
                .changedBody(patchRequestDto.getChangedBody())
                .changedCategoryEntity(categoryEntity)
                .changedTagList(tagEntities)
                .s3ImgInfo(s3ImageInfoList)
                .build();

        PortfolioEntity editedPortfolioEntity = portfolioEntityService.editPortfolioEntity(editingPortfolioEntity, patchDto);
        log.info("수정된 엔티티 저장 {} ", editedPortfolioEntity);

        PortfolioDto.ResponsePortfolioDtoForEntity responsePortfolioDto = portfolioMapper.fundingEntityToResponseFundingDto(editedPortfolioEntity);

        return new ResponseEntity(responsePortfolioDto,HttpStatus.OK);//수정된 entity 를 다시 반환함.
    }
    @Operation(summary = "포트폴리오 게시글 호출 예제", description = "json 바디값을 통한 포트폴리오 GET 메서드")//대상 api의 대한 설명을 작성하는 어노테이션
    @ApiResponses({
            @ApiResponse(responseCode = "200" ,description = "포트폴리오 게시글이 정상적으로 호출됨"),
            @ApiResponse(responseCode = "400", description = "BAD REQUEST !!"),
            @ApiResponse(responseCode = "404", description = "NOT FOUND !!"),
            @ApiResponse(responseCode = "500", description = "서버에서 에러가 발생하였습니다.")
    })
    @GetMapping("auth/get/{portfolioId}")//GET API
    public ResponseEntity getPortfolioEntity(@PathVariable("portfolioId") Long portfolioId){
        log.info("기존 패션픽업 게시글을 가져옵니다.");
        PortfolioEntity portfolio = portfolioEntityService.findPortfolioEntity(portfolioId);

        return new ResponseEntity(portfolioMapper.fundingEntityToResponseFundingDto(portfolio), HttpStatus.OK);
    }
    @Operation(summary = "포트폴리오 게시글 삭제 예제", description = "json 바디값을 통한 포트폴리오 DELETE 메서드")//대상 api의 대한 설명을 작성하는 어노테이션
    @ApiResponses({
            @ApiResponse(responseCode = "200" ,description = "포트폴리오 게시글이 정상적으로 호출됨"),
            @ApiResponse(responseCode = "400", description = "BAD REQUEST !!"),
            @ApiResponse(responseCode = "404", description = "NOT FOUND !!"),
            @ApiResponse(responseCode = "500", description = "서버에서 에러가 발생하였습니다.")
    })
    @DeleteMapping("/delete")//DELETE API
    public ResponseEntity deletePortfolioEntity(@RequestBody PortfolioDto.DeletePortfolioDtoDto deletePortfolioDtoDto){

        PortfolioEntity deletingPortfolioEntity = portfolioEntityService.findPortfolioEntity(deletePortfolioDtoDto.getPortfolioEntityId());
        log.info("수정할 객체 가져오기 {} ", deletingPortfolioEntity);

        List<String> entityUrlList = deletingPortfolioEntity.getS3ImgInfo().stream().map(S3ImageInfo::getFileName).collect(Collectors.toList());
        log.info("수정할 객체에 있는 이미지 데이터, s3에서 삭제하기 위해 호출 {} ", entityUrlList);


        portfolioEntityService.removePortfolioEntity(deletingPortfolioEntity);
        log.info("기존 패션픽업 게시글을 삭제합니다.");
        return new ResponseEntity(HttpStatus.OK);

    }


}
