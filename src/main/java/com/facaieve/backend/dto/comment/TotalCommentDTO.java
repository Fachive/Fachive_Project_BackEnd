package com.facaieve.backend.dto.comment;

import com.facaieve.backend.Constant.PostType;
import com.facaieve.backend.dto.image.ImageEntityDto;
import com.facaieve.backend.entity.etc.MyPickEntity;
import com.facaieve.backend.entity.post.FundingEntity;
import com.facaieve.backend.entity.post.PortfolioEntity;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;

import java.util.ArrayList;
import java.util.List;
//todo 1. 유저 프로파일 이미지 같이 반환하는 DTO 생성하기, 2. 1번 과정에서 필요한 로직 작성, 3. post 게시글을 get으로 확인할 때 댓글이 같이 넘어 가는지 확인하 4. mapper 먼저 수정하
public class TotalCommentDTO {
    @AllArgsConstructor
    @Data
    @Builder
    public static class ResponseCommentDTO{
        Long commentId;
        Long postId;
        PostType postType;
        Long userId;
        String commentBody;
        String commentProfileImageURI;
        Integer myPick;
    }

    @AllArgsConstructor
    @Data
    @Builder
    public static class PostCommentDTO{
        Long postId;
        PostType postType;
        String commentBody;
        Long userId;
    }

    @AllArgsConstructor
    @Data
    @Builder
    public static class GetCommentDTO{
        Long commentId;
        Long userId;
        String commentBody;
        PostType postType;
        List<MyPickEntity> myPickEntityList;
        ImageEntityDto.ResponseDto userImageDto;
    }

    @AllArgsConstructor
    @Data
    @Builder
    public static class FetchCommentDTO{//댓글을 수정할 때 사용하는 DTO 댓글의 내용만 변경 가능하게 만듦
        Long commentId;
        Long postId;
        Long userId;
        PostType postType;
        String commentBody;
    }

    @AllArgsConstructor
    @Data
    @Builder
    public static class DeleteCommentDTO{
        Long commentId;
        Long postId;
        PostType postType;
        String postBody;

    }
}
