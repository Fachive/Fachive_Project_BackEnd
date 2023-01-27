package com.facaieve.backend.service.post.conditionsImp.fashionPickup;

import com.facaieve.backend.entity.etc.CategoryEntity;
import com.facaieve.backend.entity.post.FashionPickupEntity;
import com.facaieve.backend.repository.post.FashionPickupRepository;

import com.facaieve.backend.service.post.Condition;
import lombok.AllArgsConstructor;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;

import java.util.Comparator;
import java.util.List;


@AllArgsConstructor
public class FindFashionPickupEntitiesByMyPicks implements Condition<FashionPickupEntity, CategoryEntity> {

    FashionPickupRepository fashionPickupRepository;
    @Override
    public Page<FashionPickupEntity> conditionSort(List<CategoryEntity> categoryEntities, int pageIndex, int elementNum) {

        Page<FashionPickupEntity> fashionPickupEntities = fashionPickupRepository
                .findAllByCategoryEntities(
                        categoryEntities
                        , PageRequest.of(pageIndex - 1, elementNum, Sort.by("views").descending()));

        fashionPickupEntities.stream().sorted(new Comparator<FashionPickupEntity>() {
            @Override
            public int compare(FashionPickupEntity o1, FashionPickupEntity o2) {
                    return o1.getMyPick().size() - o2.getMyPick().size();
            }
        });

        return fashionPickupEntities;
    }
}
