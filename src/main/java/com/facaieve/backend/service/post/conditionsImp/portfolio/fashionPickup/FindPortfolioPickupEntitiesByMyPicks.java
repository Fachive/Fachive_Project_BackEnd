package com.facaieve.backend.service.post.conditionsImp.portfolio.fashionPickup;

import com.facaieve.backend.entity.etc.CategoryEntity;
import com.facaieve.backend.entity.post.FashionPickupEntity;
import com.facaieve.backend.entity.post.PortfolioEntity;
import com.facaieve.backend.repository.post.FashionPickupRepository;
import com.facaieve.backend.repository.post.PortfolioRepository;
import com.facaieve.backend.service.post.Condition;
import lombok.AllArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;

import java.util.Comparator;
import java.util.List;


@AllArgsConstructor
public class FindPortfolioPickupEntitiesByMyPicks implements Condition<PortfolioEntity, CategoryEntity> {

    PortfolioRepository portfolioRepository;
    @Override
    public Page<PortfolioEntity> conditionSort(List<CategoryEntity> categoryEntities, int pageIndex, int elementNum) {

        Page<PortfolioEntity> portfolioEntities = portfolioRepository
                .findAllByCategoryEntities(
                        categoryEntities
                        , PageRequest.of(pageIndex - 1, elementNum, Sort.by("views").descending()));

        portfolioEntities.stream().sorted(new Comparator<PortfolioEntity>() {
            @Override
            public int compare(PortfolioEntity o1, PortfolioEntity o2) {
                    return o1.getMyPick().size() - o2.getMyPick().size();
            }
        });

        return portfolioEntities;
    }
}
