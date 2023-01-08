package com.facaieve.backend.repository.post;

import com.facaieve.backend.entity.post.PortfolioEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface PortfolioRepository extends JpaRepository<PortfolioEntity,Long> {
}
