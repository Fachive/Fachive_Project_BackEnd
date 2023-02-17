package com.facaieve.backend.service.chat;


import com.facaieve.backend.dto.chat.ChattingDto;
import com.facaieve.backend.entity.chat.ChatRoomEntity;
import com.facaieve.backend.entity.user.UserEntity;
import com.facaieve.backend.mapper.exception.BusinessLogicException;
import com.facaieve.backend.mapper.exception.ExceptionCode;
import com.facaieve.backend.repository.chat.ChattingRepository;
import com.facaieve.backend.service.user.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Objects;
import java.util.Optional;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class ChattingService {
    private final ChattingRepository chattingRepository;
    private final UserService userService;

    public ChatRoomEntity createChatRoom(ChattingDto.ChatRoomPostDto chatRoomPostDto) {
        ChatRoomEntity chatRoom = findExistsRoom(chatRoomPostDto.getOwnerId(), chatRoomPostDto.getVisitorId());

        return  chattingRepository.save(chatRoom);
    }

    public List<ChatRoomEntity> findMyChatRooms(long userId) {
        UserEntity member = userService.findUserEntityById(userId);
        return chattingRepository.findAllByOwnerOrVisitor(member, member);
    }

    public ChatRoomEntity findChatRoom(String chatRoomId) {
        return findVerifiedChatRoom(chatRoomId);
    }

    private ChatRoomEntity findVerifiedChatRoom(String chatRoomId) {
        return chattingRepository.findByRoomId(chatRoomId).orElseThrow(() ->
                new BusinessLogicException(ExceptionCode.CHATROOM_NOT_FOUND));


    }

    private ChatRoomEntity findExistsRoom(long ownerId, long visitorId) {

        UserEntity owner = userService.findUserEntityById(ownerId);
        UserEntity visitor = userService.findUserEntityById(visitorId);
        Optional<ChatRoomEntity> optionalChatRoomEntity = chattingRepository.findByOwnerAndVisitor(owner, visitor);

        return optionalChatRoomEntity.orElseGet(() -> ChatRoomEntity
                .builder()
                .owner(owner)
                .visitor(visitor)
                .roomId(UUID.randomUUID().toString())
                .build());
    }


}
