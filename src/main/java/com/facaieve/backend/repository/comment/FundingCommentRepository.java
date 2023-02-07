package com.facaieve.backend.repository.comment;

import com.facaieve.backend.entity.comment.FundingCommentEntity;
import com.facaieve.backend.entity.user.UserEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface FundingCommentRepository extends JpaRepository<FundingCommentEntity,Long> {
    FundingCommentEntity findFundingCommentEntityByFundingCommentEntityId(Long commentId);


}
