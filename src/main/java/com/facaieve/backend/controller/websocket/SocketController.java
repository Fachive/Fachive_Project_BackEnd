package com.facaieve.backend.controller.websocket;

import com.facaieve.backend.dto.chat.ChattingDto;
import com.facaieve.backend.entity.chat.ChatRoomEntity;
import com.facaieve.backend.mapper.chat.MessageMapper;
import com.facaieve.backend.service.chat.ChattingService;
import com.facaieve.backend.service.chat.MessageService;
import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Profile;
import org.springframework.messaging.handler.annotation.DestinationVariable;
import org.springframework.messaging.handler.annotation.MessageMapping;

import org.springframework.messaging.handler.annotation.Payload;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@Profile("stomp")
@RestController
@RequestMapping("/chat")
@RequiredArgsConstructor
public class SocketController {

    private final SimpMessagingTemplate simpMessagingTemplate;//@EnableWebSocketMessageBroker를 통해서 등록되는 bean이다. 특정 Broker로 메시지를 전달한다.
    private final ChattingService chattingService;
    private final MessageService messageService;
    private final MessageMapper messageMapper;
    @MessageMapping("/join.register")
    public void makeChatRoom(@Payload ChattingDto.PostMessageDto postMessageDto) throws Exception{


        postMessageDto.setMessage(postMessageDto.getNickname() + "님이 대화에 참여하였습니다.");
        simpMessagingTemplate.convertAndSend("/queue/chat/room/" + postMessageDto.getRoomId(), postMessageDto);
      }

    @MessageMapping("/message.send")
    public void sendMessage(@Payload ChattingDto.PostMessageDto postMessageDto) {
        this.simpMessagingTemplate.convertAndSend("/queue/chat/room/" + postMessageDto.getRoomId(), postMessageDto);

        // DB에 채팅내용 저장
        ChatRoomEntity foundChatting = chattingService.findChatRoom(postMessageDto.getRoomId());
        messageService.createMessage(messageMapper.messagePostDtoToMessage(postMessageDto));
    }

}
