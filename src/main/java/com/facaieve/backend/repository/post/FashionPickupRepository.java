package com.facaieve.backend.repository.post;

import com.facaieve.backend.entity.etc.CategoryEntity;
import com.facaieve.backend.entity.post.FashionPickupEntity;
import com.facaieve.backend.entity.user.UserEntity;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface FashionPickupRepository extends JpaRepository<FashionPickupEntity,Long>{
    Page<FashionPickupEntity> findFashionPickupEntitiesByCategoryEntity(CategoryEntity categoryEntity, Pageable views);

}
