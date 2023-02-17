package com.facaieve.backend.entity.chat;


import com.facaieve.backend.entity.basetime.BaseEntity;
import com.facaieve.backend.entity.user.UserEntity;
import lombok.*;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

@Entity
@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class ChatRoomEntity extends BaseEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "chatting_entity_id")
    private Long id;

    private String roomId;

    private String name;

    @ManyToOne
    @JoinColumn(name = "owner_id")
    private UserEntity owner;

    @ManyToOne
    @JoinColumn(name = "visitor_id")
    private UserEntity visitor;

    @OneToMany(mappedBy = "chatRoomEntity", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<Message> messages = new ArrayList<>();
}
