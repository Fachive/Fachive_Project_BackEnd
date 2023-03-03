package com.facaieve.backend.service.post.conditionsImp.portfolio;

import com.facaieve.backend.entity.etc.CategoryEntity;
import com.facaieve.backend.entity.post.FashionPickupEntity;
import com.facaieve.backend.entity.post.PortfolioEntity;
import com.facaieve.backend.repository.post.PortfolioRepository;
import com.facaieve.backend.service.post.Condition;
import lombok.AllArgsConstructor;
import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Primary;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import java.util.List;
@AllArgsConstructor
public class FindPortfolioEntitiesByDueDate implements Condition<PortfolioEntity, CategoryEntity> {

    PortfolioRepository portfolioRepository;

    @Override
    public Page<PortfolioEntity> conditionSort(CategoryEntity categoryEntity, int pageIndex,  int elementNum) {
        PageRequest pageRequest = PageRequest.of(pageIndex - 1, elementNum, Sort.by("regTime").descending());
        Page<FashionPickupEntity> fashionPickupEntities;

        if(categoryEntity.getCategoryName().equals("total")){
            return portfolioRepository.findAll(pageRequest);
        }
        else{
            return portfolioRepository.findPortfolioEntitiesByCategoryEntity(categoryEntity ,pageRequest);
        }
    }
}
