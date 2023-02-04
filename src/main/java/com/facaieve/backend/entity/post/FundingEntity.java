package com.facaieve.backend.entity.post;

import com.facaieve.backend.entity.image.S3ImageInfo;
import com.facaieve.backend.entity.basetime.BaseEntity;
import com.facaieve.backend.entity.crossReference.FundingEntityToTagEntity;
import com.facaieve.backend.entity.etc.CategoryEntity;
import com.facaieve.backend.entity.etc.MyPickEntity;
import com.facaieve.backend.entity.comment.FundingCommentEntity;
import com.facaieve.backend.entity.image.PostImageEntity;
import com.facaieve.backend.entity.user.UserEntity;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.*;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import javax.persistence.*;

@Entity
@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@Builder
public class FundingEntity extends BaseEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Schema(description = "펀딩 객체 식별자")
    Long fundingEntityId;

    @Column
    @Schema(description = "펀딩 제목")
    String title;

    @Column
    @Schema(description = "펀딩 본문 내용")
    String body;

    @Column
    @Schema(description = "펀딩 마감일")
    Date dueDate;

    @Column
    @Schema(description = "펀딩 목표액")
    Long targetPrice = 0L;//펀딩 목표금액

    @Column
    @Schema(description = "펀딩 모금액")
    Long fundedPrice = 0L;//펀딩된 현재 금액

    @Column
    @Schema(description = "펀딩 객체 조회수")
    Integer views = 0;

    @OneToMany(mappedBy = "fundingEntityPost",fetch = FetchType.LAZY)
    @Schema(description = "펀딩 이미지 목록(S3 버킷 uri)")
    List<S3ImageInfo> s3ImgInfo = new ArrayList<S3ImageInfo>();


    @OneToMany(mappedBy = "fundingEntity",fetch = FetchType.LAZY)
    @Schema(description = "펀딩에 달린 마이픽(좋아요) 객체 목록")
    private List<MyPickEntity> myPick = new ArrayList<MyPickEntity>();

    @OneToMany(mappedBy = "fundingEntity",fetch = FetchType.LAZY)
    @Schema(description = "펀딩에 달린 댓글 객체 목록")
    private List<FundingCommentEntity> commentList = new ArrayList<FundingCommentEntity>();  // 펀딩 엔티티 - 펀딩 댓글 매핑

    @OneToMany(mappedBy = "fundingEntity", cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    @Schema(description = "펀딩에 달린 태그 객체 목록")
    private List<FundingEntityToTagEntity> tagEntities = new ArrayList<FundingEntityToTagEntity>();  // 펀딩 - 카테고리 매핑

    @ManyToOne(cascade = CascadeType.PERSIST)
    @JoinColumn(name = "categoryEntity")
    CategoryEntity categoryEntity;

    @ManyToOne
    @JoinColumn(name = "userEntity_Id")
    @Schema(description = "펀딩 작성자  객체 목록")
    private UserEntity userEntity;  // 유저 - 펀딩  매핑

    @OneToMany (mappedBy = "fundingEntity", fetch = FetchType.LAZY, cascade = CascadeType.ALL)
    private List<PostImageEntity> postImageEntities = new ArrayList<>();
}
