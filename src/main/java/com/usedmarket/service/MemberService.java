package com.usedmarket.service;

import com.usedmarket.entity.Member;
import com.usedmarket.repository.MemberRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
@Transactional
public class MemberService implements UserDetailsService {
    private final MemberRepository memberRepository;

    public Member saveMember(Member member) {
        duplicateMember(member);
        return memberRepository.save(member);
    }

    public String getNickname(String email){
       return memberRepository.findByEmail(email).getNickname();

    }

    private void duplicateMember(Member member) {
        boolean emailDuplicateCheck = memberRepository.existsByEmail(member.getEmail());
        boolean nicknameDuplicateCheck = memberRepository.existsByNickname(member.getNickname());

        if(emailDuplicateCheck||nicknameDuplicateCheck){
            throw new IllegalStateException("이메일 또는 닉네임을 다시 확인해주세요");
        }
    }

    public boolean checkNickname(String nickname){
        return memberRepository.existsByNickname(nickname);
    }


    @Override
    public UserDetails loadUserByUsername(String email) throws UsernameNotFoundException {
        Member member = memberRepository.findByEmail(email);

        if(member == null) {
            throw new UsernameNotFoundException(email);
        }

        return User.builder().username(member.getEmail()).password(member.getPassword())
                .roles(member.getRole().toString()).build();
    }
}
