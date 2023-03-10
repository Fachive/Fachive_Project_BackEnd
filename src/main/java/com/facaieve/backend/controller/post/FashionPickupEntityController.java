package com.facaieve.backend.controller.post;


import com.facaieve.backend.entity.crossReference.FashionPickupEntityToTagEntity;
import com.facaieve.backend.entity.image.S3ImageInfo;
import com.facaieve.backend.entity.etc.CategoryEntity;
import com.facaieve.backend.entity.etc.TagEntity;
import com.facaieve.backend.entity.user.UserEntity;
import com.facaieve.backend.mapper.comment.TotalCommentMapper;
import com.facaieve.backend.mapper.etc.TagMapper;
import com.facaieve.backend.mapper.post.FashionPickupMapper;

import com.facaieve.backend.dto.post.FashionPickupDto;
import com.facaieve.backend.entity.post.FashionPickupEntity;
import com.facaieve.backend.mapper.post.PostImageMapper;
import com.facaieve.backend.security.CustomUserDetailsService;
import com.facaieve.backend.service.aswS3.S3FileService;
import com.facaieve.backend.service.etc.CategoryService;
import com.facaieve.backend.service.etc.TagService;
import com.facaieve.backend.service.image.PostImageService;
import com.facaieve.backend.service.post.FashionPickupEntityService;
import com.facaieve.backend.service.user.UserService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@Slf4j
@RestController
@RequestMapping("/fashionpickup")
@AllArgsConstructor
public class FashionPickupEntityController {
    //todo ?????? ??????????????? ?????? ????????? ???????????? ????????? ?????? ??????.
    FashionPickupEntityService fashionPickupEntityService;
    PostImageService postImageService;
    FashionPickupMapper fashionPickupMapper;
    S3FileService s3FileService;
    PostImageMapper postImageMapper;
    CategoryService categoryService;
    TagService tagService;
    TagMapper tagMapper;
    UserService userService;
    TotalCommentMapper totalCommentMapper;
    CustomUserDetailsService customUserDetailsService;


    @Operation(summary = "N ??? ???????????? ?????????",
            description = "????????? ?????? ????????????????????? param??? ???????????? ???????????? ?????? ??????, ????????? ?????????, ???????????? ?????? ????????? ???????????? ????????? ????????? ??? ?????? api")//?????? api??? ?????? ????????? ???????????? ???????????????
    @ApiResponses({
            @ApiResponse(responseCode = "201" ,description = "???????????? ???????????? ?????? ?????????????????????."),
            @ApiResponse(responseCode = "400", description = "BAD REQUEST !!"),
            @ApiResponse(responseCode = "404", description = "NOT FOUND !!"),
            @ApiResponse(responseCode = "500", description = "???????????? ????????? ?????????????????????.")
    })
    @GetMapping("auth/mainfasionpickup")//test pass
    public ResponseEntity getFashionEntitySortingCategoryConditions(@Parameter(name="categoryName" ,description="????????????(??????, ??????, ?????????, ??????,?????????, ?????????, ????????????, ??????, ?????????) ???????????? ??????????????? ???????????? total ??? ?????? ???????????? ????????? ?????? ????????? post ?????????")
                                                                        @RequestParam(required = false, defaultValue = "??????") String categoryName,
                                                                    @Parameter(name="sortWay" ,description="?????? ??????: ?????????, ?????????, ????????? default: ?????????")
                                                                        @RequestParam(required = false, defaultValue = "myPick") String sortWay,
                                                                    @Parameter(name="?????????" ,description="????????? ????????? ????????? 1")
                                                                        @RequestParam(required = false, defaultValue = "1") Integer pageIndex,
                                                                    @Parameter(name="contentNumByPage" ,description="???????????? ????????? ??????")
                                                                        @RequestParam(required = false, defaultValue = "20") Integer contentNumByPage) {

        //????????? ???????????? ?????? ?????????
        CategoryEntity categoryEntity = categoryService.getCategoryFromService(categoryName);

        fashionPickupEntityService.setCondition(sortWay);//condition ?????? ????????? ?????? ?????????

        Page<FashionPickupEntity> fashionPickupEntityPage =
                fashionPickupEntityService.findFashionPickupEntitiesByCondition(categoryEntity, pageIndex,contentNumByPage);

        List<FashionPickupDto.ResponseFashionPickupDtoForEntity> list = fashionPickupEntityPage
                .stream().map(entity -> fashionPickupMapper.fashionPickupEntityToResponseFashionPickupDto(entity))
                .collect(Collectors.toList());


        return new ResponseEntity(list, HttpStatus.OK);
    }


    @Operation(summary = "form-data ??? ???????????? post ????????? ???????????? ????????? ????????? ???????????? api",
            description = "@modelAttribute??? ???????????? MultipartFile ??? json??? ????????? ????????? ???????????? ?????? ????????? ?????????")//?????? api??? ?????? ????????? ???????????? ???????????????
    @ApiResponses({@ApiResponse(responseCode = "201" ,description = "???????????? ???????????? ?????? ?????????", content = @Content(schema = @Schema(implementation = FashionPickupDto.ResponseFashionPickupDtoForEntity.class))),
                   @ApiResponse(responseCode = "400", description = "BAD REQUEST !!"),
                   @ApiResponse(responseCode = "404", description = "NOT FOUND !!"),
                   @ApiResponse(responseCode = "500", description = "???????????? ????????? ?????????????????????.")})
    @PostMapping(value = "/post")//POST API
    public ResponseEntity postFashionPickupEntity(@ModelAttribute FashionPickupDto.PostDto postDto){

        List<S3ImageInfo> s3ImageInfoList = s3FileService.uploadMultiFileList(postDto.getMultipartFileList());//????????? ?????? ????????? ?????????.\
        log.info("???????????? ????????? ?????? s3????????? ??????");

        List<TagEntity> tagEntityList = new ArrayList<>();
        postDto.getTagList().stream()//dto??? ?????? ?????? ??????????????? ???????????? ?????? ????????? ????????? ???????????? list??? ??????
                .map(tag -> tagEntityList.add(tagService.createTagEntity(tag.getTagName()))).collect(Collectors.toList());
        log.info("?????? ????????? ?????? ??????");

        CategoryEntity categoryEntity = categoryService.getCategoryFromService(postDto.getCategoryName());
        log.info("???????????? ????????? ?????? ??????");

        UserEntity postingUser = userService.findUserEntityById(postDto.getUserId());
        log.info("????????? ?????? ?????? ?????? ?????? ??????");



        FashionPickupEntity fashionPickupEntity = FashionPickupEntity.builder().title(postDto.getTitle())//????????? ???????????? ?????? ??????
                .body(postDto.getBody())
                .myPick(new ArrayList<>())
                .views(0)
                .myPicks(0)
                .categoryEntity(categoryEntity)
                .s3ImgInfo(s3ImageInfoList)
                .commentList(new ArrayList<>())
                .userEntity(postingUser)
                .build();

        List<FashionPickupEntityToTagEntity> tagEntities =tagEntityList.stream().map(tagEntity -> FashionPickupEntityToTagEntity.builder()
                .fashionPickupEntity(fashionPickupEntity)
                .tagEntity(tagEntity).build()).collect(Collectors.toList());

        fashionPickupEntity.setTagEntities(tagEntities);
        log.info("????????????-?????? ?????? ????????? ??????");

        fashionPickupEntity.getS3ImgInfo().forEach(s3ImageInfo ->s3ImageInfo.setFashionPickupEntityPost(fashionPickupEntity));
        log.info("S3ImageInfo ???????????? ??????");


        FashionPickupEntity createdFashionPickupEntity = fashionPickupEntityService.createFashionPickupEntity(fashionPickupEntity);
              log.info("????????? ?????? ??????");

    //  todo dto ??? ???????????? ??????????????? ????????? ????????? ????????????.
        return new ResponseEntity(fashionPickupMapper.fashionPickupEntityToResponseFashionPickupDto(createdFashionPickupEntity), HttpStatus.CREATED);
    }
    @Operation(summary = "???????????? ????????? ??????", description = "???????????? id??? ???????????? ????????? ???????????? ???????????? ????????? ???????????? api")//?????? api??? ?????? ????????? ???????????? ???????????????
    @ApiResponses({@ApiResponse(responseCode = "201" ,description = "???????????? ???????????? ?????? ?????????", content = @Content(schema = @Schema(implementation = FashionPickupDto.ResponseFashionPickupDtoForEntity.class))),
                   @ApiResponse(responseCode = "400", description = "BAD REQUEST !!"),
                   @ApiResponse(responseCode = "404", description = "NOT FOUND !!"),
                   @ApiResponse(responseCode = "500", description = "???????????? ????????? ?????????????????????.")})
    @GetMapping("auth/get/{fashionPickupId}")//GET API
    public ResponseEntity getFashionPickupEntityMultipart(@PathVariable("fashionPickupId") Long fashionPickupId ){

        FashionPickupEntity fashionPickupEntity = fashionPickupEntityService.findFashionPickupEntity(fashionPickupId);

       return new ResponseEntity(fashionPickupMapper.fashionPickupEntityToResponseFashionPickupDto(fashionPickupEntity), HttpStatus.OK);
    }


    @Operation(summary = "???????????? ????????? ??????", description = "?????? ????????? ???????????? ???????????? ?????????")//?????? api??? ?????? ????????? ???????????? ???????????????
    @ApiResponses({ @ApiResponse(responseCode = "201" ,description = "???????????? ???????????? ?????? ?????????", content = @Content(schema = @Schema(implementation = FashionPickupDto.ResponseFashionPickupDtoForEntity.class))),
                    @ApiResponse(responseCode = "400", description = "BAD REQUEST !!"),
                    @ApiResponse(responseCode = "404", description = "NOT FOUND !!"),
                    @ApiResponse(responseCode = "500", description = "???????????? ????????? ?????????????????????.")})
    @PatchMapping("/patch")//PATCH API
    public ResponseEntity patchFashionPickupEntityMultipart(@ModelAttribute FashionPickupDto.PatchRequestDto patchRequestDto){

        FashionPickupEntity editingFashionPickupEntity = fashionPickupEntityService.findFashionPickupEntity(patchRequestDto.getFashionPickupEntityId());
            log.info("????????? ?????? ???????????? {} ", editingFashionPickupEntity);

        List<String> entityUrlList = editingFashionPickupEntity.getS3ImgInfo()
                .stream().map(S3ImageInfo::getFileName).collect(Collectors.toList());
            log.info("????????? ????????? ?????? ????????? ?????????, s3?????? ???????????? ?????? ?????? {} ", entityUrlList);
        s3FileService.deleteMultiFileList(entityUrlList);
            log.info("?????? ????????? ????????? s3?????? ?????? ?????? {} ", entityUrlList);

        List<S3ImageInfo> s3ImageInfoList = s3FileService.uploadMultiFileList(patchRequestDto.getChangedMultipartFileList());
            log.info("????????? ????????? ????????? s3??? ???????????? {} ", s3ImageInfoList);

        List<TagEntity> tagEntityList = new ArrayList<>();
        patchRequestDto.getChangedTagList().stream()//dto??? ?????? ?????? ??????????????? ???????????? ?????? ????????? ????????? ???????????? list??? ??????
                .map(tag -> tagEntityList.add(tagService.createTagEntity(tag.getTagName()))).collect(Collectors.toList());
            log.info("?????? DTO?????? ????????? ?????? ????????? ?????? ??????");

        List<FashionPickupEntityToTagEntity> tagEntities =tagEntityList.stream().map(tagEntity -> FashionPickupEntityToTagEntity.builder()
                .fashionPickupEntity(editingFashionPickupEntity)
                .tagEntity(tagEntity).build()).collect(Collectors.toList());

        editingFashionPickupEntity.setTagEntities(tagEntities);
            log.info("????????????-?????? ?????? ????????? ??????");

        CategoryEntity categoryEntity = categoryService.getCategoryFromService(patchRequestDto.getChangedCategoryName());
            log.info("???????????? ????????? ?????? ??????");


        FashionPickupDto.PatchDto patchDto = FashionPickupDto.PatchDto.builder()
                .changedTitle(patchRequestDto.getChangedTitle())
                .changedBody(patchRequestDto.getChangedBody())
                .categoryEntity(categoryEntity)
                .tagEntities(tagEntities)
                .s3ImgInfo(s3ImageInfoList)
                .build();

        patchDto.getS3ImgInfo().forEach(s3ImageInfo ->s3ImageInfo.setFashionPickupEntityPost(editingFashionPickupEntity));


        FashionPickupEntity editedFashionPickupEntity = fashionPickupEntityService
                                                    .editFashionPickupEntity(editingFashionPickupEntity, patchDto);
             log.info("????????? ????????? ?????? {} ", editedFashionPickupEntity);

        return new ResponseEntity(fashionPickupMapper
                .fashionPickupEntityToResponseFashionPickupDto(editedFashionPickupEntity),HttpStatus.OK);//????????? entity ??? ?????? ?????????.
    }

    @Operation(summary = "???????????? ????????? ?????? ??????", description = "json ???????????? ?????? ???????????? DELETE ?????????")//?????? api??? ?????? ????????? ???????????? ???????????????
    @ApiResponses({
            @ApiResponse(responseCode = "200" ,description = "???????????? ???????????? ??????????????? ?????????"),
            @ApiResponse(responseCode = "400", description = "BAD REQUEST !!"),
            @ApiResponse(responseCode = "404", description = "NOT FOUND !!"),
            @ApiResponse(responseCode = "500", description = "???????????? ????????? ?????????????????????.")
    })
    @DeleteMapping("/delete")//DELETE API
    public ResponseEntity deleteFashionPickupEntity(@RequestBody FashionPickupDto.DeleteFashionPickupDto deleteFashionPickupDto){

        FashionPickupEntity deletingFashionPickupEntity = fashionPickupEntityService
                .findFashionPickupEntity(deleteFashionPickupDto.getFashionPickupEntityId());
        log.info("????????? ?????? ???????????? {} ", deletingFashionPickupEntity);

        List<String> entityUrlList = deletingFashionPickupEntity.getS3ImgInfo()
                .stream().map(S3ImageInfo::getFileName).collect(Collectors.toList());
        log.info("????????? ????????? ?????? ????????? ?????????, s3?????? ???????????? ?????? ?????? {} ", entityUrlList);
        s3FileService.deleteMultiFileList(entityUrlList);

        fashionPickupEntityService.removeFashionPickupEntity(deletingFashionPickupEntity);

        log.info("?????? ???????????? ???????????? ???????????????.");
        return new ResponseEntity(HttpStatus.OK);
    }



}
