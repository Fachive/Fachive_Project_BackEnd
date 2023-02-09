package com.facaieve.backend.service.comment;

import com.facaieve.backend.dto.comment.CommentDTO;
import com.facaieve.backend.dto.comment.TotalCommentDTO;

public interface CommentService {

    TotalCommentDTO.ResponseCommentDTO createComment(TotalCommentDTO.PostCommentDTO postCommentDTO);
    void deleteComment(Long id);
    TotalCommentDTO.ResponseCommentDTO modifyComment(TotalCommentDTO.FetchCommentDTO fetchCommentDTO);
    TotalCommentDTO.ResponseCommentDTO getComment(Long commentId);

}
