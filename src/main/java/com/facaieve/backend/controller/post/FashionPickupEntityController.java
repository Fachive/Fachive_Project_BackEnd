package com.facaieve.backend.controller.post;


import com.facaieve.backend.dto.UserDto;
import com.facaieve.backend.mapper.post.FashionPickupMapper;

import com.facaieve.backend.dto.post.FashionPickupDto;
import com.facaieve.backend.entity.post.FashionPickupEntity;
import com.facaieve.backend.service.post.FashionPickupEntityService;
import com.facaieve.backend.stubDate.FashionPuckupStubData;
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
@RequestMapping("/fashionpickup")
@AllArgsConstructor
public class FashionPickupEntityController {

    FashionPickupEntityService fashionPickupEntityService;
    FashionPickupMapper fashionPickupMapper;

    static final FashionPuckupStubData fashionPuckupStubData = new FashionPuckupStubData();

    // 서비스 레이어 구현이 안되어 Stub 데이터로 대체(추후 변경 예정)


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
