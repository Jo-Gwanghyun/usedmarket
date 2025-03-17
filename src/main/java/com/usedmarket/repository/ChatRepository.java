package com.usedmarket.repository;


import com.usedmarket.document.Chat;
import org.springframework.data.mongodb.repository.MongoRepository;

import java.util.List;

public interface ChatRepository extends MongoRepository<Chat,Long> {
    List<Chat> findAllByRoomName(String roomName);

    void deleteAllByRoomName(String roomName);
}
