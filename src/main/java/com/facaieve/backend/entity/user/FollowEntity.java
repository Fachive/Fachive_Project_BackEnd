package com.facaieve.backend.entity.user;


import com.facaieve.backend.entity.basetime.BaseEntity;
import com.facaieve.backend.entity.user.UserEntity;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;

@Getter
@Setter
@Entity
@NoArgsConstructor
public class FollowEntity extends BaseEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    long followEntityId;


    @ManyToOne
    @JoinColumn(name="followingUserEntity_Id")
    @Schema(description = "팔로우 하는 사람")
    private UserEntity followingUserEntity;
    @ManyToOne
    @JoinColumn(name="followedUserEntity_Id")
    @Schema(description = "팔로우 되는 사람")
    private UserEntity followedUserEntity;
}
