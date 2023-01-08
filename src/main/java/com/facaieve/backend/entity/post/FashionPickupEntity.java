package com.facaieve.backend.entity.post;


import com.facaieve.backend.entity.basetime.BaseEntity;
import com.facaieve.backend.entity.etc.CategoryEntity;
import com.facaieve.backend.entity.etc.MyPickEntity;
import com.facaieve.backend.entity.etc.TagEntity;
import com.facaieve.backend.entity.comment.FashionPickUpCommentEntity;
import com.facaieve.backend.entity.user.UserEntity;

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
    Long fashionPickupEntityId;
    @Column
    String title;
    @Column
    String body;
    @Column
    int views;

    @OneToMany(mappedBy = "fashionPickupEntity",fetch = FetchType.LAZY)
    private List<MyPickEntity> myPick = new ArrayList<MyPickEntity>();  // 패션픽업 - 마이픽 매핑

    @OneToMany(mappedBy = "fashionPickupEntity",fetch = FetchType.LAZY)
    private List<FashionPickUpCommentEntity> commentList = new ArrayList<FashionPickUpCommentEntity>();  // 패션픽업 - FP 댓글 매핑

    @OneToMany(mappedBy = "fashionPickupEntity", cascade = CascadeType.ALL)
    private List<TagEntity> tagEntities = new ArrayList<TagEntity>();  // 패션픽업 - 카테고리 매핑

    @OneToMany(mappedBy = "fashionPickupEntity",fetch = FetchType.LAZY)
    private List<CategoryEntity> categoryEntities = new ArrayList<CategoryEntity>();  // 패션픽업 - 카테코리 매핑

    @ManyToOne
    @JoinColumn(name = "userEntity_Id")
    private UserEntity userEntity;  // 유저 - 패션픽업  매핑



}
