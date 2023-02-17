package com.facaieve.backend.controller.websocket;

import com.facaieve.backend.dto.chat.ChattingDto;
import com.facaieve.backend.entity.chat.ChatRoomEntity;
import com.facaieve.backend.mapper.chat.ChatRoomMapper;
import com.facaieve.backend.service.chat.ChattingService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import javax.transaction.Transactional;
import javax.validation.Valid;
import javax.validation.constraints.Positive;
import java.util.List;

@RestController
@RequiredArgsConstructor
@Validated
@RequestMapping(value = "/v1/chat")
@Slf4j
public class ChattingController {

    private final ChattingService chatRoomService;
    private final ChatRoomMapper chatRoomMapper;

    //채팅방 목록 조회
    @GetMapping(value = "/rooms/{user-id}")
    public ResponseEntity getRooms(@PathVariable("user-id") @Positive long userId){

        log.info("# All My Chat Rooms");
        List<ChatRoomEntity> myChatting = chatRoomService.findMyChatRooms(userId);
        return new ResponseEntity<>(chatRoomMapper.chattingsToChattingResponseDtos(myChatting), HttpStatus.OK);
    }

    //채팅방 개설
    @Transactional
    @PostMapping(value = "/room")
    public ResponseEntity postRoom(@Valid @RequestBody ChattingDto.ChatRoomPostDto chatRoomPostDto){

        ChatRoomEntity newChatRoomEntity = chatRoomService.createChatRoom(chatRoomPostDto);
        return new ResponseEntity<>(chatRoomMapper.chattingEntityToChattingResponseDto(newChatRoomEntity), HttpStatus.CREATED);
    }

    //채팅방 조회
    @GetMapping("/room/{chatroom-id}")
    public ResponseEntity getChatting(@PathVariable("chatroom-id") String chatRoomId){

        log.info("# get Chat Room, chatRoomID : " + chatRoomId);
        ChatRoomEntity chatting = chatRoomService.findChatRoom(chatRoomId);

        return new ResponseEntity<>(chatRoomMapper.chattingEntityToChattingResponseDto(chatting), HttpStatus.OK);

    }
}
