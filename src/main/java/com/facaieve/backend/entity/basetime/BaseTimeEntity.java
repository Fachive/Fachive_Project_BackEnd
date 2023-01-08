package com.facaieve.backend.entity.basetime;
import javax.persistence.*;
import lombok.Getter;
import lombok.Setter;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import java.time.LocalDateTime;

@EntityListeners(value={AuditingEntityListener.class})
@MappedSuperclass
@Getter
@Setter
public abstract class BaseTimeEntity {
    @CreatedDate
    @Column
    private LocalDateTime regTime;//등록시간

    @LastModifiedDate
    private LocalDateTime updateTime;//수정시간
}

