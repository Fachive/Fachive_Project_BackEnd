package com.facaieve.backend.repository.comment;

import com.facaieve.backend.entity.comment.FundingCommentEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface FundingCommentRepository extends JpaRepository<FundingCommentEntity,Long> {
    FundingCommentEntity findFundingCommentEntityByFundingCommentId(Long commentId);
}
