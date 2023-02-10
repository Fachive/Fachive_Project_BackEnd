package com.facaieve.backend.service.post.conditionsImp.funding;

import com.facaieve.backend.entity.etc.CategoryEntity;
import com.facaieve.backend.entity.etc.MyPickEntity;
import com.facaieve.backend.entity.post.FashionPickupEntity;
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

import java.util.Comparator;
import java.util.List;

@AllArgsConstructor

public class FindFundingEntitiesByMyPicks implements Condition<FundingEntity, CategoryEntity> {

    FundingRepository fundingRepository;
    @Override
    public Page<FundingEntity> conditionSort(CategoryEntity categoryEntity, int pageIndex, int elementNum) {

        PageRequest pageRequest = PageRequest.of(pageIndex - 1, elementNum, Sort.by("views").descending());
        Page<FundingEntity> fundingEntities;

        if(categoryEntity.getCategoryName().equals("total")) {
            fundingEntities = fundingRepository.findAll(pageRequest);
        }
        else{
            fundingEntities = fundingRepository.findFundingEntitiesByCategoryEntity(categoryEntity, pageRequest);
        }

        fundingEntities.stream().sorted(new Comparator<FundingEntity>() {
            @Override
            public int compare(FundingEntity o1, FundingEntity o2) {
                    return o1.getMyPick().size() - o2.getMyPick().size();
            }
        });

        return fundingEntities;
    }
}
