package com.usedmarket.dto;

import com.usedmarket.document.Chat;
import lombok.*;

import java.time.LocalDateTime;

@Getter
@Setter
@NoArgsConstructor
@ToString
public class ChatDto {

    private String message;
    private String sender;
    private String roomName;

    private LocalDateTime createAt;

    public Chat toEntity(){
        return Chat.builder().message(message).sender(sender).roomName(roomName).createAt(createAt).build();
    }
}
