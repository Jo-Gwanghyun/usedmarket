package com.usedmarket.controller;

import com.usedmarket.document.ChatRoom;
import com.usedmarket.dto.ChatDto;
import com.usedmarket.dto.ChatRoomDto;
import com.usedmarket.service.ChatService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.messaging.handler.annotation.DestinationVariable;
import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.messaging.handler.annotation.SendTo;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.security.Principal;
import java.util.List;

@Controller
@RequiredArgsConstructor
public class ChatController {
    private final ChatService chatService;

    @ResponseBody
    @PostMapping("/chat/add")
    public ResponseEntity<String> listCheck(@RequestBody ChatRoomDto chatRoomDto) {

        if(chatService.findByRoomName(chatRoomDto.getRoomName()) == null){
            chatService.createRoom(chatRoomDto);
        }

        String roomId = chatService.roomId(chatRoomDto);

        return ResponseEntity.ok(roomId);
    }

    @GetMapping("/chat/list")
    public String chatList(Principal principal, Model model){

        String nickname = chatService.getNickname(principal.getName());

        List<ChatRoomDto> findByChatRoom = chatService.findByChatRoom(nickname, nickname);
        model.addAttribute("chatRoomList",findByChatRoom);

        return "chat/chatList";
    }

    @GetMapping("/chat/start/{roomId}")
    public String chat(@PathVariable String roomId, Principal principal,Model model){

        String nickname = chatService.getNickname(principal.getName());
        String roomName = chatService.findByRoomId(roomId).getRoomName();

        List<ChatDto> chatList = chatService.findByChatList(roomName);

        model.addAttribute("nickname",nickname);
        model.addAttribute("roomName",roomName);
        model.addAttribute("chatList",chatList);
        return "chat/chat";
    }

    @MessageMapping("{roomName}") // 메시지가 전송되면 해당메서드 호출 (앞에 컨피그에서 Prefixes 적용한건(/app) 생략)
    @SendTo("/topic/{roomName}") // 구독한곳으로 메시지전송 (앞에 컨피그에서 broker 적용한건(/topic) 붙여줘야함)
    public ChatDto sendChat(@DestinationVariable String roomName, ChatDto message){
        return chatService.sendChat(message);
    }

    @ResponseBody
    @PostMapping("/chat/delete")
    public ResponseEntity<String> deleteChat(@RequestBody ChatRoomDto chatRoomDto, Principal principal){

        System.out.println("삭제확인" + chatRoomDto);

        String nickname = chatService.getNickname(principal.getName());
        chatService.deleteChat(chatRoomDto, nickname);

        return ResponseEntity.ok("삭제되었습니다.");
    }


}
