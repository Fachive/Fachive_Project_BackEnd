package com.facaieve.backend.repository.post;

import com.facaieve.backend.entity.etc.CategoryEntity;
import com.facaieve.backend.entity.post.FashionPickupEntity;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface FashionPickupRepository extends JpaRepository<FashionPickupEntity,Long> {
    Page<FashionPickupEntity> findAllByCategoryEntities(List<CategoryEntity> categoryEntities, PageRequest views);
}
