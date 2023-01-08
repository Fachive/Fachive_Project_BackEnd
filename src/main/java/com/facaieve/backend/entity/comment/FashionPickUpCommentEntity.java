package com.facaieve.backend.entity.comment;

import com.facaieve.backend.entity.post.FashionPickupEntity;
import com.facaieve.backend.entity.etc.MyPickEntity;
import com.facaieve.backend.entity.user.UserEntity;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import java.util.ArrayList;
import java.util.List;
import javax.persistence.*;
@Entity
@NoArgsConstructor
@Getter
@Setter
public class FashionPickUpCommentEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    Long fashionPickupCommentEntityId;
    @Column
    String commentBody;
    @Column
    String postType;
    @Column
    Long postId;

    @OneToMany(mappedBy = "fashionPickupCommentEntity", cascade = CascadeType.ALL)
    private List<MyPickEntity> myPickEntity = new ArrayList<>();   // FP 댓글 - 마이픽 매핑

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

}
