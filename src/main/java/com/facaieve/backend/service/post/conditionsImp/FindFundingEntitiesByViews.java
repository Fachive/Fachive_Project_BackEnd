package com.facaieve.backend.service.post.conditionsImp;

import com.facaieve.backend.entity.etc.CategoryEntity;
import com.facaieve.backend.entity.post.FundingEntity;
import com.facaieve.backend.repository.post.FundingRepository;
import com.facaieve.backend.service.post.Condition;
import lombok.AllArgsConstructor;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import java.util.List;
@RequiredArgsConstructor
@Service

public class FindFundingEntitiesByViews implements Condition<FundingEntity, CategoryEntity> {

    @Autowired
    FundingRepository fundingRepository;
    @Override
    public Page<FundingEntity> conditionSort(List<CategoryEntity> categoryEntities, int pageIndex) {
        return  fundingRepository
                .findFundingEntitiesByCategoryEntitiesIn( categoryEntities,
                        PageRequest.of(pageIndex-1, 30, Sort.by("views").descending()));
    }
}
