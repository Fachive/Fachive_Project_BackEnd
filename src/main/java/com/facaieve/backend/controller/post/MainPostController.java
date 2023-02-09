package com.facaieve.backend.controller.post;


import com.facaieve.backend.entity.etc.CategoryEntity;
import com.facaieve.backend.entity.post.FashionPickupEntity;
import com.facaieve.backend.entity.post.FundingEntity;
import com.facaieve.backend.entity.post.PortfolioEntity;
import com.facaieve.backend.mapper.post.FashionPickupMapper;
import com.facaieve.backend.mapper.post.FundingMapper;
import com.facaieve.backend.mapper.post.PortfolioMapper;
import com.facaieve.backend.mapper.post.PostMapper;
import com.facaieve.backend.service.etc.CategoryService;
import com.facaieve.backend.service.post.FashionPickupEntityService;
import com.facaieve.backend.service.post.FundingEntityService;
import com.facaieve.backend.service.post.conditionsImp.fashionPickup.FindFashionPickupEntitiesByDueDate;
import com.facaieve.backend.service.post.conditionsImp.funding.FindFundingEntitiesByDueDate;
import com.facaieve.backend.service.post.conditionsImp.portfolio.FindPortfolioEntitiesByDueDate;
import com.facaieve.backend.service.post.PortfolioEntityService;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.ArrayList;
import java.util.List;

@Slf4j
@RestController
@RequestMapping("/main")
@AllArgsConstructor
public class MainPostController {

    FashionPickupEntityService fashionPickupEntityService;
    FundingEntityService fundingEntityService;
    PortfolioEntityService portfolioEntityService;
    FashionPickupMapper fashionPickupMapper;
    FundingMapper fundingMapper;
    PortfolioMapper portfolioMapper;
    CategoryService categoryService;

    PostMapper postMapper;

    @GetMapping("/get/ten")
    public ResponseEntity get10Each(){

        CategoryEntity categoryEntity = categoryService.getCategory(CategoryEntity.builder().categoryName("suit").build());

        fashionPickupEntityService.setCondition("views");
        fundingEntityService.setCondition("views");
        portfolioEntityService.setCondition("views");

        Page<FashionPickupEntity> fashionPickupEntityPage = fashionPickupEntityService
                .findFashionPickupEntitiesByCondition(categoryEntity, 1,10);
        Page<FundingEntity> fundingEntityPage = fundingEntityService
                .findFundingEntitiesByCondition(categoryEntity, 1,10);
        Page<PortfolioEntity> portfolioEntityPage = portfolioEntityService
                .findPortfolioEntitiesByCondition(categoryEntity, 1,10);

        List<Object> postEntities = new ArrayList<>();//Object -> 새로운 dto 인터페이스로 추상화 필요

        postEntities.addAll(fundingEntityPage.stream()
                 .map(fundingEntity -> fundingMapper
                         .fundingEntityToResponseFundingDto(fundingEntity)).toList());

        postEntities.addAll(fashionPickupEntityPage.stream()
                .map(fashionPickupEntity -> fashionPickupMapper
                        .fashionPickupEntityToResponseFashionPickupDto(fashionPickupEntity)).toList());

        postEntities.addAll(portfolioEntityPage.stream()
                .map(portfolioEntity -> portfolioMapper
                        .portfolioEntityToResponsePortfolioEntity(portfolioEntity)).toList());

        return new ResponseEntity(postEntities,HttpStatus.OK);
    }


}
