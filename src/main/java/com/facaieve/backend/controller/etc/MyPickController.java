package com.facaieve.backend.controller.etc;

import com.facaieve.backend.dto.etc.MyPickDTO;
import com.facaieve.backend.mapper.etc.MyPickMapper;
import com.facaieve.backend.service.etc.MyPickService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/MyPick")
@AllArgsConstructor
@Slf4j

public class MyPickController {


    MyPickService myPickService;
    MyPickMapper myPickMapper;

    @Operation(summary = "좋아요 등록 메서드", description = "json 바디값을 통한 좋아요 등록")//대상 api의 대한 설명을 작성하는 어노테이션
    @ApiResponses({
            @ApiResponse(responseCode = "201" ,description = "사용자가 게시물 혹은 댓글에 좋아요를 달았습니다."),
            @ApiResponse(responseCode = "500", description = "서버에서 에러가 발생하였습니다.")
    })
    @io.swagger.annotations.ApiResponses(
            @io.swagger.annotations.ApiResponse( message = "ok", code=201)
    )
    @PostMapping("/post")//test pass
    public ResponseEntity postMyPick(@RequestBody MyPickDTO.PostMyPickDTO postMyPickDTO){

        myPickService.createMyPick(postMyPickDTO.getUserId(), postMyPickDTO.getWhatToPick(),postMyPickDTO.getEntityId());

        return new ResponseEntity(
                HttpStatus.CREATED);
    }

    @GetMapping("/get")//test pass
    public ResponseEntity getMyPick(@RequestParam Long myPickId, Long userId){//Response 와 동일한거 사용함.

             return new ResponseEntity(HttpStatus.OK);
    }

    @Operation(summary = "좋아요 취소 메서드", description = "json 바디값을 통한 좋아요 취소")//대상 api의 대한 설명을 작성하는 어노테이션
    @ApiResponses({
            @ApiResponse(responseCode = "201" ,description = "사용자가 게시물 혹은 댓글의 좋아요를 취소했습니다."),
            @ApiResponse(responseCode = "500", description = "서버에서 에러가 발생하였습니다.")
    })
    @DeleteMapping("/delete")//test pass
    public void deleteMyPick(@RequestBody MyPickDTO.PostMyPickDTO postMyPickDTO){

        myPickService.deleteMyPick(postMyPickDTO.getUserId(), postMyPickDTO.getWhatToPick(),postMyPickDTO.getEntityId());
        log.info("delete complete");

    }

    //todo 수정 메소드 필요하지 않을 것이라고 판단해서 구현하지 않음.
}
