package com.facaieve.backend.service.etc;

import com.facaieve.backend.entity.etc.CategoryEntity;
import com.facaieve.backend.repository.etc.CategoryRepository;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.List;

@Service
@Slf4j
@AllArgsConstructor
public class CategoryService {

    CategoryRepository categoryRepository;

    public CategoryEntity createCategoryEntity(CategoryEntity categoryEntity){
        if(categoryRepository.existsByCategoryName(categoryEntity.getCategoryName())){
            throw new RuntimeException("there is already exists category");

        }else{
            CategoryEntity savedCategoryEntity  = categoryRepository.save(categoryEntity);
            return savedCategoryEntity;
        }
    }
    @Transactional
    public CategoryEntity modifyCategoryEntity(CategoryEntity categoryEntity){
        if(categoryRepository.existsByCategoryId(categoryEntity.getCategoryId())){
            CategoryEntity categoryEntityChange =
                    categoryRepository.findCategoryEntityByCategoryId(categoryEntity.getCategoryId());
            categoryEntityChange.setCategoryName(categoryEntity.getCategoryName());
            //JPA 영속성 컨텍스트로 저장함.
            return categoryEntityChange;

        }else{
            throw new RuntimeException("there is no exists category");
        }

    }

    public CategoryEntity getCategory(CategoryEntity categoryEntity){
        if(categoryRepository.existsByCategoryName(categoryEntity.getCategoryName())){
            return categoryRepository.findCategoryEntityByCategoryName(categoryEntity.getCategoryName());
        }else{
            throw new RuntimeException("there is no kind of category name");
        }
    }

    public void deleteCategoryEntity(CategoryEntity categoryEntity){

        if(categoryRepository.existsByCategoryName(categoryEntity.getCategoryName())){
            CategoryEntity category = categoryRepository.findCategoryEntityByCategoryName(categoryEntity.getCategoryName());
             categoryRepository.deleteCategoryEntityByCategoryId(category.getCategoryId());
        }else{
            throw new RuntimeException("there is no kind of category");
        }
    }
}
