package com.facaieve.backend.entity.user;


import com.facaieve.backend.Constant.UserRole;
import com.facaieve.backend.entity.basetime.BaseEntity;
import com.facaieve.backend.Constant.UserActive;
import com.facaieve.backend.entity.comment.FashionPickUpCommentEntity;
import com.facaieve.backend.entity.comment.FundingCommentEntity;
import com.facaieve.backend.entity.comment.PortfolioCommentEntity;
import com.facaieve.backend.entity.etc.MyPickEntity;
import com.facaieve.backend.entity.image.ImageEntityProfile;
import com.facaieve.backend.entity.image.S3ImageInfo;
import com.facaieve.backend.entity.post.FashionPickupEntity;
import com.facaieve.backend.entity.post.FundingEntity;
import com.facaieve.backend.entity.post.PortfolioEntity;
import javax.persistence.*;
import javax.transaction.Transactional;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.*;
import org.hibernate.annotations.ColumnDefault;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.io.Serializable;
import java.util.*;

@Entity
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Table(uniqueConstraints = {@UniqueConstraint(columnNames = "email")})// 이메일 기준으로 사용자 구분
public class UserEntity extends BaseEntity implements UserDetails, Serializable {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)//개별 엔티티 적용
    @Schema(description = "유저 식별ID")
    Long userEntityId;

    @Schema(description = "유저 권한")
    @Enumerated(EnumType.STRING)
    @Column
    UserRole role;//todo 관리자와 일반 사용자를 구분하는 로직을 구현할 것

    @Schema(description = "유저 닉네임")
    @Column
    String displayName;

    @Schema(description = "유저 이메일")
    @Column
    String email;

    @Schema(description = "비밀번호")
    @Column
    String password;

    @Schema(description = "광역시, 도")
    @Column
    String state;

    @Schema(description = "시,군,구")
    @Column
    String city;

    @Schema(description = "간단한 자기소개")
    @Column
    String userInfo;

    @Schema(description = "커리어")
    @Column
    String career;

    @Schema(description = "학력 및 교육사항")
    @Column
    String education;

    @Schema(description = "재직회사")
    @Column
    String Company;

    @Schema(description = "유저 활동 상태")
    @Enumerated(value = EnumType.STRING)
    UserActive userActive = UserActive.Active;

    @Schema(description = "이메일 인증 상태", defaultValue = "false")
    private boolean emailVerified = false;

    //패션픽업 댓글, 펀딩 댓글, 포폴 댓글 엔티티 매핑
    @Schema(description = "패션 픽업 게시물에 단 댓글 목록")
    @OneToMany(mappedBy = "userEntity", cascade = CascadeType.PERSIST)
    List<FashionPickUpCommentEntity>  fashionPickUpCommentEntities = new ArrayList<FashionPickUpCommentEntity>();

    @Schema(description = "펀딩 게시물에 단 댓글 목록")
    @OneToMany(mappedBy = "userEntity", cascade = CascadeType.PERSIST)
    List<FundingCommentEntity>  fundingCommentEntities = new ArrayList<FundingCommentEntity>();

    @Schema(description = "포트폴리오 게시물에 단 댓글 목록")
    @OneToMany(mappedBy = "userEntity", cascade = CascadeType.PERSIST)
    List<PortfolioCommentEntity>  portfolioCommentEntities = new ArrayList<PortfolioCommentEntity>();

    //패션픽업, 펀딩, 포폴 엔티티 매핑
    @Schema(description = "작성한 패션 픽업 게시물 목록")
    @OneToMany(mappedBy = "userEntity", cascade = CascadeType.PERSIST)
    List<FashionPickupEntity>  fashionPickupEntities = new ArrayList<FashionPickupEntity>();

    @Schema(description = "작성한 펀딩 게시물 목록")
    @OneToMany(mappedBy = "userEntity", cascade = CascadeType.PERSIST)
    List<FundingEntity>  fundingEntities = new ArrayList<FundingEntity>();

    @Schema(description = "작성한 포트폴리오 게시물 목록")
    @OneToMany(mappedBy = "userEntity", cascade = CascadeType.PERSIST)
    List<PortfolioEntity>  portfolioEntities = new ArrayList<PortfolioEntity>();


    @OneToMany(mappedBy = "followingUserEntity", cascade = CascadeType.ALL)
    List<FollowEntity> followingList = new ArrayList<FollowEntity>(); // 팔로우 정보 저장을 위한 셀프 참조

    @OneToMany(mappedBy = "followedUserEntity", cascade = CascadeType.ALL)
    List<FollowEntity> followerList = new ArrayList<FollowEntity>(); // 팔로우 정보 저장을 위한 셀프 참조

    @Schema(description = "프로필 이미지")
    @OneToOne(mappedBy = "userEntity", cascade = {CascadeType.PERSIST, CascadeType.REMOVE})
    S3ImageInfo profileImg; // 프로필 이미지 매핑



    @Schema(description = "마이픽을 설정한 게시물 및 댓글")
    @OneToMany(mappedBy = "pickingUser", cascade = CascadeType.PERSIST)
    List<MyPickEntity>  myPickEntityList = new ArrayList<MyPickEntity>();

    //사진과 이름을 업데이트함
    public UserEntity update(String displayName, String picture ){
        this.displayName = displayName;
        this.profileImg = S3ImageInfo.builder()
                .userEntity(this)
                .fileURI(picture)
                .build();
        return this;
    }


    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return List.of(new SimpleGrantedAuthority(role.getUserRole()));//권한 반환
    }

    @Override
    public String getUsername() {
        return email;
    }//위에서 email 을 제약 조건으로 설정했기 때문에 email 을 이름으로 반환함.

    @Override
    public boolean isAccountNonExpired() {
        return this.userActive.equals("활동 상태");
    }

    @Override
    public boolean isAccountNonLocked() {
        return true;
    }

    @Override
    public boolean isCredentialsNonExpired() {
        return true;
    }

    @Override
    public boolean isEnabled() {
        return true;
    }

    public void emailVerifiedSuccess(){//email 인증을 위한
        this.emailVerified = true;
    }
}
