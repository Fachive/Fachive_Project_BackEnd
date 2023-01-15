package com.facaieve.backend.controller.user;

import com.facaieve.backend.service.user.FollowService;
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
    public ResponseEntity createFollow(@PathVariable("id") long myUserId, long followedUserId){

        followService.saveFollow(myUserId, followedUserId);
        log.info("팔로우 저장");
        return new ResponseEntity(HttpStatus.CREATED);
    }

    @DeleteMapping("/delete")
    public ResponseEntity deleteFollow(@PathVariable("id") long myUserId, long followedUserId){

        followService.unFollow(myUserId, followedUserId);
        log.info("언팔로우");
        return new ResponseEntity(HttpStatus.CREATED);
    }

}
