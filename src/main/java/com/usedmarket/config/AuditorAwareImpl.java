package com.usedmarket.config;

import com.usedmarket.entity.Member;
import com.usedmarket.repository.MemberRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.domain.AuditorAware;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;


import java.util.Optional;

@Configuration
public class AuditorAwareImpl implements AuditorAware<String> {

    @Autowired
    private MemberRepository memberRepository;

    @Override
    public Optional<String> getCurrentAuditor() {

        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();

        String userId = "";

            if(!authentication.getPrincipal().toString().equals("anonymousUser")) {
                userId = authentication.getName();
                Member member = memberRepository.findByEmail(userId);
                userId = member.getNickname();
            }

        return Optional.of(userId);
    }
}
