package com.usedmarket.document;

import com.usedmarket.dto.ChatRoomDto;
import lombok.*;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import java.time.LocalDateTime;

@Document(collection = "chatroom")
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@ToString
public class ChatRoom {

    @Id
    private String roomId;

    private String roomName;
    private String seller;
    private String buyer;
    private LocalDateTime createdAt;

    @Builder
    public ChatRoom (String roomName, String seller, String buyer, LocalDateTime createdAt) {
        this.roomName = roomName;
        this.seller = seller;
        this.buyer = buyer;
        this.createdAt = createdAt;
    }

    public void update(ChatRoomDto chatRoomDto){
        this.roomName = chatRoomDto.getRoomName();
        this.seller = chatRoomDto.getSeller();
        this.buyer = chatRoomDto.getBuyer();
    }
}
