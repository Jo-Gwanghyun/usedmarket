package com.usedmarket.controller;

import com.usedmarket.dto.EmailCheckDto;
import com.usedmarket.service.EmailSendService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.Map;

@RestController
@RequiredArgsConstructor
@RequestMapping("/email")
public class EmailController {
    private final EmailSendService emailSendService;

    @PostMapping("/check")
    public Boolean checkEmail(@RequestBody @Valid EmailCheckDto emailCheckDto){
        return emailSendService.checkMail(emailCheckDto.getEmail());
    }

    @PostMapping("/send")
    public Map<String,String> mailSend(@RequestBody @Valid EmailCheckDto emailCheckDto){
        String code = emailSendService.joinEmail(emailCheckDto.getEmail());

        Map<String, String> response = new HashMap<>();
        response.put("code",code);
        return response;
    }

    @PostMapping("/authNum")
    public String authNum(@RequestBody @Valid EmailCheckDto emailCheckDto){
        Boolean checked = emailSendService.checkAuthNum(emailCheckDto.getEmail(), emailCheckDto.getAuthNum());
        return checked ? "success" : "fail";
    }

    @PostMapping("/passSend")
    public ResponseEntity<String> passSend(@RequestBody @Valid EmailCheckDto emailCheckDto){
        System.out.println("-------");
        System.out.println(emailCheckDto.getAuthNum());
        emailSendService.passSend(emailCheckDto);
        return ResponseEntity.ok(emailCheckDto.getAuthNum());
    }

}
