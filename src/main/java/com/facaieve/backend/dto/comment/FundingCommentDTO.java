package com.facaieve.backend.dto.comment;

import com.facaieve.backend.entity.etc.MyPickEntity;
import com.facaieve.backend.entity.post.FundingEntity;
import com.facaieve.backend.entity.user.UserEntity;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

public class FundingCommentDTO {
    @Getter
    @Setter
    @AllArgsConstructor
    @NoArgsConstructor
    public static class PostCommentDTO{

        String commentBody;
        String postType;
        Long postId;
//        FundingEntity fundingEntity;
//        UserEntity userEntity;  // 유저 - 포트폴리오 댓글 매핑
//        MyPickEntity myPickEntity;


    }

    @Getter
    @Setter
    @AllArgsConstructor
    @NoArgsConstructor

    public static class GetCommentDTO{

        Long commentId;
        String commentBody;
        String postType;
        Long postId;

//        FundingEntity fundingEntity;
//        UserEntity userEntity;  // 유저 - 포트폴리오 댓글 매핑
//        MyPickEntity myPickEntity;

    }

    @Getter
    @Setter
    @AllArgsConstructor
    @NoArgsConstructor

    public static class DeleteCommentDTO{

        Long commentId;

        Long postId;

//        FundingEntity fundingEntity;
//        UserEntity userEntity;  // 유저 - 포트폴리오 댓글 매핑
//        MyPickEntity myPickEntity;

    }

    @Getter
    @Setter
    @AllArgsConstructor
    @NoArgsConstructor
    public static class ResponseCommentDTO{

        String commentBody;
        String postType;
        Long postId;
//
//        FundingEntity fundingEntity;
//        UserEntity userEntity;
//        MyPickEntity myPickEntity;

    }

    @Getter
    @Setter
    @AllArgsConstructor
    @NoArgsConstructor
    public static class PatchCommentDTO{

        Long commentId;
        String commentBody;
        String postType;
        Long postId;

//        FundingEntity fundingEntity;
//        UserEntity userEntity;  // 유저 - 포트폴리오 댓글 매핑
//        MyPickEntity myPickEntity;

    }



}
