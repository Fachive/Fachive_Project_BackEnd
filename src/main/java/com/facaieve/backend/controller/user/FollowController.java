package com.facaieve.backend.controller.user;

import com.facaieve.backend.dto.etc.FollowDTO;
import com.facaieve.backend.service.user.FollowService;
import io.swagger.v3.oas.annotations.parameters.RequestBody;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@Slf4j
@RestController
@AllArgsConstructor
@RequestMapping("/follow")
public class FollowController {

    FollowService followService;

    @PostMapping("/post")
    public ResponseEntity createFollow(@RequestBody FollowDTO.PostFollow postFollow){

        followService.saveFollow(postFollow.getUserId(), postFollow.getFollowedUserId());
        log.info("팔로우 저장");
        return new ResponseEntity(HttpStatus.CREATED);
    }

    @DeleteMapping("/delete")
    public ResponseEntity deleteFollow(@RequestBody FollowDTO.PostFollow delete){

        followService.unFollow(delete.getUserId(), delete.getFollowedUserId());
        log.info("언팔로우");
        return new ResponseEntity(HttpStatus.CREATED);
    }

}
