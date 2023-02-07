package com.facaieve.backend.controller.awsS3;

import com.facaieve.backend.service.aswS3.S3FileService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.InputStreamResource;
import org.springframework.core.io.Resource;
import org.springframework.http.CacheControl;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.util.Map;

@RestController
@RequestMapping("/api/v1/files")
@CrossOrigin(origins = "*", maxAge = 3600)
public class FileController {

    private static final String MESSAGE_1 = "Uploaded the file successfully";
    private static final String FILE_NAME = "fileName";

    @Autowired
    S3FileService s3FileService;

//    @GetMapping
//    public ResponseEntity<Resource> findByName(@RequestBody(required = false) Map<String, String> params) {
//        return ResponseEntity
//                .ok()
//                .cacheControl(CacheControl.noCache())
//                .header("Content-type", "application/octet-stream")
//                .header("Content-disposition", "attachment; filename=\"" + params.get(FILE_NAME) + "\"");
////                .body(new InputStreamResource(s3FileService.findByName(params.get(FILE_NAME))));
//
//    }

    @PostMapping
    public ResponseEntity<Object> save(@RequestParam("file") MultipartFile multipartFile) {
        s3FileService.uploadMultiFile(multipartFile);
        return new ResponseEntity<>(MESSAGE_1, HttpStatus.OK);
    }

}
