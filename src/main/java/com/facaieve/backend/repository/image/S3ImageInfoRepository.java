package com.facaieve.backend.repository.image;

import com.facaieve.backend.entity.image.S3ImageInfo;
import com.facaieve.backend.entity.post.FashionPickupEntity;
import com.facaieve.backend.entity.post.FundingEntity;
import com.facaieve.backend.entity.post.PortfolioEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.stream.Collectors;

@Repository
public interface S3ImageInfoRepository extends JpaRepository<S3ImageInfo, Long> {
    void deleteByFileName(String fileName);



//
//    static S3ImageInfo generateS3InfoForPortfolio(PortfolioEntity portfolioEntity, S3ImageInfo s3ImageInfos) {
//        return s3ImageInfo.builder()
//                .portfolioEntityPost(portfolioEntity)
//                .build();
//    }
//
//    static S3ImageInfo generateS3InfoForFunding(FundingEntity fundingEntity, S3ImageInfo s3ImageInfos) {
//        return S3ImageInfo.builder()
//                .fundingEntityPost(fundingEntity)
//                .build();
//    }
}
