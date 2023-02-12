package com.facaieve.backend.repository.etc;

import com.facaieve.backend.entity.comment.FashionPickUpCommentEntity;
import com.facaieve.backend.entity.comment.FundingCommentEntity;
import com.facaieve.backend.entity.comment.PortfolioCommentEntity;
import com.facaieve.backend.entity.etc.MyPickEntity;
import com.facaieve.backend.entity.post.FashionPickupEntity;
import com.facaieve.backend.entity.post.FundingEntity;
import com.facaieve.backend.entity.post.PortfolioEntity;
import com.facaieve.backend.entity.user.UserEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface MyPickRepository extends JpaRepository<MyPickEntity,Long> {

    List<MyPickEntity> findMyPickEntitiesByPickingUser(UserEntity pickingUser);
    boolean existsByPickingUser(UserEntity pickingUser);


    void deleteByPortfolioEntityAndPickingUser(PortfolioEntity portfolioEntity, UserEntity pickingUser);

    void deleteByFashionPickupEntityAndPickingUser(FashionPickupEntity fashionPickupEntity, UserEntity pickingUser);

    void deleteByFundingEntityAndPickingUser(FundingEntity fundingEntity, UserEntity pickingUser);

    void deleteByPortfolioCommentEntityAndPickingUser(PortfolioCommentEntity portfolioCommentEntity, UserEntity pickingUser);

    void deleteByFashionPickupCommentEntityAndPickingUser(FashionPickUpCommentEntity fashionPickUpCommentEntity, UserEntity pickingUser);

    void deleteByFundingCommentEntityAndPickingUser(FundingCommentEntity fundingCommentEntity, UserEntity pickingUser);

}
