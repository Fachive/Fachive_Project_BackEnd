package com.facaieve.backend.repository.post;


import com.facaieve.backend.entity.etc.CategoryEntity;
import com.facaieve.backend.entity.post.FundingEntity;
import com.facaieve.backend.entity.user.UserEntity;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface FundingRepository extends JpaRepository<FundingEntity,Long> {

    Page<FundingEntity> findFundingEntitiesByCategoryEntity(CategoryEntity categoryEntity, Pageable pageable);



}
