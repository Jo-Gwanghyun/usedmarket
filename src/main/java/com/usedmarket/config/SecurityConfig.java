package com.usedmarket.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityCustomizer;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.util.matcher.AntPathRequestMatcher;

@Configuration
@EnableWebSecurity
public class SecurityConfig {
    @Bean
    SecurityFilterChain filterChain(HttpSecurity http) throws Exception {

        http.formLogin((formLogin)->formLogin.loginPage("/members/login")
                        .defaultSuccessUrl("/",true) // 로그인성공시 이동할 url
                        .usernameParameter("email")
                        .failureForwardUrl("/members/login/error"))
                .logout((logout)->logout.logoutRequestMatcher(new AntPathRequestMatcher("/members/logout"))
                        .logoutSuccessUrl("/") //로그아웃성공시 이동할 url
                        .invalidateHttpSession(true)); // 로그아웃시 섹션삭제

        http.authorizeHttpRequests((authorizeHttpRequests)->
                                    authorizeHttpRequests.requestMatchers("/","/page/**","/email/**","/members/new",
                                                    "/members/login/**","/members/nickcheck",
                                                    "/item/view/**","/images/**","/image/**").permitAll()
                                    .requestMatchers("/admin/**").hasRole("ADMIN") // 관리자만 접속가능
                                            .anyRequest().authenticated());

        http.csrf((csrf)->csrf.ignoringRequestMatchers("/email/**","/members/nickcheck"));

        http.exceptionHandling((exceptionHandling)->
                exceptionHandling.authenticationEntryPoint(new CustomAuthenticationEntryPoint()));
        //인증되지 않은 사용자가 리소스접근시 수행되는 핸들러

        return http.build();
    }

    @Bean
    public WebSecurityCustomizer webSecurityCustomizer(){ // static 디렉터리의 하위파일들은 인증x
        return ((web)->web.ignoring().requestMatchers("/css/**","/js/**","/img/**"));
    }

    @Bean
    public PasswordEncoder passwordEncoder(){
        return new BCryptPasswordEncoder(); // 패스워드를 암호화하여 DB에 저장한다.
    }
}
