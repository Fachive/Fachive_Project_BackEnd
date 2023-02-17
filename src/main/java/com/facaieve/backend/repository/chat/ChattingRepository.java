package com.facaieve.backend.repository.chat;

import com.facaieve.backend.entity.chat.ChatRoomEntity;
import com.facaieve.backend.entity.user.UserEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface ChattingRepository extends JpaRepository<ChatRoomEntity, Long> {

    List<ChatRoomEntity> findAllByOwnerOrVisitor(UserEntity member, UserEntity member1);

    Optional<ChatRoomEntity> findByOwnerAndVisitor(UserEntity owner, UserEntity visitor);

    Optional<ChatRoomEntity> findByRoomId(String chatRoomId);
}
