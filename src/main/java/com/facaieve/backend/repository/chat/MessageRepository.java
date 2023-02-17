package com.facaieve.backend.repository.chat;

import com.facaieve.backend.entity.chat.Message;
import org.springframework.data.jpa.repository.JpaRepository;

public interface MessageRepository extends JpaRepository<Message, Long> {
}
