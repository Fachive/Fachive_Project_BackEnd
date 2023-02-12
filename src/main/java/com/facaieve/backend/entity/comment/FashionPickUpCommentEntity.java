package com.facaieve.backend.entity.comment;

import com.facaieve.backend.Constant.PostType;
import com.facaieve.backend.entity.basetime.BaseEntity;
import com.facaieve.backend.entity.post.FashionPickupEntity;
import com.facaieve.backend.entity.etc.MyPickEntity;
import com.facaieve.backend.entity.user.UserEntity;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.*;

import java.util.ArrayList;
import java.util.List;
import javax.persistence.*;
@Entity
@NoArgsConstructor
@Getter
@Setter
@AllArgsConstructor
@Builder
public class FashionPickUpCommentEntity extends BaseEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    Long fashionPickupCommentEntityId;

    @Column
    String commentBody;

    @Schema(description = "포스트 타입 선택")
    @Enumerated(value = EnumType.STRING)
    PostType postType = PostType.FASHIONPICKUP;

    @Column
    Long postId;

    @Column
    @Schema(description = "마이픽(좋아요) 수")
    Integer myPicks = 0;

    @OneToMany(mappedBy = "fashionPickupCommentEntity", cascade = CascadeType.ALL)
    List<MyPickEntity> myPickEntity = new ArrayList<>();   // FP 댓글 - 마이픽 매핑

    @ManyToOne
    @JoinColumn(name = "fashionPickUpEntity_Id")
    private FashionPickupEntity fashionPickupEntity;  // FP 댓글 - 패션픽업 매핑

    // 유저 매핑
    @ManyToOne
    @JoinColumn(name = "userEntity_Id")
    private UserEntity userEntity;  // 유저 - 패션픽업 댓글 매핑

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
