package com.facaieve.backend.controller.comment;

import com.facaieve.backend.Constant.PostType;
import com.facaieve.backend.dto.comment.TotalCommentDTO;
import com.facaieve.backend.dto.post.FashionPickupDto;
import com.facaieve.backend.service.comment.FashionPickupCommentService;
import com.facaieve.backend.service.comment.TotalCommentService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("comment")
@Slf4j
@RequiredArgsConstructor
public class TotalCommentController {

    @Autowired
    TotalCommentService totalCommentService;

    @Operation(summary = "댓글을 등록하는 api postCommentDto 를 RequestBody 방식으로 받음",
            description = "postCommentDTO를 이용해서 댓들을 달 게시글의 아이디: postId , 댓들을 다는 유저의 아이디:userId, 게시글의 타입인 :postType" +
                    "을 이용해서 댓들을 달 수 있음")//대상 api의 대한 설명을 작성하는 어노테이션
    @ApiResponses({@ApiResponse(responseCode = "201", description = "댓글이 정상 등록됨", content = @Content(schema = @Schema(implementation = TotalCommentDTO.ResponseCommentDTO.class))),
            @ApiResponse(responseCode = "400", description = "BAD REQUEST !!"),
            @ApiResponse(responseCode = "404", description = "NOT FOUND !!"),
            @ApiResponse(responseCode = "500", description = "서버에서 에러가 발생하였습니다.")})
    @PostMapping("/post")//test pass
    public ResponseEntity postComment(@RequestBody TotalCommentDTO.PostCommentDTO postCommentDTO) {
        TotalCommentDTO.ResponseCommentDTO responseCommentDTO = totalCommentService.makeComment(postCommentDTO);
        return new ResponseEntity(responseCommentDTO, HttpStatus.OK);
    }

    @Operation(summary = "댓글을 읽어오는 api",
            description = "댓글의 식별자 commentId, postType: 어떤 게시글인지 선택 앞 두 파라미터를 이용해서 댓글 객체를 가져옴")
//대상 api의 대한 설명을 작성하는 어노테이션
    @ApiResponses({@ApiResponse(responseCode = "201", description = "댓글이 정상적올 읽어와짐", content = @Content(schema = @Schema(implementation = TotalCommentDTO.ResponseCommentDTO.class))),
            @ApiResponse(responseCode = "400", description = "BAD REQUEST !!"),
            @ApiResponse(responseCode = "404", description = "NOT FOUND !!"),
            @ApiResponse(responseCode = "500", description = "서버에서 에러가 발생하였습니다.")})
    @GetMapping("auth/get")//test pass
    public ResponseEntity getComment(@RequestParam
                                     @Parameter(name = "commentId", description = "댓글의 식별자")
                                             Long commentId,
                                     @Parameter(name = "postType", description = "게시글의 게시글 타입 지정자이다 FASHIONPICKUP: 패션픽업, FUNDING: 펀딩 게시글, PORTFOLIO:포트 폴리오 게시글")
                                             PostType postType) {
        TotalCommentDTO.GetCommentDTO getCommentDTO = TotalCommentDTO.GetCommentDTO.builder()
                .commentId(commentId).postType(postType).build();

        return new ResponseEntity(totalCommentService.readComment(getCommentDTO), HttpStatus.OK);
    }

    @Operation(summary = "작성된 댓글을 변경하는 api",
            description = "fetchCpmmentDTO 객체로 받음")
//대상 api의 대한 설명을 작성하는 어노테이션
    @ApiResponses({@ApiResponse(responseCode = "201", description = "댓글이 정상적으로 변경됨", content = @Content(schema = @Schema(implementation = TotalCommentDTO.ResponseCommentDTO.class))),
            @ApiResponse(responseCode = "400", description = "BAD REQUEST !!"),
            @ApiResponse(responseCode = "404", description = "NOT FOUND !!"),
            @ApiResponse(responseCode = "500", description = "서버에서 에러가 발생하였습니다.")})
    @PatchMapping("/patch")//test pass
    public ResponseEntity changeComment(@RequestBody TotalCommentDTO.FetchCommentDTO fetchCommentDTO) {
        TotalCommentDTO.ResponseCommentDTO responseCommentDTO
                = totalCommentService.changeComment(fetchCommentDTO);
        return new ResponseEntity(responseCommentDTO, HttpStatus.OK);
    }

    @Operation(summary = "작성된 댓글을 삭제하는 api",
            description = "댓글의 식별자 commentId, 게시글 식별자: postId , postType: 댓글이 작성된 게시글의 타입")
//대상 api의 대한 설명을 작성하는 어노테이션
    @ApiResponses({@ApiResponse(responseCode = "201", description = "댓글이 정상적으로 수정됨", content = @Content(schema = @Schema(implementation = TotalCommentDTO.ResponseCommentDTO.class))),
            @ApiResponse(responseCode = "400", description = "BAD REQUEST !!"),
            @ApiResponse(responseCode = "404", description = "NOT FOUND !!"),
            @ApiResponse(responseCode = "500", description = "서버에서 에러가 발생하였습니다.")})
    @DeleteMapping("/delete")//test pass
    public void deleteComment(@RequestParam
                              @Parameter(name = "postType", description = "댓글 식별자")
                                      Long commentId,
                              @Parameter(name = "postType", description = "게시글 식별자")
                                      Long postId,
                              @Parameter(name = "postType", description = "게시글의 게시글 타입 지정자이다 FASHIONPICKUP: 패션픽업, FUNDING: 펀딩 게시글, PORTFOLIO:포트 폴리오 게시글")
                                      PostType postType) {
        TotalCommentDTO.DeleteCommentDTO deleteCommentDTO = TotalCommentDTO.DeleteCommentDTO.builder()
                .commentId(commentId).postId(postId).postType(postType).build();
        totalCommentService.deleteCommentDTO(deleteCommentDTO);
        log.info("comment deleted");
    }
    @Operation(summary = "작성된 댓글에 좋아요를 누르는 api",
            description = "작성된 댓글에 좋아요를 누르는데 post type 과 누르는 사람의 id 가 필요함")
//대상 api의 대한 설명을 작성하는 어노테이션
    @ApiResponses({@ApiResponse(responseCode = "201", description = "좋아요가 정상적으로 눌러짐", content = @Content(schema = @Schema(implementation = TotalCommentDTO.ResponseCommentDTO.class))),
            @ApiResponse(responseCode = "400", description = "BAD REQUEST !!"),
            @ApiResponse(responseCode = "404", description = "NOT FOUND !!"),
            @ApiResponse(responseCode = "500", description = "서버에서 에러가 발생하였습니다.")})
    @GetMapping("/mypick")//마지막 파라미터는 좋아요를 누른 사람의 userId   test pass
    public ResponseEntity pushMyPickAtComment(@RequestParam Long commentId, PostType postType, Long pushingUserId) {
        TotalCommentDTO.PushingMyPickAtCommentDTO pushingMyPickAtCommentDTO
                = TotalCommentDTO.PushingMyPickAtCommentDTO.builder()
                .commentId(commentId)
                .postType(postType)
                .pushingUserId(pushingUserId)
                .build();

        return new ResponseEntity(totalCommentService.pushingMyPickToComment(pushingMyPickAtCommentDTO), HttpStatus.OK);
    }
}
