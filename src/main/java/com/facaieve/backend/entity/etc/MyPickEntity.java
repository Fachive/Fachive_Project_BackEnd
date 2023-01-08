package com.facaieve.backend.entity.etc;

import com.facaieve.backend.entity.basetime.BaseEntity;
import com.facaieve.backend.entity.comment.FundingCommentEntity;
import com.facaieve.backend.entity.comment.PortfolioCommentEntity;
import com.facaieve.backend.entity.post.FashionPickupEntity;
import com.facaieve.backend.entity.post.FundingEntity;
import com.facaieve.backend.entity.post.PortfolioEntity;
import com.facaieve.backend.entity.comment.FashionPickUpCommentEntity;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import javax.persistence.*;
@Entity
@NoArgsConstructor
@Getter
@Setter
public class MyPickEntity extends BaseEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    Long myPickId;

    @Column
    Long userId;

    @ManyToOne
    @JoinColumn(name = "fashionPickupCommentEntity")
    private FashionPickUpCommentEntity fashionPickupCommentEntity;
    @ManyToOne
    @JoinColumn(name = "fundingCommentEntity")
    private FundingCommentEntity fundingCommentEntity;

    @ManyToOne
    @JoinColumn(name = "portfolioCommentEntity")
    private PortfolioCommentEntity portfolioCommentEntity;


    @ManyToOne
    @JoinColumn(name = "portfolioEntity")
    private PortfolioEntity portfolioEntity;

    @ManyToOne
    @JoinColumn(name = "fundingEntity")
    private FundingEntity fundingEntity;

    @ManyToOne
    @JoinColumn(name = "fashionPickUpEntity")
    private FashionPickupEntity fashionPickupEntity;

}
