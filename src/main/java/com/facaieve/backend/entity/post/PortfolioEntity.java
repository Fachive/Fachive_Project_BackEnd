package com.facaieve.backend.entity.post;

import com.facaieve.backend.entity.image.S3ImageInfo;
import com.facaieve.backend.entity.basetime.BaseEntity;
import com.facaieve.backend.entity.crossReference.PortfolioEntityToTagEntity;
import com.facaieve.backend.entity.etc.CategoryEntity;
import com.facaieve.backend.entity.etc.MyPickEntity;
import com.facaieve.backend.entity.comment.PortfolioCommentEntity;
import com.facaieve.backend.entity.image.PostImageEntity;
import com.facaieve.backend.entity.user.UserEntity;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.*;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

@Entity
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class PortfolioEntity extends BaseEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Schema(description = "포트폴리오 객체 식별자")
    Long portfolioEntityId;
    @Column
    @Schema(description = "포트폴리오 제목")
    String title;
    @Column
    @Schema(description = "포트폴리오 제목")
    String body;
    @Column
    @Schema(description = "포트폴리오 객체 조회수")
    Integer views;
    @Column
    @Schema(description = "마이픽(좋아요) 수")
    Integer myPicks = 0;

    @OneToMany(mappedBy = "portfolioEntityPost",fetch = FetchType.LAZY,  cascade = CascadeType.ALL)
    @Schema(description = "포트폴리오 이미지 목록(S3 버킷 uri)")
    List<S3ImageInfo> s3ImgInfo = new ArrayList<S3ImageInfo>();

    @OneToMany(mappedBy = "portfolioEntity",fetch = FetchType.LAZY)
    @Schema(description = "포트폴리오에 달린 마이픽(좋아요) 객체 목록")
    List<MyPickEntity> myPick = new ArrayList<MyPickEntity>();

    @OneToMany(mappedBy = "portfolioEntity",fetch = FetchType.LAZY)
    @Schema(description = "포트폴리오에 달린 댓글 객체 목록")
    List<PortfolioCommentEntity> commentList = new ArrayList<PortfolioCommentEntity>();


    @OneToMany(mappedBy = "portfolioEntity", cascade = CascadeType.ALL, fetch = FetchType.LAZY) //cascade = CascadeType.ALL
    @Schema(description = "포트폴리오에 달린 태그 객체 목록")
    List<PortfolioEntityToTagEntity> tagEntities = new ArrayList<PortfolioEntityToTagEntity>();  // 포트폴리오 - 카테고리 매핑

    @ManyToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "categoryEntity")
    CategoryEntity categoryEntity;


    @ManyToOne
    @JoinColumn(name = "userEntity_Id")
    @Schema(description = "포트폴리오 작성자  객체 목록")
    private UserEntity userEntity;  // 유저 - 포트폴리오  매핑

    @OneToMany(mappedBy = "portfolioEntity", fetch = FetchType.LAZY, cascade = CascadeType.ALL)
    private List<PostImageEntity> postImageEntities = new ArrayList<>();


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
