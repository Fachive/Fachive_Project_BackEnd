package com.facaieve.backend.service.post.conditionsImp.funding;

import com.facaieve.backend.entity.etc.CategoryEntity;
import com.facaieve.backend.entity.post.FundingEntity;
import com.facaieve.backend.repository.post.FundingRepository;
import com.facaieve.backend.service.post.Condition;
import io.swagger.v3.oas.annotations.servers.Server;
import lombok.AllArgsConstructor;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Primary;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import java.util.List;
@AllArgsConstructor


public class FindFundingEntitiesByDueDate implements Condition<FundingEntity, CategoryEntity> {
    FundingRepository fundingRepository;

    @Override
    public Page<FundingEntity> conditionSort(CategoryEntity categoryEntity, int pageIndex, int elementNum) {

        PageRequest pageRequest = PageRequest.of(pageIndex - 1, elementNum, Sort.by("updateTime").descending());

        if(categoryEntity.getCategoryName().equals("total")){
            return fundingRepository.findAll(pageRequest);
        }
            else{ return fundingRepository.findFundingEntitiesByCategoryEntity(categoryEntity, pageRequest);
        }
    }

}
