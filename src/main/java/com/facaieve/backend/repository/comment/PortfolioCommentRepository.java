package com.facaieve.backend.repository.comment;


import com.facaieve.backend.entity.comment.PortfolioCommentEntity;
import com.facaieve.backend.entity.user.UserEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import javax.transaction.Transactional;

@Repository
public interface PortfolioCommentRepository extends JpaRepository<PortfolioCommentEntity,Long> {
    PortfolioCommentEntity findPortfolioCommentEntityByPortfolioCommentEntityId(Long portfolioCommentId);
    @Transactional
    void deleteByPortfolioCommentEntityId(Long id);


}
