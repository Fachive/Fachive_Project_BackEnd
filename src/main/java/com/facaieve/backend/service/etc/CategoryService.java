package com.facaieve.backend.service.etc;

import com.facaieve.backend.entity.etc.CategoryEntity;
import com.facaieve.backend.mapper.exception.BusinessLogicException;
import com.facaieve.backend.mapper.exception.ExceptionCode;
import com.facaieve.backend.repository.etc.CategoryRepository;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
//todo 삭제 api 개선
//todo get 10 api 개선

@Service
@Slf4j
@AllArgsConstructor
public class CategoryService {

    CategoryRepository categoryRepository;

    public CategoryEntity createCategoryEntity(CategoryEntity categoryEntity){
        if(categoryRepository.findCategoryEntityByCategoryName(categoryEntity.getCategoryName())!=null){
            throw new RuntimeException("there is already exists category");

        }else{
            CategoryEntity savedCategoryEntity  = categoryRepository.save(categoryEntity);
            return savedCategoryEntity;
        }
    }
    @Transactional
    public CategoryEntity modifyCategoryEntity(CategoryEntity categoryEntity){
        if(categoryRepository.findCategoryEntityByCategoryName(categoryEntity.getCategoryName())!=null){
            CategoryEntity categoryEntityChange =
                    categoryRepository.findCategoryEntityByCategoryId(categoryEntity.getCategoryId());
            categoryEntityChange.setCategoryName(categoryEntity.getCategoryName());
            //JPA 영속성 컨텍스트로 저장함.
            return categoryEntityChange;

        }else{
            throw new RuntimeException("there is no exists category");
        }

    }

    public CategoryEntity getCategoryFromService(String categoryName){
        return getCategory(CategoryEntity
                .builder().categoryName(categoryName).build());
    }


    public CategoryEntity getCategory(CategoryEntity categoryEntity){
        if(categoryEntity.getCategoryName().equals("total")){
            log.info("전체 카테고리로 검색합니다. {}", categoryEntity);
            return CategoryEntity.builder().categoryName("total").build();
        }
        else if(categoryRepository.existsByCategoryName(categoryEntity.getCategoryName())){
            return categoryRepository.findCategoryEntityByCategoryName(categoryEntity.getCategoryName()).orElseThrow(()
                    -> new BusinessLogicException(ExceptionCode.NO_SUCH_CATEGORY));
        }else{
            throw new RuntimeException("there is no kind of category name");
        }
    }

    public void deleteCategoryEntity(CategoryEntity categoryEntity){

        if(categoryRepository.findCategoryEntityByCategoryName(categoryEntity.getCategoryName())!=null){//todo 찬일님에게 말씀 드릴것
            CategoryEntity category = categoryRepository.findCategoryEntityByCategoryName(categoryEntity.getCategoryName()).orElseThrow(()
                    -> new BusinessLogicException(ExceptionCode.NO_SUCH_CATEGORY));
             categoryRepository.deleteCategoryEntityByCategoryId(category.getCategoryId());
        }else{
            throw new RuntimeException("there is no kind of category");
        }
    }
}
