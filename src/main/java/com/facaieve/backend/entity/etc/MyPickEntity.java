package com.facaieve.backend.entity.etc;

import com.facaieve.backend.entity.basetime.BaseEntity;
import com.facaieve.backend.entity.comment.FundingCommentEntity;
import com.facaieve.backend.entity.comment.PortfolioCommentEntity;
import com.facaieve.backend.entity.post.FashionPickupEntity;
import com.facaieve.backend.entity.post.FundingEntity;
import com.facaieve.backend.entity.post.PortfolioEntity;
import com.facaieve.backend.entity.comment.FashionPickUpCommentEntity;
import com.facaieve.backend.entity.user.UserEntity;
import lombok.*;

import javax.persistence.*;
@Entity
@NoArgsConstructor
@Getter
@Setter
@Builder
@AllArgsConstructor
public class MyPickEntity extends BaseEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    Long myPickId;

    @ManyToOne(fetch =FetchType.EAGER )
    @JoinColumn(name = "myPickMadeByWho")
    UserEntity pickingUser;

    @ManyToOne(fetch =FetchType.EAGER )
    @JoinColumn(name = "fashionPickupCommentEntity")
    private FashionPickUpCommentEntity fashionPickupCommentEntity;
    @ManyToOne(fetch =FetchType.EAGER )
    @JoinColumn(name = "fundingCommentEntity")
    private FundingCommentEntity fundingCommentEntity;

    @ManyToOne(fetch =FetchType.EAGER )
    @JoinColumn(name = "portfolioCommentEntity")
    private PortfolioCommentEntity portfolioCommentEntity;


    @ManyToOne(fetch =FetchType.EAGER )
    @JoinColumn(name = "portfolioEntity")
    private PortfolioEntity portfolioEntity;

    @ManyToOne
    @JoinColumn(name = "fundingEntity")
    private FundingEntity fundingEntity;

    @ManyToOne
    @JoinColumn(name = "fashionPickUpEntity")
    private FashionPickupEntity fashionPickupEntity;

}
