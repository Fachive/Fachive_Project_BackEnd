package com.facaieve.backend.service.post.conditionsImp.portfolio.fashionPickup;

import com.facaieve.backend.entity.etc.CategoryEntity;
import com.facaieve.backend.entity.post.FashionPickupEntity;
import com.facaieve.backend.entity.post.PortfolioEntity;
import com.facaieve.backend.repository.post.PortfolioRepository;
import com.facaieve.backend.service.post.Condition;
import lombok.AllArgsConstructor;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;

import java.util.List;
@AllArgsConstructor
@RequiredArgsConstructor
public class FindPortfolioEntitiesByDueDate implements Condition<PortfolioEntity, CategoryEntity> {

    PortfolioRepository portfolioRepository;

    @Override
    public Page<PortfolioEntity> conditionSort(List<CategoryEntity> categoryEntities, int pageIndex,  int elementNum) {

        return portfolioRepository
                .findAllByCategoryEntities(
                        categoryEntities
                        , PageRequest.of(pageIndex - 1, elementNum, Sort.by("dueDate").descending()));
    }

}
