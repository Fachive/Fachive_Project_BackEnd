package com.facaieve.backend.entity.post;


import com.facaieve.backend.entity.etc.TagEntity;
import com.facaieve.backend.entity.image.S3ImageInfo;
import com.facaieve.backend.entity.basetime.BaseEntity;
import com.facaieve.backend.entity.crossReference.FashionPickupEntityToTagEntity;
import com.facaieve.backend.entity.etc.CategoryEntity;
import com.facaieve.backend.entity.etc.MyPickEntity;
import com.facaieve.backend.entity.comment.FashionPickUpCommentEntity;
import com.facaieve.backend.entity.image.PostImageEntity;
import com.facaieve.backend.entity.user.UserEntity;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.*;

import javax.persistence.*;
import java.util.*;
@Entity
@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@Builder
public class FashionPickupEntity extends BaseEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Schema(description = "패션 픽업 객체 식별자")
    Long fashionPickupEntityId;

    @Column
    @Schema(description = "패션 픽업 제목")
    String title;

    @Column
    @Schema(description = "패션 픽업 본문 내용")
    String body;

    @Column
    @Schema(description = "조회수")
    Integer views = 0;

    @Column
    @Schema(description = "마이픽(좋아요) 수")
    Integer myPicks = 0;


    @OneToMany(mappedBy = "fashionPickupEntityPost",fetch = FetchType.LAZY, cascade = CascadeType.ALL)
    @Schema(description = "패션 픽업 이미지 목록(S3 버킷 uri)")
    private List<S3ImageInfo> s3ImgInfo = new ArrayList<S3ImageInfo>();

    @OneToMany(mappedBy = "fashionPickupEntity",fetch = FetchType.LAZY, cascade = CascadeType.ALL)
    @Schema(description = "패션 픽업에 달린 마이픽(좋아요) 객체 목록")
    private List<MyPickEntity> myPick = new ArrayList<MyPickEntity>();  // 패션픽업 - 마이픽 매핑

    @OneToMany(mappedBy = "fashionPickupEntity",fetch = FetchType.LAZY)
    @Schema(description = "패션 픽업에 달린 댓글 객체 목록")
    private List<FashionPickUpCommentEntity> commentList = new ArrayList<FashionPickUpCommentEntity>();  // 패션픽업 - FP 댓글 매핑

    @OneToMany(mappedBy = "fashionPickupEntity", cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    @Schema(description = "패션 픽업에 달린 태그 객체 목록")
    private List<FashionPickupEntityToTagEntity> tagEntities = new ArrayList<FashionPickupEntityToTagEntity>();  // 패션픽업 - 카테고리 매핑

    @ManyToOne(cascade = CascadeType.PERSIST)
    @JoinColumn(name = "categoryEntity")
    CategoryEntity categoryEntity;

    @ManyToOne
    @JoinColumn(name = "userEntity_Id")
    @Schema(description = "패션 픽업 작성자  객체 목록")
    private UserEntity userEntity;  // 유저 - 패션픽업  매핑

    @OneToMany(mappedBy = "fashionPickupEntity", fetch = FetchType.LAZY, cascade = CascadeType.ALL)
    private List<PostImageEntity> postImageEntities = new ArrayList<>();// 프로필사진 전용(이미지 로컬 저장용)

    public void plusMypickNum(){
        this.myPicks++;
    }
    public void minusMypickNum(){
        this.myPicks--;
    }
    public void plusViewNum(){
        this.views++;
    }
}
