package com.facaieve.backend.service.image;

import com.facaieve.backend.entity.image.ImageEntityProfile;
import com.facaieve.backend.mapper.exception.BusinessLogicException;
import com.facaieve.backend.mapper.exception.ExceptionCode;
import com.facaieve.backend.repository.image.ImageRepository;
import com.facaieve.backend.repository.user.UserRepository;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.lang.Nullable;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import javax.validation.constraints.NotNull;
import java.io.IOException;

@Service
@Slf4j
@AllArgsConstructor
public class ImageService {

    ImageRepository imageRepository;
    UserRepository userRepository;

    public ImageEntityProfile uploadImage(@Nullable Long userEntityId , MultipartFile imgFile) throws IOException {
        log.info("이미지 파일 업로드 : {}", imgFile);

        ImageEntityProfile imageEntityProfile =  imageRepository.save(
                ImageEntityProfile.builder()
                        .fileName(imgFile.getOriginalFilename())//원본 파일명
                        .imageFileType(imgFile.getContentType())//이미지 파일 타입
                        .imageData(imgFile.getBytes())//원본 이미지 파일
                        .profileImgOwner(userRepository.findById(userEntityId).orElseThrow())
                        .build());

        return imageEntityProfile;
    }

    public ImageEntityProfile replaceImage(Long imageEntityId , @NotNull MultipartFile imgFile) throws IOException{
        ImageEntityProfile foundImageEntityProfile = imageRepository.findById(imageEntityId).orElseThrow();

        foundImageEntityProfile.setImageData(imgFile.getBytes());
        foundImageEntityProfile.setImageFileType(imgFile.getContentType());
        foundImageEntityProfile.setFileName(imgFile.getOriginalFilename());

        imageRepository.save(foundImageEntityProfile);

        log.info("기존의 이미지를 수정함 ");

        return foundImageEntityProfile;
    }


    public ImageEntityProfile findImageByFileName(String filename) {
        return imageRepository.findByFileName(filename).orElseThrow(() -> new BusinessLogicException(ExceptionCode.FILE_IS_NOT_EXIST));
    }





    public void removeImage(Long imageEntityId) {

        log.info("이미지 파일을 확인 후 삭제 : {}", imageEntityId);

        if(imageRepository.existsById(imageEntityId))
            imageRepository.deleteById(imageEntityId);


    }


    public String uriMaker(ImageEntityProfile image){

        return ServletUriComponentsBuilder.fromCurrentContextPath()
                .path("/img/download/")
                .path(String.valueOf(image.getFileName()))
                .toUriString();
    }




}