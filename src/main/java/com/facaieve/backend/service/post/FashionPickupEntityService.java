package com.facaieve.backend.service.post;

import com.facaieve.backend.entity.etc.CategoryEntity;
import com.facaieve.backend.entity.post.FashionPickupEntity;
import com.facaieve.backend.repository.post.FashionPickupRepository;
import com.facaieve.backend.service.post.conditionsImp.fashionPickup.FindFashionPickupEntitiesByDueDate;
import com.facaieve.backend.service.post.conditionsImp.fashionPickup.FindFashionPickupEntitiesByMyPicks;
import com.facaieve.backend.service.post.conditionsImp.fashionPickup.FindFashionPickupEntitiesByViews;
import com.facaieve.backend.service.user.UserService;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.Setter;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Service;

import java.util.*;

@Getter
@Setter
@Slf4j
@Service
@RequiredArgsConstructor
public class FashionPickupEntityService {
    @Autowired
    FashionPickupRepository fashionPickupRepository;
    @Autowired
    UserService userService;

    Condition condition;//정렬 메소드 가지고 있는 객체

    public FashionPickupEntity createFashionPickupEntity(FashionPickupEntity fashionPickupEntity) {// 패션픽업 게시물 작성

        return fashionPickupRepository.save(fashionPickupEntity);
    }

    ;

    public boolean isEqual(Object newElement, Object oldElement) {
        return newElement.equals(oldElement);
    }


    public FashionPickupEntity editFashionPickupEntity(FashionPickupEntity fashionPickupEntity) {//패션픽업 게시물 수정)

        FashionPickupEntity newFashionPickupEntity = new FashionPickupEntity();
        Optional.ofNullable(fashionPickupEntity.getTitle())
                .ifPresent(newFashionPickupEntity::setTitle);
        Optional.ofNullable(fashionPickupEntity.getBody())
                .ifPresent(newFashionPickupEntity::setBody);

        Optional.ofNullable(newFashionPickupEntity.getPostImageEntities())
                .ifPresent(newFashionPickupEntity::setPostImageEntities);
        // 추후 첨부 이미지 수정하는 기능도 추가 완료

        return fashionPickupRepository.save(newFashionPickupEntity);
    }

    //
    public FashionPickupEntity findFashionPickupEntity(Long foundingFashionPickupEntityId) {//패션픽업 게시물 호출

        return fashionPickupRepository.findById(foundingFashionPickupEntityId).orElseThrow();
    }

    public void setCondition(String sortWay) {

        switch (sortWay) {
            case "myPick":
                this.condition = new FindFashionPickupEntitiesByMyPicks(fashionPickupRepository);
            case "update":
                this.condition = new FindFashionPickupEntitiesByDueDate(fashionPickupRepository);
            default:
                this.condition = new FindFashionPickupEntitiesByViews(fashionPickupRepository);
        }
    }

    public Page<FashionPickupEntity> findFashionPickupEntitiesByCondition(CategoryEntity categoryEntity, int pageIndex, int elementNum) {
        return condition.conditionSort(categoryEntity, pageIndex, elementNum);
    }


//
//    public Page<FashionPickupEntity> findFashionPickupEntitiesByUpdatedBy(int pageIndex) {//패션픽업 게시물 페이지별로 호출(최신순)
//        return fashionPickupRepository.findAll(PageRequest.of(pageIndex-1, 30, Sort.by("updateTime").descending()));
//    }
//
//    public Page<FashionPickupEntity> findFashionPickupEntitiesByPick(int pageIndex) {//패션픽업 게시물 페이지별로 호출(조회순순)
//       return fashionPickupRepository.findAll(PageRequest.of(pageIndex-1, 30, Sort.by("myPicks").descending()));
//    }
//
//    public List<FashionPickupEntity> findFashionPickupEntitiesByMyPick(int pageIndex, Long userId) {//패션픽업 게시물 페이지별로 호출(조회순순)
//
//        UserEntity foundUserEntity = userService.findUserEntityById(userId);
//
//
//        return foundUserEntity.getFashionPickupEntities();
//    }


    public void removeFashionPickupEntity(Long deletingFashionPickupEntityId) {// 패션픽업 게시물 삭제
        fashionPickupRepository.deleteById(deletingFashionPickupEntityId);
    }

//    public FashionPickupEntity

}
