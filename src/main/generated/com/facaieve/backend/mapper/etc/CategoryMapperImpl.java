package com.facaieve.backend.mapper.etc;

import com.facaieve.backend.dto.etc.CategoryDTO;
import com.facaieve.backend.entity.etc.CategoryEntity;
import com.facaieve.backend.stubDate.CategoryStubData;
import javax.annotation.processing.Generated;
import org.springframework.stereotype.Component;

@Generated(
    value = "org.mapstruct.ap.MappingProcessor",
    date = "2023-02-01T22:45:23+0900",
    comments = "version: 1.5.3.Final, compiler: javac, environment: Java 17.0.5 (Oracle Corporation)"
)
@Component
public class CategoryMapperImpl implements CategoryMapper {

    @Override
    public CategoryEntity categoryStubDataToCategoryEntity(CategoryStubData categoryStubData) {
        if ( categoryStubData == null ) {
            return null;
        }

        CategoryEntity.CategoryEntityBuilder categoryEntity = CategoryEntity.builder();

        return categoryEntity.build();
    }

    @Override
    public CategoryEntity postCategoryDtoToCategoryEntity(CategoryDTO.PostCategoryDto postCategoryDto) {
        if ( postCategoryDto == null ) {
            return null;
        }

        CategoryEntity.CategoryEntityBuilder categoryEntity = CategoryEntity.builder();

        categoryEntity.categoryName( postCategoryDto.getCategoryName() );

        return categoryEntity.build();
    }

    @Override
    public CategoryEntity patchCategoryDtoToCategoryEntity(CategoryDTO.PatchCategoryDto patchCategoryDto) {
        if ( patchCategoryDto == null ) {
            return null;
        }

        CategoryEntity.CategoryEntityBuilder categoryEntity = CategoryEntity.builder();

        categoryEntity.categoryName( patchCategoryDto.getCategoryName() );

        return categoryEntity.build();
    }

    @Override
    public CategoryEntity getCategoryDtoToCategoryEntity(CategoryDTO.GetCategoryDto getCategoryDto) {
        if ( getCategoryDto == null ) {
            return null;
        }

        CategoryEntity.CategoryEntityBuilder categoryEntity = CategoryEntity.builder();

        categoryEntity.categoryName( getCategoryDto.getCategoryName() );

        return categoryEntity.build();
    }

    @Override
    public CategoryEntity deleteCategoryDtoToCategoryEntity(CategoryDTO.DeleteCategoryDto deleteCategoryDto) {
        if ( deleteCategoryDto == null ) {
            return null;
        }

        CategoryEntity.CategoryEntityBuilder categoryEntity = CategoryEntity.builder();

        categoryEntity.categoryName( deleteCategoryDto.getCategoryName() );

        return categoryEntity.build();
    }

    @Override
    public CategoryEntity requestCategoryToCategoryEntity(CategoryDTO.RequestCategoryDTO requestCategoryDTO) {
        if ( requestCategoryDTO == null ) {
            return null;
        }

        CategoryEntity.CategoryEntityBuilder categoryEntity = CategoryEntity.builder();

        categoryEntity.categoryId( requestCategoryDTO.getCategoryId() );
        categoryEntity.categoryName( requestCategoryDTO.getCategoryName() );

        return categoryEntity.build();
    }

    @Override
    public CategoryDTO.ResponseCategoryDTO categoryEntityToResponseCategoryDto(CategoryEntity categoryEntity) {
        if ( categoryEntity == null ) {
            return null;
        }

        CategoryDTO.ResponseCategoryDTO.ResponseCategoryDTOBuilder responseCategoryDTO = CategoryDTO.ResponseCategoryDTO.builder();

        responseCategoryDTO.categoryName( categoryEntity.getCategoryName() );

        return responseCategoryDTO.build();
    }

    @Override
    public CategoryDTO.ResponseCategoryDTO postCategoryDtoToResponseCategoryDto(CategoryDTO.PostCategoryDto postCategoryDto) {
        if ( postCategoryDto == null ) {
            return null;
        }

        CategoryDTO.ResponseCategoryDTO.ResponseCategoryDTOBuilder responseCategoryDTO = CategoryDTO.ResponseCategoryDTO.builder();

        responseCategoryDTO.categoryName( postCategoryDto.getCategoryName() );

        return responseCategoryDTO.build();
    }
}
