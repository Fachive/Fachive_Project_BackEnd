package com.facaieve.backend.entity.comment;


import com.facaieve.backend.Constant.PostType;
import com.facaieve.backend.entity.post.FundingEntity;
import com.facaieve.backend.entity.etc.MyPickEntity;
import com.facaieve.backend.entity.user.UserEntity;
import javax.persistence.*;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.ArrayList;
import java.util.List;
import javax.persistence.*;
@Entity
@NoArgsConstructor
@Data
@AllArgsConstructor
@Builder
public class FundingCommentEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    Long fundingCommentEntityId;
    @Column
    String commentBody;

    @Schema(description = "포스트 타입 선택")
    @Enumerated(value = EnumType.STRING)
    PostType postType = PostType.FUNDING;

    @Column
    Long postId;

    @Column
    @Schema(description = "마이픽(좋아요) 수")
    Integer myPicks = 0;

    @OneToMany(mappedBy = "fundingCommentEntity", cascade = CascadeType.ALL)
    private List<MyPickEntity> myPickEntity = new ArrayList<>();;

    @ManyToOne
    @JoinColumn(name = "fundingEntity_Id")
    private FundingEntity fundingEntity;

    @ManyToOne
    @JoinColumn(name = "userEntity_Id")
    private UserEntity userEntity;  // 유저 - 펀딩 댓글 매핑
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
