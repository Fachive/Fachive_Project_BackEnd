package com.facaieve.backend.service.post;

import com.facaieve.backend.dto.post.FashionPickupDto;
import com.facaieve.backend.entity.etc.CategoryEntity;
import com.facaieve.backend.entity.post.FashionPickupEntity;
import com.facaieve.backend.mapper.exception.BusinessLogicException;
import com.facaieve.backend.mapper.exception.ExceptionCode;
import com.facaieve.backend.repository.post.FashionPickupRepository;
import com.facaieve.backend.service.post.conditionsImp.fashionPickup.FindFashionPickupEntitiesByDueDate;
import com.facaieve.backend.service.post.conditionsImp.fashionPickup.FindFashionPickupEntitiesByMyPicks;
import com.facaieve.backend.service.post.conditionsImp.fashionPickup.FindFashionPickupEntitiesByViews;
import com.facaieve.backend.service.user.UserService;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.Setter;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
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

    @Transactional
    public FashionPickupEntity createFashionPickupEntity(FashionPickupEntity fashionPickupEntity) {// 패션픽업 게시물 작성
           return fashionPickupRepository.save(fashionPickupEntity);
    }


    public boolean isEqual(Object newElement, Object oldElement) {
        return newElement.equals(oldElement);
    }


    public FashionPickupEntity editFashionPickupEntity(FashionPickupEntity editingFashionPickupEntity, FashionPickupDto.PatchDto patchDto) {//패션픽업 게시물 수정)


        Optional.ofNullable(patchDto.getChangedTitle())
                .ifPresent(editingFashionPickupEntity::setTitle);
        Optional.ofNullable(patchDto.getChangedBody())
                .ifPresent(editingFashionPickupEntity::setBody);
        Optional.ofNullable(patchDto.getCategoryEntity())
                .ifPresent(editingFashionPickupEntity::setCategoryEntity);
        Optional.ofNullable(patchDto.getTagEntities())
                .ifPresent(editingFashionPickupEntity::setTagEntities);
        Optional.ofNullable(patchDto.getS3ImgInfo())
                .ifPresent(editingFashionPickupEntity::setS3ImgInfo);

        return fashionPickupRepository.save(editingFashionPickupEntity);
    }


    public FashionPickupEntity findFashionPickupEntity(Long foundingFashionPickupEntityId) {//패션픽업 게시물 호출

        return fashionPickupRepository.findById(foundingFashionPickupEntityId).orElseThrow(() -> new BusinessLogicException(ExceptionCode.NO_SUCH_POST_ENTITY));
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

    public void removeFashionPickupEntity(FashionPickupEntity deletingFashionPickupEntity) {// 패션픽업 게시물 삭제
        fashionPickupRepository.delete(deletingFashionPickupEntity);
    }



}
