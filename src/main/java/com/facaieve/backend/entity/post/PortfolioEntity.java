package com.facaieve.backend.entity.post;

import com.facaieve.backend.entity.basetime.BaseEntity;
import com.facaieve.backend.entity.etc.MyPickEntity;
import com.facaieve.backend.entity.etc.TagEntity;
import com.facaieve.backend.entity.comment.PortfolioCommentEntity;
import com.facaieve.backend.entity.user.UserEntity;
import lombok.*;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

@Entity
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class PortfolioEntity extends BaseEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    Long portfolioEntityId;
    @Column
    String title;
    @Column
    String body;
    @Column
    int views;

    @OneToMany(mappedBy = "portfolioEntity",fetch = FetchType.LAZY)
    private List<MyPickEntity> myPick = new ArrayList<MyPickEntity>();

    @OneToMany(mappedBy = "portfolioEntity",fetch = FetchType.LAZY)
    private List<PortfolioCommentEntity> commentList = new ArrayList<PortfolioCommentEntity>();


    @OneToMany(mappedBy = "portfolioEntity", fetch = FetchType.LAZY) //cascade = CascadeType.ALL
    private List<TagEntity> tagEntities = new ArrayList<TagEntity>();  // 포트폴리오 - 카테고리 매핑

    @ManyToOne
    @JoinColumn(name = "userEntity_Id")
    private UserEntity userEntity;  // 유저 - 포트폴리오  매핑

}
