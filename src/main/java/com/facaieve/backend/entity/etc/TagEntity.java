package com.facaieve.backend.entity.etc;

import com.facaieve.backend.entity.basetime.BaseEntity;
import com.facaieve.backend.entity.crossReference.FashionPickupEntityToTagEntity;
import com.facaieve.backend.entity.crossReference.FundingEntityToTagEntity;
import com.facaieve.backend.entity.crossReference.PortfolioEntityToTagEntity;
import com.facaieve.backend.entity.post.FashionPickupEntity;
import com.facaieve.backend.entity.post.FundingEntity;
import com.facaieve.backend.entity.post.PortfolioEntity;
import javax.persistence.*;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.*;
import org.hibernate.validator.constraints.UniqueElements;

import java.util.ArrayList;
import java.util.List;

@Entity
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class TagEntity extends BaseEntity {

    @Id

    String tagName;


    @OneToMany(mappedBy = "tagEntity", cascade = CascadeType.ALL)
    @Schema(description = "패션 픽업에 달린 태그 객체 목록")
    List<FashionPickupEntityToTagEntity> fashionPickupEntityToTagEntities = new ArrayList<FashionPickupEntityToTagEntity>();

    @OneToMany(mappedBy = "tagEntity", cascade = CascadeType.ALL)
    @Schema(description = "펀딩에 달린 태그 객체 목록")
    List<FundingEntityToTagEntity> fundingEntityToTagEntities = new ArrayList<FundingEntityToTagEntity>();

    @OneToMany(mappedBy = "tagEntity", cascade = CascadeType.ALL)
    @Schema(description = "포트폴리오에 달린 태그 객체 목록")
    List<PortfolioEntityToTagEntity> portfolioEntityToTagEntities = new ArrayList<PortfolioEntityToTagEntity>();



    @ManyToOne
    @JoinColumn(name = "funding_Entity_Id")
    FundingEntity fundingEntity;

    @ManyToOne
    @JoinColumn(name = "portfolio_Entity_Id")
    PortfolioEntity portfolioEntity;

    //update method 를 entity 내부에다가 구현함.
    public void update(String tagName){
        this.tagName = tagName;

    }

    public TagEntity(String tagName) {
        this.tagName = tagName;
    }
}
