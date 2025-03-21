package com.usedmarket.service;

import com.usedmarket.constant.Role;
import com.usedmarket.dto.MemberDto;
import com.usedmarket.entity.Member;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestBuilders;
import org.springframework.security.test.web.servlet.response.SecurityMockMvcResultMatchers;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.transaction.annotation.Transactional;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
@AutoConfigureMockMvc
@Transactional
class MemberServiceTest {
    @Autowired
    private MemberService memberService;
    @Autowired
    PasswordEncoder passwordEncoder;
    @Autowired
    private MockMvc mockMvc;


    public Member newMember(){
        MemberDto memberDto = new MemberDto();
        memberDto.setEmail("test@test.com");
        memberDto.setPassword("11111111");
        memberDto.setPasswordCheck("11111111");
        memberDto.setNickname("테스트닉네임");
        memberDto.setMemberName("테스트1");
        memberDto.setAddress("abcd");
        memberDto.setRole(Role.MEMBER);

        return memberDto.toEntity();
    }

//    @Test
//    @DisplayName("회원가입테스트")
//    public void newMemberTest(){
//        Member member = newMember();
//        Member savedMember = memberService.saveMember(member);
//        assertEquals(member.getEmail(),savedMember.getEmail());
//        assertEquals(member.getNickname(),savedMember.getNickname());
//    }

//    @Test
//    @DisplayName("중복체크 테스트")
//    public void duplicateMemberTest(){
//        Member mem1 = newMember();
//        Member mem2 = newMember();
//
//        memberService.saveMember(mem1);
//
//        Throwable e = assertThrows(IllegalStateException.class,()->{
//            memberService.saveMember(mem2);
//        });
//
//        assertEquals("이메일 또는 닉네임을 다시 확인해주세요",e.getMessage());
//    }

//    @Test
//    @DisplayName("로그인성공테스트")
//    public void loginSuccessTest() throws Exception {
//        Member member = newMember();
//        Member savedmember = memberService.saveMember(member);
//
//        mockMvc.perform(SecurityMockMvcRequestBuilders.formLogin().userParameter("email")
//                .loginProcessingUrl("/members/login")
//                .user(savedmember.getEmail()).password(savedmember.getPassword()))
//                .andExpect(SecurityMockMvcResultMatchers.authenticated());
//    }

}