package com.usedmarket.service;

import com.usedmarket.config.RedisConfig;
import com.usedmarket.dto.EmailCheckDto;
import com.usedmarket.repository.MemberRepository;
import jakarta.mail.MessagingException;
import jakarta.mail.internet.MimeMessage;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.redis.core.ValueOperations;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.JavaMailSenderImpl;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Objects;
import java.util.Random;
import java.util.concurrent.TimeUnit;

@Service
@RequiredArgsConstructor
@Transactional
public class EmailSendService {
    private final MemberRepository memberRepository;

    @Autowired
    private JavaMailSender javaMailSender;

    @Autowired
    private RedisConfig redisConfig;

    private int authNum;

    @Value("${spring.mail.username}")
    private String serviceName;

    @Autowired
    private JavaMailSenderImpl mailSender;

    public boolean checkMail(String email){
        return memberRepository.existsByEmail(email);
    }

    void makeAuthNum(){
        Random random = new Random();
        String randomNum = "";

        for (int i=0;i<6;i++){
            randomNum += Integer.toString(random.nextInt(10));
        }

        authNum = Integer.parseInt(randomNum);
    }

    void mainSend(String setForm, String toEmail, String title, String content){
        MimeMessage message = mailSender.createMimeMessage();

        try {
            MimeMessageHelper messageHelper = new MimeMessageHelper(message, true, "UTF-8");
            messageHelper.setFrom(setForm);
            messageHelper.setTo(toEmail);
            messageHelper.setSubject(title);
            messageHelper.setText(content,true);

            mailSender.send(message);
        } catch (MessagingException e){
            e.printStackTrace();
        }

        ValueOperations<String,String> valueOperations = redisConfig.redisTemplate().opsForValue();
        valueOperations.set(toEmail,Integer.toString(authNum),180, TimeUnit.SECONDS);
    }

    public String joinEmail(String email){
        makeAuthNum();
        String title = "인증번호입니다. 확인해주세요.";
        String content = "인증번호는 " + authNum + " 입니다.";

        mainSend(serviceName, email, title, content);
        return Integer.toString(authNum);
    }

    public Boolean checkAuthNum(String email, String authNum){
        ValueOperations<String, String> valueOperations = redisConfig.redisTemplate().opsForValue();
        String code = valueOperations.get(email);

        return Objects.equals(code,authNum);
    }

    public void passSend(EmailCheckDto emailCheckDto){

        MimeMessage message = mailSender.createMimeMessage();
        String content = "새 패스워드는 " + emailCheckDto.getAuthNum() + "입니다.";
        try{
            MimeMessageHelper messageHelper = new MimeMessageHelper(message,true,"UTF-8");
            messageHelper.setFrom(serviceName);
            messageHelper.setTo(emailCheckDto.getEmail());
            messageHelper.setSubject("새 패스워드");
            messageHelper.setText(content,true);

            mailSender.send(message);
        }catch (MessagingException e){
            e.printStackTrace();
        }

    }
}
