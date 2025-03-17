package com.usedmarket.document;

import lombok.*;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import java.time.LocalDateTime;

@Document(collection = "chat")
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@ToString
public class Chat {
    @Id
    private String chatId;

    private String message;
    private String sender;
    private String roomName;

    private LocalDateTime createAt;

    @Builder
    public Chat(String message, String sender, String roomName, LocalDateTime createAt){
        this.message = message;
        this.sender = sender;
        this.roomName = roomName;
        this.createAt = createAt;
    }
}
