package com.facaieve.backend.service.chat;


import com.facaieve.backend.entity.chat.Message;
import com.facaieve.backend.repository.chat.MessageRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class MessageService {

    private final MessageRepository messageRepository;

    public Message createMessage(Message message) {
        return messageRepository.save(message);
    }
}
