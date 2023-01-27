package com.facaieve.backend.service.post.conditionsImp.funding;

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

import java.util.List;
@AllArgsConstructor
@RequiredArgsConstructor
public class FindFundingEntitiesByDueDate implements Condition<FundingEntity, CategoryEntity> {
    @Autowired
    FundingRepository fundingRepository;

    @Override
    public Page<FundingEntity> conditionSort(List<CategoryEntity> categoryEntities, int pageIndex, int elementNum) {

        return fundingRepository
                .findAllByCategoryEntities(
                        categoryEntities
                        , PageRequest.of(pageIndex - 1, elementNum, Sort.by("dueDate").descending()));
    }

}
