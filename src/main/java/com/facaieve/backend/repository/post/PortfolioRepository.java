package com.facaieve.backend.repository.post;

import com.facaieve.backend.entity.etc.CategoryEntity;
import com.facaieve.backend.entity.post.PortfolioEntity;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface PortfolioRepository extends JpaRepository<PortfolioEntity,Long> {
    Page<PortfolioEntity> findPortfolioEntitiesByCategoryEntitiesIn(List<CategoryEntity> categoryEntities, Pageable dueDate);

}
