package com.usedmarket.config;

import com.usedmarket.entity.Member;
import com.usedmarket.repository.MemberRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.domain.AuditorAware;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;


import java.util.Optional;


public class AuditorAwareImpl implements AuditorAware<String> {

    private Member member;

    @Override
    public Optional<String> getCurrentAuditor() {

        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();

        String userId = "";

        userId = authentication.getName();


        return Optional.of(userId);
    }
}
