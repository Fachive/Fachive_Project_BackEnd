package com.facaieve.backend.controller.post;


import com.facaieve.backend.dto.post.FashionPickupDto;
import com.facaieve.backend.entity.etc.CategoryEntity;
import com.facaieve.backend.entity.post.FashionPickupEntity;
import com.facaieve.backend.entity.post.FundingEntity;
import com.facaieve.backend.entity.post.PortfolioEntity;
import com.facaieve.backend.mapper.post.FashionPickupMapper;
import com.facaieve.backend.service.post.FashionPickupEntityService;
import com.facaieve.backend.service.post.FundingEntityService;
import com.facaieve.backend.service.post.conditionsImp.funding.FindFundingEntitiesByDueDate;
import com.facaieve.backend.service.post.fashionPickupEntityService;
import com.facaieve.backend.service.post.PortfolioEntityService;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@Slf4j
@RestController
@RequestMapping("/main")
@AllArgsConstructor
public class MainPostController {

    FashionPickupEntityService fashionPickupEntityService;
    FundingEntityService fundingEntityService;
    PortfolioEntityService portfolioEntityService;
    FashionPickupMapper fashionPickupMapper;

    @GetMapping("/get/ten")
    public ResponseEntity get10Each(){

        List<CategoryEntity> categoryEntities = new ArrayList<>();
        categoryEntities.add(CategoryEntity.builder()
                        .categoryName("total")
                        .build());

        fashionPickupEntityService.setCondition(new FindFundingEntitiesByDueDate());
        fundingEntityService.setCondition(new FindFundingEntitiesByDueDate());

        Page<FashionPickupEntity> fashionPickupEntities = fashionPickupEntityService.findFundingEntitiesByCondition(categoryEntities, 1);
        Page<FundingEntity> fundingEntityPage = fundingEntityService.findFundingEntitiesByCondition(categoryEntities, 1);








        return new ResponseEntity(HttpStatus.OK);
    }


}
