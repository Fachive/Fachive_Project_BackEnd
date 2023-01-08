package com.facaieve.backend.repository.post;

import com.facaieve.backend.entity.post.FashionPickupEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface FashionPickupRepository extends JpaRepository<FashionPickupEntity,Long> {
}
