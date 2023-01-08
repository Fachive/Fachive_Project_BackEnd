package com.facaieve.backend.entity.comment;
import com.facaieve.backend.entity.basetime.BaseEntity;
import com.facaieve.backend.entity.etc.MyPickEntity;
import javax.persistence.*;
import lombok.Getter;
import lombok.Setter;
import org.springframework.data.annotation.Id;

public abstract class CommentEntity extends BaseEntity {
    @Setter
    @Getter
            @Id
    Long commentId;
    @Getter
    Long userId;
    @Getter
    String commentBody;
    @Getter
    String postType;
    @Getter
    Long postId;

    MyPickEntity myPickEntity;



}
