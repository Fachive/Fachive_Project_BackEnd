package com.facaieve.backend.controller.etc;

import com.facaieve.backend.dto.etc.MyPickDTO;
import com.facaieve.backend.mapper.etc.MyPickMapper;
import com.facaieve.backend.service.etc.MyPickService;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
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


    @PostMapping("/post")//test pass
    public ResponseEntity postMyPick(@RequestBody MyPickDTO.PostMyPickDTO postMyPickDTO){

        postMyPickDTO.setUserId(Long.valueOf(1));//설정값

        return new ResponseEntity(myPickService.createMyPick(myPickMapper.postMyPickDtoToMyPickEntity(postMyPickDTO)),
                HttpStatus.CREATED);
    }

    @GetMapping("/get")//test pass
    public ResponseEntity getMyPick(@RequestBody MyPickDTO.ResponseMyPickDTO responseMyPickDTO){//Response 와 동일한거 사용함.

        responseMyPickDTO.setMyPickId(Long.valueOf(1));
        responseMyPickDTO.setUserId(Long.valueOf(1));

        return new ResponseEntity(myPickService
                .getMyPick(myPickMapper.responseMyPickDtoToMyPickEntity(responseMyPickDTO)),
                HttpStatus.OK);

    }

    //유저 아디로 삭제하는게 필요함
    @DeleteMapping("/delete")//test pass
    public void deleteMyPick(@RequestBody MyPickDTO.ResponseMyPickDTO responseMyPickDTO){

        responseMyPickDTO.setMyPickId(Long.valueOf(1));//설정값
        responseMyPickDTO.setUserId(Long.valueOf(1));//설정값

        myPickService.deleteMyPick(myPickMapper.responseMyPickDtoToMyPickEntity(responseMyPickDTO));
        log.info("delete complete");
    }

    //todo 수정 메소드 필요하지 않을 것이라고 판단해서 구현하지 않음.
}
