package com.facaieve.backend.repository.etc;

import com.facaieve.backend.entity.etc.MyPickEntity;
import com.facaieve.backend.entity.user.UserEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import javax.transaction.Transactional;
import java.util.List;

@Repository
public interface MyPickRepository extends JpaRepository<MyPickEntity,Long> {

    List<MyPickEntity> findMyPickEntitiesByPickingUser(UserEntity pickingUser);
    boolean existsByPickingUser(UserEntity pickingUser);


}
