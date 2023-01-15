package com.facaieve.backend.service.image;

import com.facaieve.backend.entity.image.ImageEntity;
import com.facaieve.backend.entity.image.ImageUtils;
import com.facaieve.backend.repository.image.ImageRepository;
import com.facaieve.backend.repository.user.UserRepository;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.lang.Nullable;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import javax.validation.constraints.NotNull;
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

    public ImageEntity replaceImage(Long imageEntityId , @NotNull MultipartFile imgFile) throws IOException{
        ImageEntity foundImageEntity = imageRepository.findById(imageEntityId).orElseThrow();

        foundImageEntity.setImageData(ImageUtils.compressImage(ImageUtils.compressImage(imgFile.getBytes())));
        foundImageEntity.setImageFileType(imgFile.getContentType());
        foundImageEntity.setFileName(imgFile.getOriginalFilename());

        imageRepository.save(foundImageEntity);

        log.info("기존의 이미지를 수정함 ");

        return foundImageEntity;
    }

    public void removeImage(long imageEntityId) {

        log.info("이미지 파일을 확인 후 삭제 : {}", imageEntityId);

        if(imageRepository.existsById(imageEntityId))
            imageRepository.deleteById(imageEntityId);


    }
}
