package com.facaieve.backend.dto.chat;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.Positive;
import java.time.LocalDateTime;


public class ChattingDto {

    @Getter
    @Setter
    @AllArgsConstructor
    public static class ChatRoomPostDto {

        @Positive
        private long ownerId;
        @Positive
        private long visitorId;
    }
    @Getter
    @Setter
    @AllArgsConstructor
    public static class PostMessageDto {

        public enum MessageType{
            ENTER, TALK, LEAVE;
        }
        private MessageType type; // 메시지 타입
        private String roomId; // 방 번호
        private long sendingUserId;
        private String nickname;
        private String message;

    }


    @Getter
    @Setter
    @AllArgsConstructor
    @NoArgsConstructor
    public static class ChattingResponseDto {
        private long chattingEntityId;
        private String roomId;
        private String name;
        private long ownerId;
        private long visitorId;
        private LocalDateTime creationDate;
        private LocalDateTime lastEditDate;
    }


}
