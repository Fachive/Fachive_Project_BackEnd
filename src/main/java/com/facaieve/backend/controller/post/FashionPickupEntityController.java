package com.facaieve.backend.controller.post;


import com.facaieve.backend.dto.image.PostImageDto;
import com.facaieve.backend.dto.multi.Multi_ResponseDTO;
import com.facaieve.backend.entity.etc.CategoryEntity;
import com.facaieve.backend.entity.image.PostImageEntity;
import com.facaieve.backend.mapper.post.FashionPickupMapper;

import com.facaieve.backend.dto.post.FashionPickupDto;
import com.facaieve.backend.entity.post.FashionPickupEntity;
import com.facaieve.backend.mapper.post.PostImageMapper;
import com.facaieve.backend.service.aswS3.S3FileService;
import com.facaieve.backend.service.etc.CategoryService;
import com.facaieve.backend.service.image.PostImageService;
import com.facaieve.backend.service.post.FashionPickupEntityService;
import com.facaieve.backend.service.post.conditionsImp.funding.FindFundingEntitiesByDueDate;
import com.facaieve.backend.service.post.conditionsImp.funding.FindFundingEntitiesByMyPicks;
import com.facaieve.backend.stubDate.FashionPuckupStubData;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@Slf4j
@RestController
@RequestMapping("/fashionpickup")
@AllArgsConstructor
public class FashionPickupEntityController {
    //todo 해당 컨트롤러에 너무 과도한 의존성이 들어감 수정 요함.
    FashionPickupEntityService fashionPickupEntityService;
    PostImageService postImageService;
    FashionPickupMapper fashionPickupMapper;
    S3FileService s3FileService;
    PostImageMapper postImageMapper;
    CategoryService categoryService;

    static final FashionPuckupStubData fashionPuckupStubData = new FashionPuckupStubData();

   /* @Operation(summary = "패션픽업 게시물(최신순) 호출 메서드", description = "패션픽업 페이지를 위한 패션픽업 객체 30개 반환 메서드(최신순)")//대상 api의 대한 설명을 작성하는 어노테이션
    @ApiResponses({
            @ApiResponse(responseCode = "200" ,description = "객체가 정상적으로 호출됨", content = @Content(schema = @Schema(implementation = FashionPickupDto.ResponseFashionPickupIncludeURI.class))),
            @ApiResponse(responseCode = "400", description = "BAD REQUEST !!"),
            @ApiResponse(responseCode = "404", description = "NOT FOUND !!"),
            @ApiResponse(responseCode = "500", description = "서버에서 에러가 발생하였습니다.")
    })
    @io.swagger.annotations.ApiResponses(
            @io.swagger.annotations.ApiResponse(
                    response = FashionPickupDto.ResponseFashionPickupIncludeURI.class, message = "ok", code=200)
    )

    @GetMapping("/get/updated/{page}")
    public ResponseEntity getFashionPickupEntitiesForMainPageByUpdatedBy(@PathVariable(required = false) int pageIndex){

        Page<FashionPickupEntity> foundFashionPickupEntities = fashionPickupEntityService.findFashionPickupEntitiesByUpdatedBy(pageIndex);
        List<FashionPickupDto.ResponseFashionPickupIncludeURI> responseFashionPickupDtoList = foundFashionPickupEntities
                .stream()
                .map(fashionPickupEntity -> fashionPickupMapper.fashionPickupEntityToResponseFashionPickupIncludeURI(fashionPickupEntity))
                .toList();

        return new ResponseEntity(responseFashionPickupDtoList, HttpStatus.OK);
    }
    */

    /* @Operation(summary = "패션픽업 게시물(추천순) 호출 메서드", description = "패션픽업 페이지를 위한 패션픽업 객체 30개 반환 메서드(추천순)")//대상 api의 대한 설명을 작성하는 어노테이션
    @ApiResponses({
            @ApiResponse(responseCode = "200" ,description = "객체가 정상적으로 호출됨", content = @Content(schema = @Schema(implementation = FashionPickupDto.ResponseFashionPickupIncludeURI.class))),
            @ApiResponse(responseCode = "400", description = "BAD REQUEST !!"),
            @ApiResponse(responseCode = "404", description = "NOT FOUND !!"),
            @ApiResponse(responseCode = "500", description = "서버에서 에러가 발생하였습니다.")
    })
    @io.swagger.annotations.ApiResponses(
            @io.swagger.annotations.ApiResponse(
                    response = FashionPickupDto.ResponseFashionPickupIncludeURI.class, message = "ok", code=200)
    )
    @GetMapping("/get/mypicks/{page}")
    public ResponseEntity getFashionPickupEntitiesForMainPageByPick(@PathVariable(required = false) int pageIndex){

        Page<FashionPickupEntity> foundFashionPickupEntities = fashionPickupEntityService.findFashionPickupEntitiesByPick(pageIndex);
        List<FashionPickupDto.ResponseFashionPickupIncludeURI> responseFashionPickupDtoList = foundFashionPickupEntities
                .stream()
                .map(fashionPickupEntity -> fashionPickupMapper.fashionPickupEntityToResponseFashionPickupIncludeURI(fashionPickupEntity))
                .toList();

        return new ResponseEntity(responseFashionPickupDtoList, HttpStatus.OK);
//        Multi_ResponseDTO<FashionPickupMainPageStubData> responseDTO = new Multi_ResponseDTO<>();
//        List<FashionPickupMainPageStubData> fashionPickupMainPageStubDataList = new ArrayList<>();
//        for(int i = 0; i<want; i++){
//            fashionPickupMainPageStubDataList.add(new FashionPickupMainPageStubData());
//        }
//        responseDTO.setData(fashionPickupMainPageStubDataList);
//        return new ResponseEntity(responseDTO,HttpStatus.OK);
    }

     */

    /*
    @Operation(summary = "패션픽업 게시물(마이픽) 호출 메서드", description = "패션픽업 페이지를 위한 패션픽업 객체 30개 반환 메서드(마이픽)")//대상 api의 대한 설명을 작성하는 어노테이션
    @ApiResponses({
            @ApiResponse(responseCode = "200" ,description = "객체가 정상적으로 호출됨", content = @Content(schema = @Schema(implementation = FashionPickupDto.ResponseFashionPickupIncludeURI.class))),
            @ApiResponse(responseCode = "400", description = "BAD REQUEST !!"),
            @ApiResponse(responseCode = "404", description = "NOT FOUND !!"),
            @ApiResponse(responseCode = "500", description = "서버에서 에러가 발생하였습니다.")
    })
    @io.swagger.annotations.ApiResponses(
            @io.swagger.annotations.ApiResponse(
                    response = FashionPickupDto.ResponseFashionPickupIncludeURI.class, message = "ok", code=200)
    )
    @GetMapping("/get/mypick/{page}")
    public ResponseEntity getFashionPickupEntitiesForMainPageByMyPick(@PathVariable(required = false) int pageIndex, @RequestHeader(value="userId") long userId){//헤더에 값 넣기


        List<FashionPickupEntity> foundFashionPickupEntities = fashionPickupEntityService.findFashionPickupEntitiesByMyPick(pageIndex, userId);
        List<FashionPickupDto.ResponseFashionPickupIncludeURI> responseFashionPickupDtoList = foundFashionPickupEntities
                .stream()
                .skip((pageIndex-1)*30L)
                .limit(30)
                .map(fashionPickupEntity -> fashionPickupMapper.fashionPickupEntityToResponseFashionPickupIncludeURI(fashionPickupEntity))
                .toList();

//        Pageable pageable = PageRequest.of(pageIndex - 1, 30);
//        List<FashionPickupEntity> page = new PagedListHolder<>(foundFashionPickupEntities).getPageList();

        return new ResponseEntity(responseFashionPickupDtoList, HttpStatus.OK);
//        Multi_ResponseDTO<FashionPickupMainPageStubData> responseDTO = new Multi_ResponseDTO<>();
//        List<FashionPickupMainPageStubData> fashionPickupMainPageStubDataList = new ArrayList<>();
//        for(int i = 0; i<want; i++){
//            fashionPickupMainPageStubDataList.add(new FashionPickupMainPageStubData());
//        }
//        responseDTO.setData(fashionPickupMainPageStubDataList);
//        return new ResponseEntity(responseDTO,HttpStatus.OK);
    }

     */

    //todo 카테고리 정렬순서 그리고 페이지를 파라미터로 가지는 api  구현할 것

    //todo parameter 로 category total, top, outer, one piece, skirt, accessory, suit, dress
    //todo sortway mypick, update, duedate
    @GetMapping("/mainfashionpickup")
    public ResponseEntity getFundingEntitySortingCategoryConditions(@RequestParam(required = false, defaultValue = "total") String categoryName,
                                                                    @RequestParam(required = false, defaultValue = "myPick") String sortWay,
                                                                    @RequestParam(required = false, defaultValue = "1") int pageIndex) {
        List<CategoryEntity> categoryEntities = new ArrayList<>();
        categoryEntities.add(categoryService
                .getCategory(CategoryEntity.builder()
                        .categoryName(categoryName)
                        .build()));

        switch (sortWay){
            case "myPick" : fashionPickupEntityService.setCondition(new FindFundingEntitiesByMyPicks());
            case "update" : fashionPickupEntityService.setCondition(new FindFundingEntitiesByDueDate());
            default : fashionPickupEntityService.setCondition(new FindFundingEntitiesByDueDate());
        }

        Page<FashionPickupEntity> fashionPickupEntityPage = fashionPickupEntityService.findFashionPickupEntitiesByCondition(categoryEntities, pageIndex,30);
        List<FashionPickupDto.ResponseFashionPickupIncludeURI> fashionPickupIncludeURIList = fashionPickupEntityPage.stream()
                .map(fashionPickupEntity -> fashionPickupMapper.fashionPickupEntityToResponseFashionPickupIncludeURI(fashionPickupEntity))
                .collect(Collectors.toList());

        Multi_ResponseDTO<FashionPickupDto.ResponseFashionPickupIncludeURI> multi_responseDTO =
                new Multi_ResponseDTO<FashionPickupDto.ResponseFashionPickupIncludeURI>(fashionPickupIncludeURIList, fashionPickupEntityPage);

        return new ResponseEntity(multi_responseDTO,HttpStatus.OK);

    }

    //todo 추후에 서비스 로직을 전부다 fashionPickupService 레이어 하위에 생성해서 controller 단에서의 의존성을 줄일 예정
    @PostMapping("/multipartPost")
    public ResponseEntity postFashionPickupEntityWithMultipart(
            @ModelAttribute FashionPickupDto.RequestFashionPickupIncludeMultiPartFileDto multiPartFileDto){

        List<MultipartFile> multipartFileList = multiPartFileDto.getMultiPartFileList();
        List<PostImageDto> postImageDtoList = s3FileService.uploadMultiFileList(multipartFileList);//저장될 파일 객체가 들어감.
        //S3에 저장후에 파일 이름과 URI 를 가지고 있음.


        FashionPickupDto.ResponseFashionPickupIncludeURI responseFashionPickupIncludeURI
                 = FashionPickupDto.ResponseFashionPickupIncludeURI.builder()
                .fashionPickupEntityId(multiPartFileDto.getFashionPickupEntityId())
                .title(multiPartFileDto.getTitle())
                .views(multiPartFileDto.getViews())
                .Body(multiPartFileDto.getBody())
                .multiPartFileList(postImageDtoList.stream().collect(Collectors.toList()))
                .build();//todo stream 사용할것

        FashionPickupEntity fashionPickupEntity =
                fashionPickupMapper.fashionPickupIncludeURIToFashionPickupEntity(responseFashionPickupIncludeURI);

        List<PostImageEntity> postImageEntities = fashionPickupEntity.getPostImageEntities();
        for(PostImageEntity postImage:postImageEntities){
            postImage.setFashionPickupEntity(fashionPickupEntity);
        }
        //연관관계의 주인인 이미지에 야무지게 삽입함

    //todo dto 를 반환해야 수나환참조 문제를 해결이 가능하다.
        return new ResponseEntity(fashionPickupMapper
                .fashionPickupEntityToResponseFashionPickupIncludeURI(
                        fashionPickupEntityService.createFashionPickupEntity
                                (fashionPickupEntity)),
                HttpStatus.OK);

    }

    // 사진을 가지고 있는 객체면 그냥 사진 uri랑 같이 반환됨.
    @GetMapping("/multiGet")
    public ResponseEntity getFashionPickupEntityMultipart(@RequestParam("postFashionEntityId") Long FashionEntityId){

        FashionPickupEntity fashionPickupEntity =
                fashionPickupEntityService.findFashionPickupEntity(FashionEntityId);

        //dto 로 변환해서 무한 참조 오류 피함
       return new ResponseEntity(fashionPickupMapper
               .fashionPickupEntityToResponseFashionPickupIncludeURI
                       (fashionPickupEntity)
               ,HttpStatus.OK);
    }



    @PatchMapping("/multiPatch")
    public ResponseEntity patchFashionPickupEntityMultipart(@RequestBody List<MultipartFile> multipartFiles,
                                                            Long fashionPickupEntityId){

        FashionPickupEntity fashionPickupEntity =
                fashionPickupEntityService.findFashionPickupEntity(fashionPickupEntityId);

        List<PostImageEntity> postImageEntities = fashionPickupEntity.getPostImageEntities();
//        List<String> fileNames = postImageEntities.stream().map(PostImageEntity::getFileName).collect(Collectors.toList());
        List<String> fileURIes = postImageEntities
                .stream()
                .map(PostImageEntity::getFileURI)
                .collect(Collectors.toList());

        s3FileService.deleteMultiFileList(fileURIes);//파일 URI 를 이용해서 파일을 삭제함


        List<PostImageDto> postImageDtoList = s3FileService.uploadMultiFileList(multipartFiles);
        fashionPickupEntity
                .setPostImageEntities(postImageDtoList
                        .stream()
                        .map(postImageMapper::postImageDtoToPostImageEntity)
                        .collect(Collectors.toList()));
        List<PostImageEntity> postImageEntityList = fashionPickupEntity.getPostImageEntities();

        for(PostImageEntity postImageEntity: postImageEntityList){
            postImageEntity.setFashionPickupEntity(fashionPickupEntity);
        }
        fashionPickupEntityService.editFashionPickupEntity(fashionPickupEntity);
        //파라미터로 들어온 파일을 받아서 bucket에 새롭게 저장함
        return new ResponseEntity(fashionPickupEntity,HttpStatus.OK);//수정된 entity 를 다시 반환함.


    }


    @Operation(summary = "패션픽업 게시글 등록 메서드 예제", description = "json 바디값을 통한 패션픽업 Post 메서드")//대상 api의 대한 설명을 작성하는 어노테이션
    @ApiResponses({
            @ApiResponse(responseCode = "201" ,description = "패션픽업 게시글이 정상 등록됨", content = @Content(schema = @Schema(implementation = FashionPickupDto.ResponseFashionPickupDto.class))),
            @ApiResponse(responseCode = "400", description = "BAD REQUEST !!"),
            @ApiResponse(responseCode = "404", description = "NOT FOUND !!"),
            @ApiResponse(responseCode = "500", description = "서버에서 에러가 발생하였습니다.")
    })
    @PostMapping("/post")
    public ResponseEntity postFashionPickupEntity(@RequestBody FashionPickupDto.PostFashionPickupDto postFashionPickupDto) {
//        FashionPickupEntity postingFashionPickupEntity = fashionPickupMapper.fashionPickupPostDtoToFashionPickupEntity(postFashionPickupDto);
//        FashionPickupEntity postedFashionPickupEntity = fashionPickupEntityService.createFashionPickupEntity(postingFashionPickupEntity);
//        return new ResponseEntity( fashionPickupMapper.fashionPickupEntityToResponseFashionPickupEntity(postedFashionPickupEntity), HttpStatus.OK);

        FashionPickupEntity stubdata =  fashionPickupMapper.fashionPickupDtoToFashionPickupStubData(fashionPuckupStubData);
        log.info("새로운 패션픽업 게시물을 등록합니다.");
        return new ResponseEntity( fashionPickupMapper.fashionPickupEntityToResponseFashionPickupEntity(stubdata), HttpStatus.OK);
    }



    @Operation(summary = "패션픽업 게시글 수정 메서드 예제", description = "json 바디값을 통한 패션픽업 Post 메서드")//대상 api의 대한 설명을 작성하는 어노테이션
    @ApiResponses({
            @ApiResponse(responseCode = "200" ,description = "패션픽업 게시글이 수정됨", content = @Content(schema = @Schema(implementation = FashionPickupDto.ResponseFashionPickupDto.class))),
            @ApiResponse(responseCode = "400", description = "BAD REQUEST !!"),
            @ApiResponse(responseCode = "404", description = "NOT FOUND !!"),
            @ApiResponse(responseCode = "500", description = "서버에서 에러가 발생하였습니다.")
    })
    @PatchMapping("/patch")
    public ResponseEntity patchFashionPickupEntity(@RequestBody FashionPickupDto.PatchFashionPickupDto patchFashionPickupDto){
//        FashionPickupEntity patchingFashionPickupEntity = fashionPickupMapper.fashionPickupPatchDtoToFashionPickupEntity(patchFashionPickupDto);
//        FashionPickupEntity patchedFashionPickupEntity = fashionPickupEntityService.editFashionPickupEntity(patchingFashionPickupEntity);
//        return new ResponseEntity( fashionPickupMapper.fashionPickupEntityToResponseFashionPickupEntity(patchedFashionPickupEntity), HttpStatus.OK);
//
        FashionPickupEntity stubdata =  fashionPickupMapper.fashionPickupDtoToFashionPickupStubData(fashionPuckupStubData);
        stubdata.setBody("패션픽업 게시글 내용 수정완료");
        stubdata.setTitle("패션픽업 게시글 제목 수정완료");

        log.info("기존 패션픽업 게시물을 수정합니다.");
        return new ResponseEntity( fashionPickupMapper.fashionPickupEntityToResponseFashionPickupEntity(stubdata), HttpStatus.OK);
    }

    @Operation(summary = "패션픽업 게시글 호출 예제", description = "json 바디값을 통한 패션픽업 GET 메서드")//대상 api의 대한 설명을 작성하는 어노테이션
    @ApiResponses({
            @ApiResponse(responseCode = "200" ,description = "패션픽업 게시글이 정상적으로 호출됨", content = @Content(schema = @Schema(implementation = FashionPickupDto.ResponseFashionPickupDto.class))),
            @ApiResponse(responseCode = "400", description = "BAD REQUEST !!"),
            @ApiResponse(responseCode = "404", description = "NOT FOUND !!"),
            @ApiResponse(responseCode = "500", description = "서버에서 에러가 발생하였습니다.")
    })
    @GetMapping("/get")
    public ResponseEntity getFashionPickupEntity(@RequestBody FashionPickupDto.GetFashionPickupDto getFashionPickupDto){
//        FashionPickupEntity foundFashionPickupEntity = fashionPickupEntityService.findFashionPickupEntity(getFashionPickupDto.getFashionPickupEntityId());
//        return new ResponseEntity( fashionPickupMapper.fashionPickupEntityToResponseFashionPickupEntity(foundFashionPickupEntity), HttpStatus.OK);
        log.info("기존 패션픽업 게시글을 가져옵니다.");

        FashionPickupEntity stubdata =  fashionPickupMapper.fashionPickupDtoToFashionPickupStubData(fashionPuckupStubData);
        return new ResponseEntity( fashionPickupMapper.fashionPickupEntityToResponseFashionPickupEntity(stubdata), HttpStatus.OK);
    }

    @Operation(summary = "패션픽업 게시글 삭제 예제", description = "json 바디값을 통한 패션픽업 DELETE 메서드")//대상 api의 대한 설명을 작성하는 어노테이션
    @ApiResponses({
            @ApiResponse(responseCode = "200" ,description = "패션픽업 게시글이 정상적으로 호출됨", content = @Content(schema = @Schema(implementation = FashionPickupDto.ResponseFashionPickupDto.class))),
            @ApiResponse(responseCode = "400", description = "BAD REQUEST !!"),
            @ApiResponse(responseCode = "404", description = "NOT FOUND !!"),
            @ApiResponse(responseCode = "500", description = "서버에서 에러가 발생하였습니다.")
    })
    @DeleteMapping("/delete")
    public ResponseEntity deleteFashionPickupEntity(@RequestBody FashionPickupDto.DeleteFashionPickupDto deleteFashionPickupDto){

//        fashionPickupEntityService.removeFashionPickupEntity(deleteFashionPickupDto.getFashionPickupEntityId());

        log.info("기존 패션픽업 게시글을 삭제합니다.");
        return new ResponseEntity(HttpStatus.OK);



    }



}
