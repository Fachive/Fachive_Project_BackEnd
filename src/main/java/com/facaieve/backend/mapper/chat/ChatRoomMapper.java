package com.facaieve.backend.mapper.chat;


import com.facaieve.backend.dto.chat.ChattingDto;
import com.facaieve.backend.entity.chat.ChatRoomEntity;
import com.facaieve.backend.entity.user.UserEntity;
import org.mapstruct.Mapper;

import java.util.List;

@Mapper(componentModel = "spring")
public interface ChatRoomMapper {
    default ChatRoomEntity chattingPostDtoToChatEntity(ChattingDto.ChatRoomPostDto chatRoomPostDto){
        if (chatRoomPostDto == null) {
            return null;
        }

        ChatRoomEntity chatting = new ChatRoomEntity();
        UserEntity owner = new UserEntity();
        UserEntity visitor = new UserEntity();


        owner.setUserEntityId(chatRoomPostDto.getOwnerId());
        visitor.setUserEntityId(chatRoomPostDto.getVisitorId());


        chatting.setOwner(owner);
        chatting.setVisitor(visitor);

        return chatting;

    }


//    default ChatRoom chatRoomPostDtoToChatRoom(ChatRoomPostDto chatRoomPostDto) {
//        ChatRoom chatRoom = new ChatRoom();
//        Member seller = new Member();
//        Member buyer = new Member();
//        Product product = new Product();
//
//        seller.setMemberId(chatRoomPostDto.getSellerId());
//        buyer.setMemberId(chatRoomPostDto.getBuyerId());
//        product.setProductId(chatRoomPostDto.getProductId());
//
//        chatRoom.setSeller(seller);
//        chatRoom.setBuyer(buyer);
//        chatRoom.setProduct(product);
//
//        return chatRoom;
//    }

    default ChattingDto.ChattingResponseDto chattingEntityToChattingResponseDto(ChatRoomEntity chatting) {
        if (chatting == null) {
            return null;
        }

        ChattingDto.ChattingResponseDto chatRoomResponseDto = new ChattingDto.ChattingResponseDto();

        chatRoomResponseDto.setChattingEntityId(chatting.getId());
        chatRoomResponseDto.setRoomId(chatting.getRoomId());
        chatRoomResponseDto.setName(chatting.getName());
        chatRoomResponseDto.setOwnerId(chatting.getOwner().getUserEntityId());
        chatRoomResponseDto.setVisitorId(chatting.getVisitor().getUserEntityId());
        chatRoomResponseDto.setCreationDate(chatting.getRegTime());
        chatRoomResponseDto.setLastEditDate(chatting.getUpdateTime());

        return chatRoomResponseDto;
    }

    List<ChattingDto.ChattingResponseDto> chattingsToChattingResponseDtos(List<ChatRoomEntity> chattingEntities);

}
