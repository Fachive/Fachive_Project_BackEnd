package com.facaieve.backend.repository.etc;

import com.facaieve.backend.entity.etc.CategoryEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import javax.transaction.Transactional;

@Repository
public interface CategoryRepository extends JpaRepository<CategoryEntity,Long> {

    boolean existsByCategoryName(String categoryName);
    CategoryEntity findCategoryEntityByCategoryName(String categoryName);
    boolean existsByCategoryId(Long categoryId);
    @Transactional
    CategoryEntity deleteCategoryEntityByCategoryName(String categoryName);
    @Transactional

    void deleteCategoryEntityByCategoryId(Long categoryId);

    CategoryEntity findCategoryEntityByCategoryId(Long Id);
}
