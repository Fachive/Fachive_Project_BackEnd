package com.facaieve.backend.service.comment;

import com.facaieve.backend.Constant.PostType;
import com.facaieve.backend.dto.comment.CommentDTO;
import com.facaieve.backend.dto.comment.TotalCommentDTO;
import com.facaieve.backend.entity.comment.PortfolioCommentEntity;
import com.facaieve.backend.entity.etc.MyPickEntity;
import com.facaieve.backend.entity.user.UserEntity;
import com.facaieve.backend.mapper.exception.BusinessLogicException;
import com.facaieve.backend.mapper.exception.ExceptionCode;

import java.util.List;
import java.util.stream.Collectors;

public interface CommentService<T> {

    TotalCommentDTO.ResponseCommentDTO createComment(TotalCommentDTO.PostCommentDTO postCommentDTO);
    void deleteComment(Long id);
    TotalCommentDTO.ResponseCommentDTO modifyComment(TotalCommentDTO.FetchCommentDTO fetchCommentDTO);
    TotalCommentDTO.ResponseCommentDTO getComment(Long commentId);
    TotalCommentDTO.ResponseCommentDTO pushMyPick(TotalCommentDTO.PushingMyPickAtCommentDTO pushingMyPickAtCommentDTO);
   void validatePickedUser(T t,TotalCommentDTO.PushingMyPickAtCommentDTO pushingMyPickAtCommentDTO);

}
