package com.facaieve.backend.service.image;

import com.facaieve.backend.entity.image.PostImageEntity;
import com.facaieve.backend.exception.BusinessLogicException;
import com.facaieve.backend.repository.image.PostImageRepository;
import lombok.AllArgsConstructor;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;

import static com.facaieve.backend.exception.ExceptionCode.FILE_IS_NOT_EXIST_IN_BUCKET;
import static com.facaieve.backend.exception.ExceptionCode.REQUESTING_FILE_ALREADY_EXIST;

@Service
@Slf4j
@AllArgsConstructor
@RequiredArgsConstructor
public class PostImageService implements ImageHandler<PostImageEntity,PostImageEntity> {

    @Autowired
    PostImageRepository postImageRepository;


    @Override
    public PostImageEntity getImage(PostImageEntity postImageEntity) {

        if(postImageRepository.existsByFileName(postImageEntity.getFileName())){
            return postImageRepository.findByFileName(postImageEntity.getFileName());
        }else{
            throw new BusinessLogicException(FILE_IS_NOT_EXIST_IN_BUCKET);
        }
    }

    @Override
    @Transactional
    public PostImageEntity modifyImage(PostImageEntity postImageEntity) {
        if(postImageRepository.existsByFileName(postImageEntity.getFileName())){
            PostImageEntity updatedPostImage = postImageRepository.findByFileName(postImageEntity.getFileName());
            updatedPostImage.update(postImageEntity.getFileName(), postImageEntity.getFileURI());
            return updatedPostImage;

        }else{
            throw new BusinessLogicException(FILE_IS_NOT_EXIST_IN_BUCKET);
        }
    }

    @Override
    public String deleteImage(PostImageEntity postImageEntity) {

        if(postImageRepository.existsByFileName(postImageEntity.getFileName())){
            postImageRepository.deleteByFileName(postImageEntity.getFileName());
            return "image :"+ postImageEntity.getFileURI() +"has been deleted";

        }else{
            throw new BusinessLogicException(FILE_IS_NOT_EXIST_IN_BUCKET);
        }
    }

    @Override
    public PostImageEntity createImage(PostImageEntity postImageEntity) {
        if(postImageRepository.existsByFileName(postImageEntity.getFileName())) {
            throw new BusinessLogicException(REQUESTING_FILE_ALREADY_EXIST);
        }else{
            return postImageRepository.save(postImageEntity);
        }
    }
}
