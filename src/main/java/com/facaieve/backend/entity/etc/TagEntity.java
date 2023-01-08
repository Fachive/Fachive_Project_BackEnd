package com.facaieve.backend.entity.etc;

import com.facaieve.backend.entity.basetime.BaseEntity;
import com.facaieve.backend.entity.post.FashionPickupEntity;
import com.facaieve.backend.entity.post.FundingEntity;
import com.facaieve.backend.entity.post.PortfolioEntity;
import javax.persistence.*;import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Getter
@Setter
@NoArgsConstructor
public class TagEntity extends BaseEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    Long tagId;
    @Column
    String tagName;
    @Column
    String description;

    @ManyToOne
    @JoinColumn(name = "fashion_Pickup_Entity_Id")
    FashionPickupEntity fashionPickupEntity;

    @ManyToOne
    @JoinColumn(name = "funding_Entity_Id")
    FundingEntity fundingEntity;

    @ManyToOne
    @JoinColumn(name = "portfolio_Entity_Id")
    PortfolioEntity portfolioEntity;

    //update method 를 entity 내부에다가 구현함.
    public void update(String tagName, String description){
        this.tagName = tagName;
        this.description = description;
    }

}
