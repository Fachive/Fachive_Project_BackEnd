package com.facaieve.backend.controller;

import com.facaieve.backend.service.FollowService;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@Slf4j
@RestController
@AllArgsConstructor
@RequestMapping("/follow")
public class FollowController {

    FollowService followService;

    @GetMapping("/checkfollow")
    public ResponseEntity createFollow(@PathVariable("id") long myUserId, long followedUserId){

        followService.saveFollow(myUserId, followedUserId);
        log.info("팔로우 저장");
        return new ResponseEntity(HttpStatus.CREATED);
    }

    @GetMapping("/deletefollow")
    public ResponseEntity deleteFollow(@PathVariable("id") long myUserId, long followedUserId){

        followService.unFollow(myUserId, followedUserId);
        log.info("언팔로우");
        return new ResponseEntity(HttpStatus.CREATED);
    }

}
