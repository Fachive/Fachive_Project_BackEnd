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

import java.util.Comparator;
import java.util.List;


@AllArgsConstructor

public class FindPortfolioPickupEntitiesByMyPicks implements Condition<PortfolioEntity, CategoryEntity> {

    PortfolioRepository portfolioRepository;
    @Override
    public Page<PortfolioEntity> conditionSort(CategoryEntity categoryEntity, int pageIndex, int elementNum) {
        PageRequest pageRequest =  PageRequest.of(pageIndex - 1, elementNum, Sort.by("myPicks").descending());
        Page<PortfolioEntity> portfolioEntities;

        if(categoryEntity.getCategoryName().equals("total")){
            portfolioEntities = portfolioRepository.findAll(pageRequest);
        }
        else {
            portfolioEntities = portfolioRepository.findPortfolioEntitiesByCategoryEntity( categoryEntity , pageRequest);
        }

        portfolioEntities.stream().sorted(new Comparator<PortfolioEntity>() {
            @Override
            public int compare(PortfolioEntity o1, PortfolioEntity o2) {
                return o1.getMyPick().size() - o2.getMyPick().size();
            }
        });
        return portfolioEntities;
    }
}
