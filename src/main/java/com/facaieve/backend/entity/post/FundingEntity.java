package com.facaieve.backend.entity.post;

import com.facaieve.backend.entity.basetime.BaseEntity;
import com.facaieve.backend.entity.etc.CategoryEntity;
import com.facaieve.backend.entity.etc.MyPickEntity;
import com.facaieve.backend.entity.etc.TagEntity;
import com.facaieve.backend.entity.comment.FundingCommentEntity;
import com.facaieve.backend.entity.user.UserEntity;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import javax.persistence.*;

@Entity
@NoArgsConstructor
@Getter
@Setter
public class FundingEntity extends BaseEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    Long fundingEntityId;
    @Column
    String title;
    @Column
    String Body;
    @Column
    Date dueDate;
    @Column
    Long targetPrice;//펀딩 목표금액
    @Column
    Long fundedPrice;//펀딩된 현재 금액


    @OneToMany(mappedBy = "fundingEntity",fetch = FetchType.LAZY)
    private List<MyPickEntity> myPick = new ArrayList<MyPickEntity>();

    @OneToMany(mappedBy = "fundingEntity",fetch = FetchType.LAZY)
    private List<FundingCommentEntity> commentList = new ArrayList<FundingCommentEntity>();  // 펀딩 엔티티 - 펀딩 댓글 매핑

    @OneToMany(mappedBy = "fundingEntity", cascade = CascadeType.ALL)
    private List<TagEntity> tagEntities = new ArrayList<TagEntity>();  // 펀딩 - 카테고리 매핑

    @OneToMany(mappedBy = "fundingEntity",fetch = FetchType.LAZY)
    private List<CategoryEntity> categoryEntities = new ArrayList<CategoryEntity>();  // 펀딩 - 카테코리 매핑

    @ManyToOne
    @JoinColumn(name = "userEntity_Id")
    private UserEntity userEntity;  // 유저 - 펀딩  매핑
}
