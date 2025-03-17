package com.usedmarket.repository;

import com.usedmarket.document.ChatRoom;
import org.springframework.data.mongodb.repository.MongoRepository;

import java.util.List;

public interface ChatRoomRepository extends MongoRepository<ChatRoom,String> {
    ChatRoom findByRoomName(String roomName);
    ChatRoom findByRoomId(String roomId);
    List<ChatRoom> findBySellerOrBuyer(String seller, String buyer);
    void deleteByRoomName(String roomName);


}
