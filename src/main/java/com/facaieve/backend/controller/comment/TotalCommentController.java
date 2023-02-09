package com.facaieve.backend.controller.comment;

import com.facaieve.backend.Constant.PostType;
import com.facaieve.backend.dto.comment.TotalCommentDTO;
import com.facaieve.backend.service.comment.FashionPickupCommentService;
import com.facaieve.backend.service.comment.TotalCommentService;
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

    @PostMapping("/post")//test pass
    public ResponseEntity postComment(@RequestBody TotalCommentDTO.PostCommentDTO postCommentDTO){
       TotalCommentDTO.ResponseCommentDTO responseCommentDTO =  totalCommentService.makeComment(postCommentDTO);
       return new ResponseEntity(responseCommentDTO, HttpStatus.OK);
    }

    @GetMapping("/get")//test pass
    public ResponseEntity getComment(@RequestParam Long commentId, PostType postType){
        TotalCommentDTO.GetCommentDTO getCommentDTO = TotalCommentDTO.GetCommentDTO.builder()
                .commentId(commentId).postType(postType).build();

        return new ResponseEntity(totalCommentService.readComment(getCommentDTO), HttpStatus.OK);
    }

    @PatchMapping("/patch")//
    public ResponseEntity changeComment(@RequestBody TotalCommentDTO.FetchCommentDTO fetchCommentDTO){
         TotalCommentDTO.ResponseCommentDTO responseCommentDTO
                  = totalCommentService.changeComment(fetchCommentDTO);
         return new ResponseEntity(responseCommentDTO,HttpStatus.OK);
    }

    @DeleteMapping("/delete")
    public void deleteComment(@RequestParam Long commentId, Long postId, PostType postType){
        TotalCommentDTO.DeleteCommentDTO deleteCommentDTO = TotalCommentDTO.DeleteCommentDTO.builder()
                .commentId(commentId).postId(postId).postType(postType).build();
        totalCommentService.deleteCommentDTO(deleteCommentDTO);
        log.info("comment deleted");
    }
}
