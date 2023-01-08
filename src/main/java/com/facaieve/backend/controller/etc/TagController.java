package com.facaieve.backend.controller.etc;

import com.facaieve.backend.dto.etc.TagDTO;
import com.facaieve.backend.dto.post.FashionPickupDto;
import com.facaieve.backend.dto.post.FundingDto;
import com.facaieve.backend.entity.etc.TagEntity;
import com.facaieve.backend.mapper.etc.TagMapper;
import com.facaieve.backend.service.etc.TagService;
import com.facaieve.backend.stubDate.TagStubData;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import lombok.AllArgsConstructor;
import lombok.Setter;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/tag")
@AllArgsConstructor
@Slf4j
public class TagController {

    private TagService tagService;
    private TagMapper tagMapper;


    @Operation(summary = "태그 호출 메서드 예제", description = "json 바디값을 통한 태그  GET 요청 메서드")//대상 api의 대한 설명을 작성하는 어노테이션
    @ApiResponses({
            @ApiResponse(responseCode = "200" ,description = "태그가 정상 호출됨", content = @Content(schema = @Schema(implementation = FundingDto.ResponseFundingDto.class))),
            @ApiResponse(responseCode = "400", description = "BAD REQUEST !!"),
            @ApiResponse(responseCode = "404", description = "NOT FOUND !!"),
            @ApiResponse(responseCode = "500", description = "서버에서 에러가 발생하였습니다.")
    })
    @GetMapping("/get/{tagName}")//test pass
    public ResponseEntity getTagEntity(@PathVariable("tagName") String tagName){
//        tagName = "tagName";//todo delete
//        TagEntity tagEntity = tagService.getTagEntityByTagName(tagName);
//        TagDTO.GetTagDTO getTagDTO = tagMapper.tagEntityToGetTagEntity(tagEntity);
//        System.out.println(getTagDTO.getTagName());
//        return new ResponseEntity(getTagDTO, HttpStatus.OK);
        TagStubData stubData = new TagStubData();
        return new ResponseEntity(tagMapper.tagStubDataToStubTagEntity(stubData), HttpStatus.OK);
    }

    @Operation(summary = "태그 수정 메서드 예제", description = "json 바디값을 통한 태그 GET 메서드")//대상 api의 대한 설명을 작성하는 어노테이션
    @ApiResponses({
            @ApiResponse(responseCode = "200" ,description = "태그가 수정됨", content = @Content(schema = @Schema(implementation = FashionPickupDto.ResponseFashionPickupDto.class))),
            @ApiResponse(responseCode = "400", description = "BAD REQUEST !!"),
            @ApiResponse(responseCode = "404", description = "NOT FOUND !!"),
            @ApiResponse(responseCode = "500", description = "서버에서 에러가 발생하였습니다.")
    })
    @PatchMapping("/patch")//tes
    public ResponseEntity patchTag(@RequestBody TagDTO.PatchTagDTO patchTagDTO){
//        TagEntity  tagEntityChanged = tagService.modifyTagEntity(tagMapper.patchTagDtoToTagEntity(patchTagDTO));
//        return new ResponseEntity(tagMapper.tagEntityToResponseTagDTO(tagEntityChanged), HttpStatus.OK);
        TagStubData stubData = new TagStubData();
        stubData.setTagName("수정된 태그");
        return new ResponseEntity(tagMapper.tagStubDataToStubTagEntity(stubData), HttpStatus.OK);

//        tagService todo 수정 메소드 추가해야함...
    }

    @Operation(summary = "태그 작성 메서드 예제", description = "json 바디값을 통한 태그 POST 요청 메서드")//대상 api의 대한 설명을 작성하는 어노테이션
    @ApiResponses({
            @ApiResponse(responseCode = "201" ,description = "태그가 정상 등록됨", content = @Content(schema = @Schema(implementation = FundingDto.ResponseFundingDto.class))),
            @ApiResponse(responseCode = "400", description = "BAD REQUEST !!"),
            @ApiResponse(responseCode = "404", description = "NOT FOUND !!"),
            @ApiResponse(responseCode = "500", description = "서버에서 에러가 발생하였습니다.")
    })
    @PostMapping("/post")//test pass
    public ResponseEntity postTag(@RequestBody TagDTO.PostTagDTO postTagDTO){
//        TagEntity postingTagEntity = tagMapper.postTagDtoToTagEntity(postTagDTO);
//        TagDTO.ResponseTagDTO responseTagDTO = tagMapper.tagEntityToResponseTagDTO(tagService.createTagEntity(postingTagEntity));
//        return new ResponseEntity(responseTagDTO,HttpStatus.CREATED);
        TagStubData stubData = new TagStubData();
        return new ResponseEntity(tagMapper.tagStubDataToStubTagEntity(stubData), HttpStatus.OK);
    }

    @Operation(summary = "태그 삭제 메서드 예제", description = "json 바디값을 통한 태그 DELETE 메서드")//대상 api의 대한 설명을 작성하는 어노테이션
    @ApiResponses({
            @ApiResponse(responseCode = "200" ,description = "태그가 삭제됨", content = @Content(schema = @Schema(implementation = FashionPickupDto.ResponseFashionPickupDto.class))),
            @ApiResponse(responseCode = "400", description = "BAD REQUEST !!"),
            @ApiResponse(responseCode = "404", description = "NOT FOUND !!"),
            @ApiResponse(responseCode = "500", description = "서버에서 에러가 발생하였습니다.")
    })
    @DeleteMapping("/delete")
    public ResponseEntity deleteTag(@RequestBody TagDTO.deleteTagDTO deleteTagDTO){
//        tagService.deleteTagEntityService(tagService.getTagEntityByTagName(deleteTagDTO.getTagName()));
//        return new ResponseEntity(HttpStatus.OK);
        TagStubData stubData = new TagStubData();
        return new ResponseEntity(tagMapper.tagStubDataToStubTagEntity(stubData), HttpStatus.OK);
    }

}
