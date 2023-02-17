package com.facaieve.backend.entity.chat;


import com.facaieve.backend.entity.basetime.BaseEntity;
import com.facaieve.backend.entity.user.UserEntity;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;

@Entity
@Table(name = "message")
@Getter
@Setter
@NoArgsConstructor
public class Message extends BaseEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "message_id")
    private Long id;

    private String content;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "chatting_entity_id")
    private ChatRoomEntity chatRoomEntity;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_entity_id")
    private UserEntity chatUser;
}