package com.facaieve.backend.service.post.conditionsImp.fashionPickup;

import com.facaieve.backend.entity.etc.CategoryEntity;
import com.facaieve.backend.entity.post.FashionPickupEntity;
import com.facaieve.backend.repository.post.FashionPickupRepository;
import com.facaieve.backend.repository.post.FundingRepository;
import com.facaieve.backend.service.post.Condition;
import lombok.AllArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;

import java.util.List;

@AllArgsConstructor
public class FindFashionPickupEntitiesByViews implements Condition<FashionPickupEntity, CategoryEntity> {


    FashionPickupRepository fashionPickupRepository;
    @Override
    public Page<FashionPickupEntity> conditionSort(List<CategoryEntity> categoryEntities, int pageIndex, int elementNum) {
        return  fashionPickupRepository
                .findAllByCategoryEntities(
                        categoryEntities
                        , PageRequest.of(pageIndex-1, elementNum, Sort.by("views").descending()));
    }
}
