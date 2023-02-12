package com.facaieve.backend.controller.post;


import com.facaieve.backend.dto.post.MainPostDto;
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
import com.facaieve.backend.service.post.PortfolioEntityService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.*;

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



    @Operation(summary = "메인 페이지를 위한 게시물 호출 API",
            description = "모든 카테고리의, 조회수 순으로(변경가능) 패션픽업, 펀딩 게시물 10개씩(현재 고정) 반환 api")//대상 api의 대한 설명을 작성하는 어노테이션
    @ApiResponses({
            @ApiResponse(responseCode = "201" ,description = "패션픽업, 펀딩 게시글들이 조회수 높은 순으로 10개씩 정상 호출되었습니다."),
            @ApiResponse(responseCode = "400", description = "BAD REQUEST !!"),
            @ApiResponse(responseCode = "404", description = "NOT FOUND !!"),
            @ApiResponse(responseCode = "500", description = "서버에서 에러가 발생하였습니다.")
    })
    @GetMapping("/get/ten")
    public ResponseEntity get10Each(){


        CategoryEntity categoryEntity = categoryService.getCategory(CategoryEntity.builder().categoryName("total").build());

        fashionPickupEntityService.setCondition("views");
        fundingEntityService.setCondition("views");
        portfolioEntityService.setCondition("views");

        Page<FashionPickupEntity> fashionPickupEntityPage = fashionPickupEntityService
                .findFashionPickupEntitiesByCondition(categoryEntity, 1,10);
        Page<FundingEntity> fundingEntityPage = fundingEntityService
                .findFundingEntitiesByCondition(categoryEntity, 1,10);
//        Page<PortfolioEntity> portfolioEntityPage = portfolioEntityService
//                .findPortfolioEntitiesByCondition(categoryEntity, 1,10);// 주완님 요청으로 삭제(2-12)

//        List<Object> postEntities = new ArrayList<>();//Object -> 새로운 dto 인터페이스로 추상화 필요 // 주완님 요청으로 DTO(ResponseMainDtoForEntity)로 각 항목 감싸 반환하도록 설정(2-12)


        MainPostDto.ResponseMainDtoForEntity responseMainDtoForEntity  = MainPostDto.ResponseMainDtoForEntity.builder()
                .fundingList(fundingEntityPage.stream()
                        .map(fundingEntity -> fundingMapper.fundingEntityToResponseFundingDto(fundingEntity)).toList())
                .fashionpickList(fashionPickupEntityPage.stream()
                        .map(fashionPickupEntity -> fashionPickupMapper.fashionPickupEntityToResponseFashionPickupDto(fashionPickupEntity)).toList())
                .build();// 프론트에서 포트폴리오는 뺴달라고 하여 제거


        return new ResponseEntity(responseMainDtoForEntity, HttpStatus.OK);

    }


}
