package com.facaieve.backend.controller.post;


import com.facaieve.backend.dto.image.PostImageDto;
import com.facaieve.backend.dto.multi.Multi_ResponseDTO;
import com.facaieve.backend.dto.post.FashionPickupDto;
import com.facaieve.backend.dto.post.FundingDto;
import com.facaieve.backend.entity.image.PostImageEntity;
import com.facaieve.backend.mapper.post.PortfolioMapper;
import com.facaieve.backend.dto.post.PortfolioDto;
import com.facaieve.backend.entity.post.PortfolioEntity;
import com.facaieve.backend.service.aswS3.S3FileService;
import com.facaieve.backend.service.post.PortfolioEntityService;
import com.facaieve.backend.stubDate.PortfolioMagePageStubData;
import com.facaieve.backend.stubDate.PortfolioStubData;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.util.ArrayList;
import java.util.List;

@Slf4j
@RestController
@RequestMapping("/portfolio")
@AllArgsConstructor
public class PortfolioEntityController {

    PortfolioEntityService portfolioEntityService;
    PortfolioMapper portfolioMapper;
    S3FileService s3FileService;

    static final PortfolioStubData portfolioStubData = new PortfolioStubData();

    @GetMapping("/mainPageGet")
    public ResponseEntity getPortfolioMainPage(@RequestParam(required = false, defaultValue = "30") int want){
        Multi_ResponseDTO<PortfolioMagePageStubData> responseDTO = new Multi_ResponseDTO<>();
        List<PortfolioMagePageStubData> portfolioMagePageStubDataList = new ArrayList<>();
        for(int i = 0; i< want; i++){
            portfolioMagePageStubDataList.add(new PortfolioMagePageStubData());
        }
        responseDTO.setData(portfolioMagePageStubDataList);
        return new ResponseEntity(responseDTO,HttpStatus.OK);
    }

    @PostMapping("/multipartPost")
    public ResponseEntity postPortfolioEntity(@ModelAttribute PortfolioDto.RequestPortfolioIncludeMultiPartFiles
                                                          requestPortfolioIncludeMultiPartFiles){

        List<MultipartFile> multipartFileList = requestPortfolioIncludeMultiPartFiles.getMultipartFileList();
        List<PostImageDto> postImageDtoList = s3FileService.uploadMultiFileList(multipartFileList);

        PortfolioDto.ResponsePortfolioIncludeURI responsePortfolioIncludeURI =
                PortfolioDto.ResponsePortfolioIncludeURI.builder()
                        .portfolioEntityId(requestPortfolioIncludeMultiPartFiles.getPortfolioEntityId())
                        .title(requestPortfolioIncludeMultiPartFiles.getTitle())
                        .body(requestPortfolioIncludeMultiPartFiles.getBody())
                        .views(requestPortfolioIncludeMultiPartFiles.getViews())
                        .postImageDtoList(postImageDtoList)
                        .build();

        PortfolioEntity portfolio = portfolioMapper
                .responsePortfolioIncludeURIToPortfolioEntity(responsePortfolioIncludeURI);
        List<PostImageEntity> postImageEntities = portfolio.getPostImageEntities();

        //context 로 foregin key 저장하기 위해서 사용함
        for(PostImageEntity postImageEntity: postImageEntities){
            postImageEntity.setPortfolioEntity(portfolio);
        }

        return new ResponseEntity(portfolioMapper
                .portfolioEntityToResponsePortfolioIncludeURI(
                        portfolioEntityService.createPortfolioEntity(portfolio))
                ,HttpStatus.OK);
    }

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
            @ApiResponse(responseCode = "200" ,description = "포트폴리오 게시글이 수정됨", content = @Content(schema = @Schema(implementation = FashionPickupDto.ResponseFashionPickupDto.class))),
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
            @ApiResponse(responseCode = "200" ,description = "포트폴리오 게시글이 정상적으로 호출됨", content = @Content(schema = @Schema(implementation = FashionPickupDto.ResponseFashionPickupDto.class))),
            @ApiResponse(responseCode = "400", description = "BAD REQUEST !!"),
            @ApiResponse(responseCode = "404", description = "NOT FOUND !!"),
            @ApiResponse(responseCode = "500", description = "서버에서 에러가 발생하였습니다.")
    })
    @GetMapping("/get")
    public ResponseEntity getPortfolioEntity(@RequestBody PortfolioDto.GetPortfolioDtoDto getPortfolioDtoDto){
//        PortfolioEntity foundPortfolioEntity = portfolioEntityService.findPortfolioEntity(getPortfolioDtoDto.getPortfolioEntityId());
//        return new ResponseEntity( portfolioMapper.portfolioEntityToResponsePortfolioEntity(foundPortfolioEntity), HttpStatus.OK);
//        log.info("기존 패션픽업 게시글을 가져옵니다.");

        PortfolioEntity stubdata =  portfolioMapper.portfolioDtoToFashionPickupStubData(portfolioStubData);
        return new ResponseEntity( portfolioMapper.portfolioEntityToResponsePortfolioEntity(stubdata), HttpStatus.OK);
    }


    @Operation(summary = "포트폴리오 게시글 삭제 예제", description = "json 바디값을 통한 포트폴리오 DELETE 메서드")//대상 api의 대한 설명을 작성하는 어노테이션
    @ApiResponses({
            @ApiResponse(responseCode = "200" ,description = "포트폴리오 게시글이 정상적으로 호출됨", content = @Content(schema = @Schema(implementation = FashionPickupDto.ResponseFashionPickupDto.class))),
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
