package com.facaieve.backend.entity.image;

import com.facaieve.backend.entity.post.FashionPickupEntity;
import com.facaieve.backend.entity.post.FundingEntity;
import com.facaieve.backend.entity.post.PortfolioEntity;
import lombok.*;

import javax.persistence.*;
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Getter
@Builder
@Setter
public class PostImageEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    Long postImageId;

    @Column
    String fileName;

    @Column
    String fileURI;

    @ManyToOne(cascade = CascadeType.ALL, fetch = FetchType.EAGER)
    @JoinColumn(name = "fashionPickupEntity_Id")
    FashionPickupEntity fashionPickupEntity;

    @ManyToOne
    @JoinColumn(name = "fundingEntity_Id")
    FundingEntity fundingEntity;

    @ManyToOne
    @JoinColumn(name = "portfolioEntity_Id")
    PortfolioEntity portfolioEntity;

    //JPA context 로 업데이트 하기 위한 메소드 설정
    public void update(String fileName, String fileURI){
        this.fileName = fileName;
        this.fileURI = fileURI;
    }
}
