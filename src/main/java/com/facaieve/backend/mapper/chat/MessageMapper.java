package com.facaieve.backend.mapper.chat;

import com.facaieve.backend.dto.chat.ChattingDto;
import com.facaieve.backend.entity.chat.Message;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface MessageMapper {

    Message messagePostDtoToMessage(ChattingDto.PostMessageDto postMessageDto);
//    default Message messagePostDtoToMessage(ChattingDto.PostMessageDto messagePostDto) {
//        Message message = new Message();
//        Member member = new Member();
//        ChatRoom chatRoom = new ChatRoom();
//
//        member.setMemberId(messagePostDto.getMemberId());
//        chatRoom.setId(messagePostDto.getChatRoomId());
//
//        message.setContent(messagePostDto.getContent());
//        message.setChatRoom(chatRoom);
//        message.setMember(member);
//
//        return message;
//    }
}
