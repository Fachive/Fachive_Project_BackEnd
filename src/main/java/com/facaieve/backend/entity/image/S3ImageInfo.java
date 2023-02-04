package com.facaieve.backend.entity.image;

import com.facaieve.backend.entity.comment.FashionPickUpCommentEntity;
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
    @GeneratedValue(strategy =  GenerationType.IDENTITY)
    Long S3ImageInfoId;
    @Column
    String fileName;
    @Column
    String fileURI;


    @ManyToOne(fetch = FetchType.EAGER )
    @JoinColumn(name = "fashionPickupEntity")
    private FashionPickupEntity fashionPickupEntityPost;

    @ManyToOne(fetch = FetchType.EAGER )
    @JoinColumn(name = "portfolioEntity")
    private PortfolioEntity portfolioEntityPost;

    @ManyToOne(fetch = FetchType.EAGER )
    @JoinColumn(name = "fundingEntity")
    private FundingEntity fundingEntityPost;
}
