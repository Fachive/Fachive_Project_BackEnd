package com.facaieve.backend.repository.comment;


import com.facaieve.backend.entity.comment.PortfolioCommentEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface PortfolioCommentRepository extends JpaRepository<PortfolioCommentEntity,Long> {
    PortfolioCommentEntity findPortfolioCommentEntityByPortfolioCommentId(Long portfolioCommentId);
}
