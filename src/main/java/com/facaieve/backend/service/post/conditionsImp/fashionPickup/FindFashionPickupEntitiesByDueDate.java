package com.facaieve.backend.service.post.conditionsImp.fashionPickup;

import com.facaieve.backend.entity.etc.CategoryEntity;
import com.facaieve.backend.entity.post.FashionPickupEntity;
import com.facaieve.backend.repository.post.FashionPickupRepository;
import com.facaieve.backend.service.post.Condition;
import lombok.AllArgsConstructor;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Primary;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Service;

import java.util.List;
@AllArgsConstructor


public class FindFashionPickupEntitiesByDueDate implements Condition<FashionPickupEntity, CategoryEntity> {

    FashionPickupRepository fashionPickupRepository;

    @Override
    public Page<FashionPickupEntity> conditionSort(CategoryEntity categoryEntity, int pageIndex, int elementNum) {

        return fashionPickupRepository.findFashionPickupEntitiesByCategoryEntity(categoryEntity
                        , PageRequest.of(pageIndex - 1, elementNum, Sort.by("dueDate").descending()));
    }

}
