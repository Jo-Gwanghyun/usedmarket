package com.usedmarket.service;

import com.usedmarket.document.Chat;
import com.usedmarket.document.ChatRoom;
import com.usedmarket.dto.ChatDto;
import com.usedmarket.dto.ChatRoomDto;
import com.usedmarket.repository.ChatRepository;
import com.usedmarket.repository.ChatRoomRepository;
import com.usedmarket.repository.MemberRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Service
@RequiredArgsConstructor
@Transactional
public class ChatService {
    private final ChatRoomRepository chatRoomRepository;
    private final ChatRepository chatRepository;
    private final MemberRepository memberRepository;

    @Transactional(readOnly = true)
    public ChatRoom findByRoomName(String roomName) {
        return chatRoomRepository.findByRoomName(roomName);
    }

    @Transactional(readOnly = true)
    public ChatRoom findByRoomId(String roomId){
        return chatRoomRepository.findByRoomId(roomId);
    }

    public void createRoom(ChatRoomDto chatRoomDto) {

        if(chatRoomRepository.findByRoomName(chatRoomDto.getRoomName()) == null) {

            chatRoomDto.setCreateAt(LocalDateTime.now());

            ChatRoom chatRoom = chatRoomDto.toEntity();
            chatRoomRepository.save(chatRoom);
        }
    }

    @Transactional(readOnly = true)
    public String roomId(ChatRoomDto chatRoomDto) {
        ChatRoom chatRoom = chatRoomRepository.findByRoomName(chatRoomDto.getRoomName());

        return chatRoom.getRoomId();
    }

    @Transactional(readOnly = true)
    public List<ChatRoomDto> findByChatRoom(String seller, String buyer){

        List<ChatRoom> chatRoomList = chatRoomRepository.findBySellerOrBuyer(seller, buyer);
        List<ChatRoomDto> chatRoomDtoList = new ArrayList<>();

        for (ChatRoom chatRoom : chatRoomList) {
            ChatRoomDto chatRoomDto = new ChatRoomDto();
            chatRoomDto.setRoomId(chatRoom.getRoomId());
            chatRoomDto.setRoomName(chatRoom.getRoomName());
            chatRoomDto.setSeller(chatRoom.getSeller());
            chatRoomDto.setBuyer(chatRoom.getBuyer());

            chatRoomDtoList.add(chatRoomDto);
        }

        return chatRoomDtoList;
    }

    public ChatDto sendChat(ChatDto message){
        message.setCreateAt(LocalDateTime.now());
        Chat chat = message.toEntity();

        chatRepository.save(chat);

        return message;
    }

    @Transactional(readOnly = true)
    public List<ChatDto> findByChatList(String roomName){
        List<Chat> chatList = chatRepository.findAllByRoomName(roomName);

        List<ChatDto> chatDtoList = new ArrayList<>();

        for (Chat chat : chatList) {
            ChatDto chatDto = new ChatDto();
            chatDto.setRoomName(chat.getRoomName());
            chatDto.setSender(chat.getSender());
            chatDto.setMessage(chat.getMessage());
            chatDto.setCreateAt(chat.getCreateAt());

            chatDtoList.add(chatDto);
        }

        return chatDtoList;
    }

    public void deleteChat(ChatRoomDto chatRoomDto, String nickname){
        if(chatRoomDto.getSeller().equals(nickname)){
            chatRoomDto.setSeller("");
            ChatRoom chatRoom = findByRoomName(chatRoomDto.getRoomName());
            chatRoom.update(chatRoomDto);
            chatRoomRepository.save(chatRoom);
        } else {
            chatRoomDto.setBuyer("");
            ChatRoom chatRoom = findByRoomName(chatRoomDto.getRoomName());
            chatRoom.update(chatRoomDto);
            chatRoomRepository.save(chatRoom);
        }

        if(chatRoomDto.getSeller().isEmpty() && chatRoomDto.getBuyer().isEmpty()){
            chatRepository.deleteAllByRoomName(chatRoomDto.getRoomName());
            chatRoomRepository.deleteByRoomName(chatRoomDto.getRoomName());
        }
    }

    public String getNickname(String email){
        return memberRepository.findByEmail(email).getNickname();
    }

}
