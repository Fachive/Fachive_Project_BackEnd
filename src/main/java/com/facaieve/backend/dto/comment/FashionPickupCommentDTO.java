package com.facaieve.backend.dto.comment;

import com.facaieve.backend.entity.basetime.BaseEntity;
import com.facaieve.backend.entity.etc.MyPickEntity;
import com.facaieve.backend.entity.post.FashionPickupEntity;
import com.facaieve.backend.entity.user.UserEntity;
import lombok.*;

public class FashionPickupCommentDTO  {

    @Getter
    @Setter
    @AllArgsConstructor
    @NoArgsConstructor
    @Builder

    public static class PostCommentDTO{

        String commentBody;
        String postType;
        Long postId;
        UserEntity userEntity;  // 유저 - 포트폴리오 댓글 매핑
        MyPickEntity myPickEntity;
        FashionPickupEntity fashionPickupEntity;
    }

    @Getter
    @Setter
    @AllArgsConstructor
    @NoArgsConstructor
    @Builder

    public static class GetCommentDTO{

        Long commentId;
        String commentBody;
        String postType;
        Long postId;
        UserEntity userEntity;  // 유저 - 포트폴리오 댓글 매핑
        MyPickEntity myPickEntity;
        FashionPickupEntity fashionPickupEntity;
    }

    @Getter
    @Setter
    @AllArgsConstructor
    @NoArgsConstructor
    @Builder

    public static class DeleteCommentDTO{

        Long commentId;
        Long postId;
        UserEntity userEntity;  // 유저 - 포트폴리오 댓글 매핑
        MyPickEntity myPickEntity;
        FashionPickupEntity fashionPickupEntity;
    }

    @Getter
    @Setter
    @AllArgsConstructor
    @NoArgsConstructor
    @Builder

    public static class ResponseCommentDTO{

        String commentBody;
        String postType;
        Long postId;
        UserEntity userEntity;
        MyPickEntity myPickEntity;
        FashionPickupEntity fashionPickupEntity;
    }

    @Getter
    @Setter
    @AllArgsConstructor
    @NoArgsConstructor
    @Builder

    public static class PatchCommentDTO{

        Long commentId;
        String commentBody;
        String postType;
        Long postId;
        UserEntity userEntity;  // 유저 - 포트폴리오 댓글 매핑
        MyPickEntity myPickEntity;
        FashionPickupEntity fashionPickupEntity;
    }


}
