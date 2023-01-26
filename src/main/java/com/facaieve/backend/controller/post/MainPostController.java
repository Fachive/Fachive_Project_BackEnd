package com.facaieve.backend.controller.post;


import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@Slf4j
@RestController
@RequestMapping("/main")
@AllArgsConstructor
public class MainPostController {




    @GetMapping("/getten")
    public ResponseEntity get10Each(){




        return new ResponseEntity(HttpStatus.OK);
    }


}
