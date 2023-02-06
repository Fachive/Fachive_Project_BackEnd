package com.facaieve.backend.entity.image;


import com.facaieve.backend.entity.post.FashionPickupEntity;
import com.facaieve.backend.entity.post.FundingEntity;
import com.facaieve.backend.entity.post.PortfolioEntity;
import lombok.*;

import javax.persistence.*;

@AllArgsConstructor
@Builder
@NoArgsConstructor
@Getter
@Setter
@Entity
public class S3ImageInfo {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    Long S3ImageInfoId;
    @Column
    String fileName;
    @Column
    String fileURI;


    @ManyToOne(cascade = CascadeType.PERSIST, fetch = FetchType.LAZY)
    @JoinColumn(name = "fashionPickup_Entity_Id")
    FashionPickupEntity fashionPickupEntityPost;

    @ManyToOne
    @JoinColumn(name = "portfolio_Entity_Id")
    PortfolioEntity portfolioEntityPost;

    @ManyToOne
    @JoinColumn(name = "funding_Entity_Id")
    FundingEntity fundingEntityPost;

    public FashionPickupEntity addFashionPickupEntityPost(FashionPickupEntity fashionPickupEntityPost) {
        this.fashionPickupEntityPost = fashionPickupEntityPost;
        return fashionPickupEntityPost;
    }

    public PortfolioEntity addPortfolioEntityPost(PortfolioEntity fashionPickupEntityPost) {
        this.portfolioEntityPost = fashionPickupEntityPost;
        return portfolioEntityPost;
    }

    public FundingEntity addFundingEntityPost(FundingEntity fashionPickupEntityPost) {
        this.fundingEntityPost = fashionPickupEntityPost;
        return fundingEntityPost;
    }
}
