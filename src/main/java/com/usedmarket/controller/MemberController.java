package com.usedmarket.controller;

import com.usedmarket.constant.Role;
import com.usedmarket.dto.EmailCheckDto;
import com.usedmarket.dto.MemberDto;
import com.usedmarket.entity.Member;
import com.usedmarket.service.MemberService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

@Controller
@RequestMapping("/members")
@RequiredArgsConstructor
public class MemberController {
    private final MemberService memberService;
    private final PasswordEncoder passwordEncoder;

    @GetMapping("/new")
    public String newMember(Model model) {
        model.addAttribute("memberDto", new MemberDto());
        return "member/newMember";
    }

    @PostMapping("/new")
    public String newMember(@Valid MemberDto memberDto, BindingResult bindingResult, Model model) {
        if(bindingResult.hasErrors()){
            return "member/newMember";
        }

        if(!memberDto.getPassword().equals(memberDto.getPasswordCheck())){
            bindingResult.rejectValue("passwordCheck","passwordCheckError",
                    "비밀번호가 일치하지않습니다.");
            return "member/newMember";
        }

        try {
            memberDto.setPassword(passwordEncoder.encode(memberDto.getPassword()));

            if (memberDto.getNickname().equals("admin")) {
                memberDto.setRole(Role.ADMIN);
            } else {
                memberDto.setRole(Role.MEMBER);
            }

            Member member = memberDto.toEntity();
            memberService.saveMember(member);

        } catch (IllegalStateException e){
            model.addAttribute("errorMessage",e.getMessage());
            return "/member/newMember";
        }
        return "redirect:/";
    }

    @GetMapping("/login")
    public String loginMember(){
        return "/member/loginMember";
    }

    @GetMapping("/login/error")
    public String loginError(Model model){
        model.addAttribute("loginError","아이디 또는 비밀번호를 확인하세요");
        return "/member/loginMember";
    }

    @ResponseBody
    @PostMapping("/nickcheck")
    public Boolean nicknameCheck(@RequestBody @Valid EmailCheckDto checkDto){
        return memberService.checkNickname(checkDto.getNickname());
    }
}
