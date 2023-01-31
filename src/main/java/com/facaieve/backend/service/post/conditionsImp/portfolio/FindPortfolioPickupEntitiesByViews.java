package com.facaieve.backend.service.post.conditionsImp.portfolio;

import com.facaieve.backend.entity.etc.CategoryEntity;
import com.facaieve.backend.entity.post.PortfolioEntity;
import com.facaieve.backend.repository.post.PortfolioRepository;
import com.facaieve.backend.service.post.Condition;
import lombok.AllArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import java.util.List;

@AllArgsConstructor

public class FindPortfolioPickupEntitiesByViews implements Condition<PortfolioEntity, CategoryEntity> {
    PortfolioRepository portfolioRepository;
    @Override
    public Page<PortfolioEntity> conditionSort(CategoryEntity categoryEntities, int pageIndex, int elementNum) {
        return  portfolioRepository
                .findPortfolioEntitiesByCategoryEntity(
                        categoryEntities
                        , PageRequest.of(pageIndex-1, elementNum, Sort.by("views").descending()));
    }
}
