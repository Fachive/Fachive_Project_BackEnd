package com.facaieve.backend.mapper.etc;

import com.facaieve.backend.dto.etc.CategoryDTO;
import com.facaieve.backend.entity.etc.CategoryEntity;
import com.facaieve.backend.stubDate.CategoryStubData;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface CategoryMapper {

    CategoryEntity categoryStubDataToCategoryEntity(CategoryStubData categoryStubData);

    CategoryEntity postCategoryDtoToCategoryEntity(CategoryDTO.PostCategoryDto postCategoryDto);

    CategoryEntity patchCategoryDtoToCategoryEntity(CategoryDTO.PatchCategoryDto patchCategoryDto);

    CategoryEntity getCategoryDtoToCategoryEntity(CategoryDTO.GetCategoryDto getCategoryDto);

    CategoryEntity deleteCategoryDtoToCategoryEntity(CategoryDTO.DeleteCategoryDto deleteCategoryDto);
    CategoryEntity requestCategoryToCategoryEntity(CategoryDTO.RequestCategoryDTO requestCategoryDTO);

    CategoryDTO.ResponseCategoryDTO categoryEntityToResponseCategoryDto(CategoryEntity categoryEntity);
}
