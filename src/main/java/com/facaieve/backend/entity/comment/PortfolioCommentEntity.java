package com.facaieve.backend.entity.comment;

import com.facaieve.backend.entity.etc.MyPickEntity;
import com.facaieve.backend.entity.post.PortfolioEntity;
import com.facaieve.backend.entity.user.UserEntity;
import javax.persistence.*;
import lombok.Data;

import lombok.NoArgsConstructor;
import java.util.*;
import javax.persistence.*;
@Entity
@NoArgsConstructor
@Data
public class PortfolioCommentEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    Long portfolioCommentId;
    @Column
    Long userId;
    @Column
    String commentBody;
    @Column
    String postType;
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
}
