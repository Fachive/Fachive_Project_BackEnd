package com.facaieve.backend.entity.user;

import com.facaieve.backend.Constant.UserActive;
import com.facaieve.backend.entity.basetime.BaseEntity;

import javax.persistence.*;
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


}
