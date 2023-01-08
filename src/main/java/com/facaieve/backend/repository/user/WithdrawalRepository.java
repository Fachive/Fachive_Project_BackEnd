package com.facaieve.backend.repository.user;

import com.facaieve.backend.entity.comment.FundingCommentEntity;
import com.facaieve.backend.entity.user.WithdrawalEntity;
import org.springframework.data.jpa.repository.JpaRepository;

public interface WithdrawalRepository extends JpaRepository<WithdrawalEntity,Long> {
}
