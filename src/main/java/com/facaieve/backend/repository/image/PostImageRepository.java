package com.facaieve.backend.repository.image;

import com.facaieve.backend.entity.image.PostImageEntity;
import com.facaieve.backend.entity.post.FashionPickupEntity;
import com.facaieve.backend.entity.post.FundingEntity;
import com.facaieve.backend.entity.post.PortfolioEntity;
import com.facaieve.backend.stubDate.PortfolioStubData;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface PostImageRepository extends JpaRepository< PostImageEntity, Long> {

    List<PostImageEntity> findAllByFashionPickupEntity(FashionPickupEntity fashionPickupEntity);
    List<PostImageEntity> findAllByPortfolioEntity(PortfolioEntity portfolioEntity);
    List<PostImageEntity> findAllByFundingEntity(FundingEntity fundingEntity);
    PostImageEntity findByFileName(String fileName);
    void deleteByFileName(String fileName);
    void deleteByFileURI(String fileURI);

    boolean existsByFileName(String fileName);

}
