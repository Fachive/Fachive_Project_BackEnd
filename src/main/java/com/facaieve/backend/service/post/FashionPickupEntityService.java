package com.facaieve.backend.service.post;

import com.facaieve.backend.entity.post.FashionPickupEntity;
import com.facaieve.backend.entity.user.UserEntity;
import com.facaieve.backend.repository.post.FashionPickupRepository;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import java.util.Optional;
import java.util.function.Consumer;

@Slf4j
@Service
@AllArgsConstructor
public class FashionPickupEntityService {

    FashionPickupRepository fashionPickupRepository;

    public FashionPickupEntity createFashionPickupEntity(FashionPickupEntity fashionPickupEntity){// 패션픽업 게시물 작성

        return fashionPickupRepository.save(fashionPickupEntity);
    };

    public boolean isEqual(Object newElement, Object oldElement){
        return newElement.equals(oldElement);
    }



    public FashionPickupEntity editFashionPickupEntity(FashionPickupEntity fashionPickupEntity) {//패션픽업 게시물 수정)

        FashionPickupEntity newFashionPickupEntity = new FashionPickupEntity();
        Optional.ofNullable(fashionPickupEntity.getTitle())
                .ifPresent(newFashionPickupEntity::setTitle);
        Optional.ofNullable(fashionPickupEntity.getBody())
                .ifPresent(newFashionPickupEntity::setBody);
        // 추후 첨부 이미지 수정하는 기능도 추가 필요

        return fashionPickupRepository.save(newFashionPickupEntity);
    }
    //
    public FashionPickupEntity findFashionPickupEntity(long foundingFashionPickupEntityId) {//패션픽업 게시물 호출
        return fashionPickupRepository.findById(foundingFashionPickupEntityId).orElseThrow();
    }


    public Page<FashionPickupEntity> findFashionPickupEntities(int pageIndex) {//패션픽업 게시물 페이지별로 호출
        return fashionPickupRepository.findAll(PageRequest.of(pageIndex, 30, Sort.by("updateTime").descending()));
    }


    public void removeFashionPickupEntity(long deletingFashionPickupEntityId) {// 패션픽업 게시물 삭제
        fashionPickupRepository.deleteById(deletingFashionPickupEntityId);
    }

//    public FashionPickupEntity

}
