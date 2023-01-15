package com.facaieve.backend.entity.user;

import com.facaieve.backend.Constant.UserActive;
import com.facaieve.backend.entity.basetime.BaseEntity;

import javax.persistence.*;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.*;

@NoArgsConstructor
@Entity
@Getter
@Setter
@AllArgsConstructor
public class WithdrawalEntity extends BaseEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    Long withdrawalId;

    @OneToOne(mappedBy = "withdrawalEntity")
    UserEntity withdrawalUserEntity;



    @Enumerated(value = EnumType.STRING)
    UserActive userActive;

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
}
