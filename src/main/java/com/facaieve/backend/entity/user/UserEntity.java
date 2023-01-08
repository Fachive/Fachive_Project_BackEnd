package com.facaieve.backend.entity.user;


import com.facaieve.backend.entity.basetime.BaseEntity;
import com.facaieve.backend.Constant.UserActive;
import com.facaieve.backend.entity.comment.FashionPickUpCommentEntity;
import com.facaieve.backend.entity.comment.FundingCommentEntity;
import com.facaieve.backend.entity.comment.PortfolioCommentEntity;
import com.facaieve.backend.entity.post.FashionPickupEntity;
import com.facaieve.backend.entity.post.FundingEntity;
import com.facaieve.backend.entity.post.PortfolioEntity;
import javax.persistence.*;import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import java.util.*;
import javax.persistence.*;

@Entity
@Getter
@Setter
@NoArgsConstructor
public class UserEntity extends BaseEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)//개별 엔티티 적용
    Long userEntityId;
    @Column
    String displayName;
    @Column
    String email;
    @Column
    String password;
    @Column
    String state;
    @Column
    String city;
    @Column
    String userInfo;
    @Column
    String career;
    @Column
    String education;
    @Column
    String Company;

    @Enumerated(value = EnumType.STRING)
    UserActive userActive;

    //패션픽업 댓글, 펀딩 댓글, 포폴 댓글 엔티티 매핑
    @OneToMany(mappedBy = "userEntity", cascade = CascadeType.PERSIST)
    List<FashionPickUpCommentEntity>  fashionPickUpCommentEntities = new ArrayList<FashionPickUpCommentEntity>();

    @OneToMany(mappedBy = "userEntity", cascade = CascadeType.PERSIST)
    List<FundingCommentEntity>  fundingCommentEntities = new ArrayList<FundingCommentEntity>();

    @OneToMany(mappedBy = "userEntity", cascade = CascadeType.PERSIST)
    List<PortfolioCommentEntity>  portfolioCommentEntities = new ArrayList<PortfolioCommentEntity>();

    //패션픽업, 펀딩, 포폴 엔티티 매핑
    @OneToMany(mappedBy = "userEntity", cascade = CascadeType.PERSIST)
    List<FashionPickupEntity>  fashionPickupEntities = new ArrayList<FashionPickupEntity>();

    @OneToMany(mappedBy = "userEntity", cascade = CascadeType.PERSIST)
    List<FundingEntity>  fundingEntities = new ArrayList<FundingEntity>();

    @OneToMany(mappedBy = "userEntity", cascade = CascadeType.PERSIST)
    List<PortfolioEntity>  portfolioEntities = new ArrayList<PortfolioEntity>();

    @ManyToOne
    @JoinColumn
    private UserEntity userFollowing = this; // 팔로잉 팔로우 정보 저장 위한 셀프 참조
    @ManyToOne
    @JoinColumn
    private UserEntity userFollower = this;

    @OneToMany(mappedBy = "userFollowing", cascade = CascadeType.ALL)
    private List<UserEntity> followingList = new ArrayList<UserEntity>();

    @OneToMany(mappedBy = "userFollower", cascade = CascadeType.ALL)
    private List<UserEntity> followerList = new ArrayList<UserEntity>();

    public void addFollowing(UserEntity following) {
        this.followingList.add(following);

        if(!following.getFollowerList().contains(this)) {
            following.getFollowerList().add(this);
        }
        //연관관계의 주인을 통한 확인
        if(!following.getUserFollower().getFollowerList().contains(this)) {
            following.getUserFollower().getFollowerList().add(this);
        }
    }
    public void addFollower(UserEntity follower) {
        this.followerList.add(follower);

        if(follower.getFollowingList().contains(this)) {
            follower.getFollowingList().add(this);
        }
        //연관관계의 주인을 통한 확인
        if(!follower.getUserFollowing().getFollowingList().contains(this)) {
            follower.getUserFollowing().getFollowingList().add(this);
        }
    }

    @OneToOne   /*(cascade = CascadeType.PERSIST, fetch = FetchType.LAZY, mappedBy = "withdrawalUserIdEntity")*/
    WithdrawalEntity withdrawalEntity;// 휴탈 회원정보 - 유저 매핑

}
