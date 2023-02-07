package com.facaieve.backend.service.post.conditionsImp.s3;

import com.facaieve.backend.entity.etc.CategoryEntity;
import com.facaieve.backend.entity.image.S3ImageInfo;
import com.facaieve.backend.entity.post.FashionPickupEntity;
import com.facaieve.backend.entity.post.PortfolioEntity;
import com.facaieve.backend.repository.image.S3ImageInfoRepository;
import com.facaieve.backend.service.post.Condition;
import com.facaieve.backend.service.post.ConditionForS3;
import lombok.AllArgsConstructor;

@AllArgsConstructor
public class SetMappingEntity implements ConditionForS3<FashionPickupEntity, S3ImageInfo> {

    S3ImageInfoRepository s3ImageInfoRepository;

    @Override
    public S3ImageInfo conditionSort(FashionPickupEntity fashionPickupEntity, S3ImageInfo s3ImageInfo) {
       return s3ImageInfoRepository.save(generateS3InfoForFashionPickup(fashionPickupEntity, s3ImageInfo));
    }

    static S3ImageInfo generateS3InfoForFashionPickup(FashionPickupEntity fashionPickupEntity, S3ImageInfo s3ImageInfo) {
        return   S3ImageInfo.builder()
                .fashionPickupEntityPost(fashionPickupEntity)
                .build();
    }
}
