package com.facaieve.backend.controller.post;



import com.facaieve.backend.dto.UserDto;
import com.facaieve.backend.mapper.post.FundingMapper;

import com.facaieve.backend.dto.post.FundingDto;
import com.facaieve.backend.entity.post.FundingEntity;
import com.facaieve.backend.service.post.FundingEntityService;
import com.facaieve.backend.stubDate.FundingStubData;
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

@Slf4j
@RestController
@RequestMapping("/funding")
@AllArgsConstructor
public class FundingEntityController {

    FundingEntityService fundingEntityService;
    FundingMapper fundingMapper;

    static final FundingStubData fundingStubData = new FundingStubData();

    // 서비스 레이어 구현이 안되어 Stub 데이터로 대체(추후 변경 예정)

    @Operation(summary = "펀딩 게시글 작성 메서드 예제", description = "json 바디값을 통한 펀딩 게시글 POST 요청 메서드")//대상 api의 대한 설명을 작성하는 어노테이션
    @ApiResponses({
            @ApiResponse(responseCode = "201" ,description = "펀딩 게시글이 정상 등록됨", content = @Content(schema = @Schema(implementation = FundingDto.ResponseFundingDto.class))),
            @ApiResponse(responseCode = "400", description = "BAD REQUEST !!"),
            @ApiResponse(responseCode = "404", description = "NOT FOUND !!"),
            @ApiResponse(responseCode = "500", description = "서버에서 에러가 발생하였습니다.")
    })
    @PostMapping("/post")
    public ResponseEntity postFundingEntity(@RequestBody FundingDto.PostFundingDto postFundingDto) {
//        FundingEntity postingFundingEntity = fundingMapper.fundingPostDtoToFundingEntity(postFundingDto);
//        FundingEntity postedFundingEntity = fundingEntityService.createFundingEntity(postingFundingEntity);
//        return new ResponseEntity( fundingMapper.fundingEntityToResponseFundingEntity(postedFundingEntity), HttpStatus.OK);

        FundingEntity stubdata =  fundingMapper.fundingDtoToFundingEntityStubData(fundingStubData);
        log.info("새로운 펀딩 게시물을 등록합니다.");
        return new ResponseEntity( fundingMapper.fundingEntityToResponseFundingEntity(stubdata), HttpStatus.OK);
    }


    @Operation(summary = "펀딩 게시글 수정 메서드 예제", description = "json 바디값을 통한 펀딩 게시글 PATCH 요청 메서드")//대상 api의 대한 설명을 작성하는 어노테이션
    @ApiResponses({
            @ApiResponse(responseCode = "200" ,description = "펀딩 게시글이 수정되었습니다 ", content = @Content(schema = @Schema(implementation = FundingDto.ResponseFundingDto.class))),
            @ApiResponse(responseCode = "400", description = "BAD REQUEST !!"),
            @ApiResponse(responseCode = "404", description = "NOT FOUND !!"),
            @ApiResponse(responseCode = "500", description = "서버에서 에러가 발생하였습니다.")
    })
    @PatchMapping("/patch")
    public ResponseEntity patchFundingEntity(@RequestBody FundingDto.PatchFundingDto patchFundingDto){
//        FundingEntity patchingFundingEntity = fundingMapper.fundingPatchDtoToFundingEntity(patchFundingDto);
//        FundingEntity patchedFundingEntity = fundingEntityService.createFundingEntity(patchingFundingEntity);
//        return new ResponseEntity( fundingMapper.fundingEntityToResponseFundingEntity(patchedFundingEntity), HttpStatus.OK);

        FundingEntity stubdata =  fundingMapper.fundingDtoToFundingEntityStubData(fundingStubData);
        stubdata.setTitle("펀딩 게시글 제목 수정완료");
        stubdata.setBody("펀딩 게시글 내용 수정완료");

        log.info("기존 펀딩 게시물을 수정합니다.");
        return new ResponseEntity( fundingMapper.fundingEntityToResponseFundingEntity(stubdata), HttpStatus.OK);
    }


    @Operation(summary = "펀딩 게시글 호출 메서드 예제", description = "json 바디값을 통한 펀딩 게시글 GET 요청 메서드")//대상 api의 대한 설명을 작성하는 어노테이션
    @ApiResponses({
            @ApiResponse(responseCode = "200" ,description = "펀딩 게시글이 호출 되었습니다", content = @Content(schema = @Schema(implementation = FundingDto.ResponseFundingDto.class))),
            @ApiResponse(responseCode = "400", description = "BAD REQUEST !!"),
            @ApiResponse(responseCode = "404", description = "NOT FOUND !!"),
            @ApiResponse(responseCode = "500", description = "서버에서 에러가 발생하였습니다.")
    })
    @GetMapping("/get")
    public ResponseEntity getFundingEntity(@RequestBody FundingDto.GetFundingDto getFundingDto){
//        FundingEntity foundFundingEntity = fundingEntityService.findFundingEntity(getFundingDto.getFundingEntityId());
//        return new ResponseEntity( fundingMapper.fundingEntityToResponseFundingEntity(foundFundingEntity), HttpStatus.OK);
        log.info("기존 펀딩 게시글을 가져옵니다.");

        FundingEntity stubdata =  fundingMapper.fundingDtoToFundingEntityStubData(fundingStubData);
        return new ResponseEntity( fundingMapper.fundingEntityToResponseFundingEntity(stubdata), HttpStatus.OK);
    }

    @Operation(summary = "펀딩 게시글 삭제 메서드 예제", description = "json 바디값을 통한 펀딩 게시글 DELETE 요청 메서드")//대상 api의 대한 설명을 작성하는 어노테이션
    @ApiResponses({
            @ApiResponse(responseCode = "200" ,description = "펀딩 게시글이 삭제 되었습니다", content = @Content(schema = @Schema(implementation = FundingDto.ResponseFundingDto.class))),
            @ApiResponse(responseCode = "400", description = "BAD REQUEST !!"),
            @ApiResponse(responseCode = "404", description = "NOT FOUND !!"),
            @ApiResponse(responseCode = "500", description = "서버에서 에러가 발생하였습니다.")
    })
    @DeleteMapping()
    public ResponseEntity deleteFundingEntity(@RequestBody FundingDto.DeleteFundingDto deleteFundingDto){

        fundingEntityService.removeFundingEntity(deleteFundingDto.getFundingEntityId());
        log.info("기존 패션픽업 게시글을 삭제합니다.");
        return new ResponseEntity(HttpStatus.OK);



    }


}
