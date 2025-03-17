package com.usedmarket.dto;

import com.usedmarket.document.ChatRoom;
import lombok.*;

import java.time.LocalDateTime;

@Getter
@Setter
@NoArgsConstructor
@ToString
public class ChatRoomDto {

    private String roomId;

    private String roomName;

    private String seller;

    private String buyer;

    private LocalDateTime createAt;

    public ChatRoom toEntity(){
        return ChatRoom.builder().roomName(roomName).seller(seller).buyer(buyer).createdAt(createAt).build();
    }

}
