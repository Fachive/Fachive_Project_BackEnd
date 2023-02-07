package com.facaieve.backend.entity.etc;
import javax.persistence.*;
import com.facaieve.backend.entity.post.FashionPickupEntity;
import com.facaieve.backend.entity.post.FundingEntity;

import com.facaieve.backend.entity.post.PortfolioEntity;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.*;

import java.util.ArrayList;
import java.util.List;

@Entity
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class CategoryEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column
    Long categoryId;

    @Column
    String categoryName;

    @OneToMany(mappedBy = "categoryEntity",fetch = FetchType.LAZY, cascade = CascadeType.ALL)
    @Schema(description = "패션 픽업에 카테고리 객체 목록")
    List<FashionPickupEntity> fashionPickupEntityList = new ArrayList<>();


    @OneToMany(mappedBy = "categoryEntity",fetch = FetchType.LAZY, cascade = CascadeType.ALL)
    @Schema(description = "펀딩에 카테고리 객체 목록")
    List<FundingEntity> fundingEntities = new ArrayList<>();

    @OneToMany(mappedBy = "categoryEntity",fetch = FetchType.LAZY, cascade = CascadeType.ALL)
    @Schema(description = "포트폴리오에 카테고리 객체 목록")
    List<PortfolioEntity> portfolioEntities = new ArrayList<>();
}
