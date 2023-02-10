package com.facaieve.backend.service.comment;

import com.facaieve.backend.Constant.PostType;
import com.facaieve.backend.dto.comment.TotalCommentDTO;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@RequiredArgsConstructor
@Service
@Slf4j
public class TotalCommentService {

    @Autowired
    FashionPickupCommentService fashionPickupCommentService;
    @Autowired
    FundingCommentService fundingCommentService;
    @Autowired
    PortfolioCommentService portfolioCommentService;

    CommentService commentService = fundingCommentService;



    private void setCommentService(PostType postType){

        switch(postType){

            case FASHIONPICKUP :
                commentService = fashionPickupCommentService;
                break;

            case FUNDING:
                commentService = fundingCommentService;
                        break;

            case PORTFOLIO:
                commentService = portfolioCommentService;
                break;

            default:
                commentService = fashionPickupCommentService;
        }
    }

    public TotalCommentDTO.ResponseCommentDTO makeComment(TotalCommentDTO.PostCommentDTO postCommentDTO){
        setCommentService(postCommentDTO.getPostType());
       return commentService.createComment(postCommentDTO);

    }

    public TotalCommentDTO.ResponseCommentDTO readComment(TotalCommentDTO.GetCommentDTO getCommentDTO){
        setCommentService(getCommentDTO.getPostType());
        return commentService.getComment(getCommentDTO.getCommentId());
    }

    public void deleteCommentDTO(TotalCommentDTO.DeleteCommentDTO deleteCommentDTO){
        setCommentService(deleteCommentDTO.getPostType());
        commentService.deleteComment(deleteCommentDTO.getCommentId());
    }

    public TotalCommentDTO.ResponseCommentDTO changeComment(TotalCommentDTO.FetchCommentDTO fetchCommentDTO){
        setCommentService(fetchCommentDTO.getPostType());
        return commentService.modifyComment(fetchCommentDTO);
    }

    public TotalCommentDTO.ResponseCommentDTO pushingMyPickToComment(TotalCommentDTO.PushingMyPickAtCommentDTO pushingMyPickAtCommentDTO){
        setCommentService(pushingMyPickAtCommentDTO.getPostType());
        return commentService.pushMyPick(pushingMyPickAtCommentDTO);
    }



}
