package com.usedmarket.service;

import com.usedmarket.constant.Role;
import com.usedmarket.dto.MemberDto;
import com.usedmarket.dto.MemberUpdateDto;
import com.usedmarket.entity.Member;
import com.usedmarket.exception.PasswordException;
import com.usedmarket.repository.MemberRepository;
import jakarta.persistence.EntityNotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Random;

@Service
@RequiredArgsConstructor
@Transactional
public class MemberService implements UserDetailsService {
    private final MemberRepository memberRepository;
    private final PasswordEncoder passwordEncoder;
    private final MemberDeleteService memberDeleteService;

    public void saveMember(MemberDto memberDto) throws PasswordException {

        passwordCheck(memberDto.getPassword(),memberDto.getPasswordCheck());

        memberDto.setPassword(passwordEncoder.encode(memberDto.getPassword()));

        if (memberDto.getNickname().equals("admin")) {
            memberDto.setRole(Role.ADMIN);
        } else {
            memberDto.setRole(Role.MEMBER);
        }

        Member member = memberDto.toEntity();

        duplicateMember(member);

        memberRepository.save(member);
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

    public void passwordCheck(String password, String passwordCheck) throws PasswordException {
        if(!password.equals(passwordCheck)){
            throw new PasswordException("비밀번호가 일치하지 않습니다.");
        }
    }

    public String newPassword(String email){
        Member member = memberRepository.findByEmail(email);
        String password = makeNewPassword();

        MemberUpdateDto memberUpdateDto = MemberUpdateDto.of(member);
        String encodePassword = passwordEncoder.encode(password);

        member.update(memberUpdateDto.getMemberName(), memberUpdateDto.getNickname(),
                encodePassword ,memberUpdateDto.getAddress());

        return password;
    }

    private String makeNewPassword(){
        Random random = new Random();
        String password = "";
        for(int i=0 ;i<8; i++){
            password += Integer.toString(random.nextInt(10));
        }
        return password;
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

    public Member findByEmail(String email) {
        return memberRepository.findByEmail(email);
    }

    public void updateMember(MemberUpdateDto memberUpdateDto) throws PasswordException {
        Member member = memberRepository.findById(memberUpdateDto.getId()).orElseThrow(EntityNotFoundException::new);

        String password = member.getPassword();

        if(!memberUpdateDto.getPassword().isEmpty()){
            if(memberUpdateDto.getPassword().length() < 8 || memberUpdateDto.getPassword().length() > 16){
                throw new PasswordException("비밀번호는 최소 8자, 최대 16자로 입력하셔야합니다.");
            }
            passwordCheck(memberUpdateDto.getPassword(), memberUpdateDto.getPasswordCheck());
            password = passwordEncoder.encode(memberUpdateDto.getPassword());
        }

        if(!member.getNickname().equals(memberUpdateDto.getNickname())){
            if(memberRepository.existsByNickname(memberUpdateDto.getNickname())){
                throw new IllegalStateException("닉네임을 다시 확인해주세요.");
            }
        }

        member.update(memberUpdateDto.getMemberName(), memberUpdateDto.getNickname(),
                password,memberUpdateDto.getAddress());
    }

    public void deleteMember(Long memberId){

        if(!memberDeleteService.findByItem(memberId)){
            throw new IllegalStateException("거래중인상품이 있습니다. 상품을 확인해주세요.");
        }

        memberRepository.deleteById(memberId);
        SecurityContextHolder.clearContext();
    }
}
