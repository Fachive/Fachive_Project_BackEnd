package com.facaieve.backend.entity.comment;

import com.facaieve.backend.Constant.PostType;
import com.facaieve.backend.entity.basetime.BaseEntity;
import com.facaieve.backend.entity.etc.MyPickEntity;
import com.facaieve.backend.entity.post.PortfolioEntity;
import com.facaieve.backend.entity.user.UserEntity;
import javax.persistence.*;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;

import lombok.NoArgsConstructor;
import java.util.*;
import javax.persistence.*;
@Entity
@NoArgsConstructor
@Data
@AllArgsConstructor
@Builder
public class PortfolioCommentEntity extends BaseEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    Long portfolioCommentEntityId;
    @Column
    Long userId;
    @Column
    String commentBody;

    @Schema(description = "포스트 타입 선택")
    @Enumerated(value = EnumType.STRING)
    PostType postType = PostType.PORTFOLIO;

    @Column
    @Schema(description = "마이픽(좋아요) 수")
    Integer myPicks = 0;

    @Column
    Long postId;

    @OneToMany(mappedBy = "portfolioCommentEntity", cascade = CascadeType.ALL)
    private List<MyPickEntity> myPickEntity;

    @ManyToOne
    @JoinColumn(name = "portfolioEntity_Id")
    private PortfolioEntity portfolioEntity;

    @ManyToOne
    @JoinColumn(name = "userEntity_Id")
    private UserEntity userEntity;  // 유저 - 포트폴리오 댓글 매핑

    public void update(String commentBody){
        this.commentBody = commentBody;
    }

    public void plusMypickNum(){
        this.myPicks++;
    }

    public void minusMypickNum(){
        this.myPicks--;
    }
}
