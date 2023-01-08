package com.facaieve.backend.controller.etc;

import com.facaieve.backend.dto.etc.CategoryDTO;
import com.facaieve.backend.dto.post.FashionPickupDto;
import com.facaieve.backend.dto.post.FundingDto;
import com.facaieve.backend.entity.etc.CategoryEntity;
import com.facaieve.backend.mapper.etc.CategoryMapper;
import com.facaieve.backend.service.etc.CategoryService;
import com.facaieve.backend.stubDate.CategoryStubData;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
@Slf4j
@RestController
@RequestMapping("category")
@AllArgsConstructor
public class CategoryController {


    CategoryService categoryService;
    CategoryMapper categoryMapper;


    @Operation(summary = "카테고리 호출 메서드 예제", description = "json 바디값을 통한 카테고리  GET 요청 메서드")//대상 api의 대한 설명을 작성하는 어노테이션
    @ApiResponses({
            @ApiResponse(responseCode = "200" ,description = "카테고리가 정상 호출됨", content = @Content(schema = @Schema(implementation = FundingDto.ResponseFundingDto.class))),
            @ApiResponse(responseCode = "400", description = "BAD REQUEST !!"),
            @ApiResponse(responseCode = "404", description = "NOT FOUND !!"),
            @ApiResponse(responseCode = "500", description = "서버에서 에러가 발생하였습니다.")
    })
    @GetMapping("/get")//test pass
    public ResponseEntity readCategory(@RequestBody CategoryDTO.GetCategoryDto getCategoryDto){
//        CategoryEntity foundingCategoryEntity = categoryMapper.getCategoryDtoToCategoryEntity(getCategoryDto);
//        CategoryEntity foundCategoryEntity = categoryService.getCategory(foundingCategoryEntity);
//        return new ResponseEntity (categoryMapper.categoryEntityToResponseCategoryDto(foundCategoryEntity), HttpStatus.OK);
        CategoryStubData categoryStubData = new CategoryStubData();
        return new ResponseEntity(categoryMapper.categoryStubDataToCategoryEntity(categoryStubData), HttpStatus.OK);
    }

    @Operation(summary = "카테고리 작성 메서드 예제", description = "json 바디값을 통한 카테고리 POST 요청 메서드")//대상 api의 대한 설명을 작성하는 어노테이션
    @ApiResponses({
            @ApiResponse(responseCode = "201" ,description = "카테고리가 정상 등록됨", content = @Content(schema = @Schema(implementation = FundingDto.ResponseFundingDto.class))),
            @ApiResponse(responseCode = "400", description = "BAD REQUEST !!"),
            @ApiResponse(responseCode = "404", description = "NOT FOUND !!"),
            @ApiResponse(responseCode = "500", description = "서버에서 에러가 발생하였습니다.")
    })
    @PostMapping("/post")//test pass
    public ResponseEntity postCategory(@RequestBody CategoryDTO.PostCategoryDto postCategoryDto){
//        CategoryEntity postingCategoryEntity = categoryMapper.postCategoryDtoToCategoryEntity(postCategoryDto);
//        CategoryEntity postedCategoryEntity= categoryService.createCategoryEntity(postingCategoryEntity);
//
//        return new ResponseEntity(categoryMapper.categoryEntityToResponseCategoryDto(postedCategoryEntity), HttpStatus.CREATED);
//
        CategoryStubData categoryStubData = new CategoryStubData();
        return new ResponseEntity(categoryMapper.categoryStubDataToCategoryEntity(categoryStubData), HttpStatus.OK);
    }

    @Operation(summary = "카테고리 수정 메서드 예제", description = "json 바디값을 통한 카테고리 GET 메서드")//대상 api의 대한 설명을 작성하는 어노테이션
    @ApiResponses({
            @ApiResponse(responseCode = "200" ,description = "카테고리가 수정됨", content = @Content(schema = @Schema(implementation = FashionPickupDto.ResponseFashionPickupDto.class))),
            @ApiResponse(responseCode = "400", description = "BAD REQUEST !!"),
            @ApiResponse(responseCode = "404", description = "NOT FOUND !!"),
            @ApiResponse(responseCode = "500", description = "서버에서 에러가 발생하였습니다.")
    })
    @PatchMapping("/patch")//test pass
    public ResponseEntity patchCategory(@RequestBody CategoryDTO.PatchCategoryDto patchCategoryDto){
//        CategoryEntity patchingCategoryEntity = categoryMapper.patchCategoryDtoToCategoryEntity(patchCategoryDto);
//        CategoryEntity patchedCategoryEntity = categoryService.modifyCategoryEntity(patchingCategoryEntity);
//        return new ResponseEntity(categoryMapper.categoryEntityToResponseCategoryDto(patchedCategoryEntity), HttpStatus.OK);
//
        log.info("수정이 완료되었습니다");

        CategoryStubData categoryStubData = new CategoryStubData();
        return new ResponseEntity(categoryMapper.categoryStubDataToCategoryEntity(categoryStubData), HttpStatus.OK);
    }

    @Operation(summary = "카테고리 삭제 메서드 예제", description = "json 바디값을 통한 카테고리 DELETE 메서드")//대상 api의 대한 설명을 작성하는 어노테이션
    @ApiResponses({
            @ApiResponse(responseCode = "200" ,description = "카테고리가 삭제됨", content = @Content(schema = @Schema(implementation = FashionPickupDto.ResponseFashionPickupDto.class))),
            @ApiResponse(responseCode = "400", description = "BAD REQUEST !!"),
            @ApiResponse(responseCode = "404", description = "NOT FOUND !!"),
            @ApiResponse(responseCode = "500", description = "서버에서 에러가 발생하였습니다.")
    })
    @DeleteMapping("/delete")//test pass
    public ResponseEntity deleteCategory(@RequestBody CategoryDTO.DeleteCategoryDto deleteCategoryDto){
        CategoryEntity deletingCategoryEntity = categoryMapper.deleteCategoryDtoToCategoryEntity(deleteCategoryDto);
        categoryService.deleteCategoryEntity(deletingCategoryEntity);
        log.info("category 를 삭제합니다.");

        return new ResponseEntity(HttpStatus.OK);
    }

    //todo 추후에 필요하면 구현함.
//    public ResponseEntity modifyCategory(@RequestBody CategoryDTO.RequestCategoryDTO requestCategoryDTO) {
//
//    }


}
