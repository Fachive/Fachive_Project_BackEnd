package com.facaieve.backend.entity.post;


import com.facaieve.backend.entity.basetime.BaseEntity;
import com.facaieve.backend.entity.etc.CategoryEntity;
import com.facaieve.backend.entity.etc.MyPickEntity;
import com.facaieve.backend.entity.etc.TagEntity;
import com.facaieve.backend.entity.comment.FashionPickUpCommentEntity;
import com.facaieve.backend.entity.image.PostImageEntity;
import com.facaieve.backend.entity.user.UserEntity;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import javax.persistence.*;
import java.util.*;
@Entity
@NoArgsConstructor
@Getter
@Setter
public class FashionPickupEntity extends BaseEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Schema(description = "패션 픽업 객체 식별자")
    Long fashionPickupEntityId;

    @Column
    @Schema(description = "패션 픽업 제목")
    String title;

    @Column
    @Schema(description = "패션 픽업 본문 내용")
    String body;

//    @Column
//    @Schema(description = "패션 픽업 이미지 목록(S3 버킷 uri)")
//    List<String> imgUri;

    @Column
    @Schema(description = "조회수")
    int views;

    @OneToMany(mappedBy = "fashionPickupEntity",fetch = FetchType.LAZY)
    @Schema(description = "패션 픽업에 달린 마이픽(좋아요) 객체 목록")
    private List<MyPickEntity> myPick = new ArrayList<MyPickEntity>();  // 패션픽업 - 마이픽 매핑

    @OneToMany(mappedBy = "fashionPickupEntity",fetch = FetchType.LAZY)
    @Schema(description = "패션 픽업에 달린 댓글 객체 목록")
    private List<FashionPickUpCommentEntity> commentList = new ArrayList<FashionPickUpCommentEntity>();  // 패션픽업 - FP 댓글 매핑

    @OneToMany(mappedBy = "fashionPickupEntity", cascade = CascadeType.ALL)
    @Schema(description = "패션 픽업에 달린 태그 객체 목록")
    private List<TagEntity> tagEntities = new ArrayList<TagEntity>();  // 패션픽업 - 카테고리 매핑

    @OneToMany(mappedBy = "fashionPickupEntity",fetch = FetchType.LAZY)
    @Schema(description = "패션 픽업에 카테고리 객체 목록")
    private List<CategoryEntity> categoryEntities = new ArrayList<CategoryEntity>();  // 패션픽업 - 카테코리 매핑

    @ManyToOne
    @JoinColumn(name = "userEntity_Id")
    @Schema(description = "패션 픽업 작성자  객체 목록")
    private UserEntity userEntity;  // 유저 - 패션픽업  매핑

    @OneToMany(mappedBy = "fashionPickupEntity", fetch = FetchType.EAGER, cascade = CascadeType.ALL)
    private List<PostImageEntity> postImageEntities = new ArrayList<>();

}
