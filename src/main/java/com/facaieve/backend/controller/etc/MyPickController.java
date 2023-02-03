package com.facaieve.backend.controller.etc;

import com.facaieve.backend.dto.etc.MyPickDTO;
import com.facaieve.backend.entity.etc.MyPickEntity;
import com.facaieve.backend.mapper.etc.MyPickMapper;
import com.facaieve.backend.service.etc.MyPickService;
import com.facaieve.backend.service.post.conditionsImp.fashionPickup.FindFashionPickupEntitiesByDueDate;
import com.facaieve.backend.service.post.conditionsImp.fashionPickup.FindFashionPickupEntitiesByMyPicks;
import com.facaieve.backend.service.post.conditionsImp.fashionPickup.FindFashionPickupEntitiesByViews;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import static com.facaieve.backend.dto.etc.MyPickDTO.PostMyPickDTO.PickableEntity.*;

@RestController
@RequestMapping("/MyPick")
@AllArgsConstructor
@Slf4j

public class MyPickController {


    MyPickService myPickService;
    MyPickMapper myPickMapper;


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

    //유저 아디로 삭제하는게 필요함
    @DeleteMapping("/delete")//test pass
    public void deleteMyPick(@RequestBody MyPickDTO.PostMyPickDTO postMyPickDTO){

        myPickService.deleteMyPick(postMyPickDTO.getUserId(), postMyPickDTO.getWhatToPick(),postMyPickDTO.getEntityId());
        log.info("delete complete");
    }

    //todo 수정 메소드 필요하지 않을 것이라고 판단해서 구현하지 않음.
}
