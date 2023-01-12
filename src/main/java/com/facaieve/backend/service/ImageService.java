package com.facaieve.backend.service;

import com.facaieve.backend.entity.ImageEntity;
import com.facaieve.backend.entity.ImageUtils;
import com.facaieve.backend.repository.ImageRepository;
import com.facaieve.backend.repository.user.UserRepository;
import com.facaieve.backend.service.user.UserService;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.lang.Nullable;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.awt.image.ImagingOpException;
import java.io.IOException;

@Service
@Slf4j
@AllArgsConstructor
public class ImageService {

    ImageRepository imageRepository;
    UserRepository userRepository;

    public ImageEntity uploadImage(@Nullable Long userEntityId , MultipartFile imgFile) throws IOException {
        log.info("이미지 파일 업로드 : {}", imgFile);

        ImageEntity imageEntity =  imageRepository.save(
                ImageEntity.builder()
                        .fileName(imgFile.getOriginalFilename())//원본 파일명
                        .imageFileType(imgFile.getContentType())//이미지 파일 타입
                        .imageData(ImageUtils.compressImage(imgFile.getBytes()))//원본 이미지 파일
                        .build());

        if(userEntityId!=null){
            imageEntity.setProfileImgOwner(userRepository.findById(userEntityId).orElseThrow());
        }

        return imageEntity;
    }


    public void removeImage(long imageEntityId) {
        if(imageRepository.existsById(imageEntityId))
            imageRepository.deleteById(imageEntityId);


    }
}
