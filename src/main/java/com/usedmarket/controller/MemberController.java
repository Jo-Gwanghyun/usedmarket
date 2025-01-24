package com.usedmarket.controller;

import com.usedmarket.constant.Role;
import com.usedmarket.dto.EmailCheckDto;
import com.usedmarket.dto.MemberDto;
import com.usedmarket.dto.MemberUpdateDto;
import com.usedmarket.entity.Member;
import com.usedmarket.exception.PasswordException;
import com.usedmarket.service.MemberDeleteService;
import com.usedmarket.service.MemberService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import java.security.Principal;
import java.sql.SQLIntegrityConstraintViolationException;

@Controller
@RequestMapping("/members")
@RequiredArgsConstructor
public class MemberController {
    private final MemberService memberService;
    private final MemberDeleteService memberDeleteService;
    private final PasswordEncoder passwordEncoder;

    @GetMapping("/new")
    public String newMember(Model model) {
        model.addAttribute("memberDto", new MemberDto());
        return "member/newMember";
    }

    @PostMapping("/new")
    public String newMember(@Valid MemberDto memberDto, BindingResult bindingResult, Model model){

        if(bindingResult.hasErrors()){
            return "member/newMember";
        }

        try {
            memberService.passwordCheck(memberDto.getPassword(),memberDto.getPasswordCheck());

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
        } catch (PasswordException e){
            bindingResult.rejectValue("passwordCheck","passwordError",e.getMessage());
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
    public Boolean nicknameCheck(@RequestBody @Valid String nickname) throws ParseException {

        JSONParser parser = new JSONParser();
        JSONObject jsonObject = (JSONObject) parser.parse(nickname);

        return memberService.checkNickname(jsonObject.get("nickname").toString());
    }

    @GetMapping("/mypage")
    public String myPage(Principal principal, Model model){
        Member member = memberService.findByEmail(principal.getName());

        model.addAttribute("id", member.getId());
        return "/member/memberPage";
    }

    @GetMapping("/update/{memberId}")
    public String updateMember(@PathVariable("memberId")Long memberId, Principal principal, Model model){

        Member member = memberService.findByEmail(principal.getName());

        if(!memberId.equals(member.getId())){
            return "redirect:/";
        }

        MemberUpdateDto memberUpdateDto = MemberUpdateDto.of(member);
        model.addAttribute("memberUpdateDto", memberUpdateDto);

        return "/member/updateMember";
    }

    @ResponseBody
    @PutMapping("/update/{memberId}")
    public ResponseEntity<String> updateMember(@RequestBody MemberUpdateDto memberUpdateDto){

        try {
            memberService.updateMember(memberUpdateDto);

        } catch (PasswordException | IllegalStateException e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(e.getMessage());
        }
        return ResponseEntity.ok("수정완료");
    }

    @ResponseBody
    @DeleteMapping("/delete/{memberId}")
    public ResponseEntity<String> deleteMember(@RequestBody @PathVariable("memberId") Long memberId){

        //거래종료상태인 상품이있을경우 해당 상품 삭제
        memberDeleteService.soldOutStateDelete(memberId);

        //위시리스트에 상품이있을경우 삭제
        memberDeleteService.wishlistDelete(memberId);
        try {
            memberService.deleteMember(memberId);
        } catch (IllegalStateException e){
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(e.getMessage());
        }

        return ResponseEntity.ok("탈퇴완료");
    }
}
