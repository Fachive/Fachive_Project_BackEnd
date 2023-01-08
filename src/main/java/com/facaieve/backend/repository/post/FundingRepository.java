package com.facaieve.backend.repository.post;


import com.facaieve.backend.entity.post.FundingEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface FundingRepository extends JpaRepository<FundingEntity,Long> {
}
