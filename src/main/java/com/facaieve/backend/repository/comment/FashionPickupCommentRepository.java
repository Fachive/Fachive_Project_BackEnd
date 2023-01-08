package com.facaieve.backend.repository.comment;

import com.facaieve.backend.entity.comment.FashionPickUpCommentEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface FashionPickupCommentRepository extends JpaRepository<FashionPickUpCommentEntity,Long> {
    FashionPickUpCommentEntity findFashionPickUpCommentEntityByFashionPickupCommentEntityId(Long id);
}
