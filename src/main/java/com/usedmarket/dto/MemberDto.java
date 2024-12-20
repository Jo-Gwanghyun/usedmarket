package com.usedmarket.dto;

import com.usedmarket.constant.Role;
import com.usedmarket.entity.Member;
import jakarta.validation.constraints.AssertTrue;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotEmpty;
import lombok.Getter;
import lombok.Setter;
import org.hibernate.validator.constraints.Length;

@Getter
@Setter
public class MemberDto {
    @NotBlank(message = "이름은 필수입니다.")
    private String memberName;

    @NotBlank(message = "닉네임은 필수입니다.")
    private String nickname;

    @NotBlank(message = "이메일은 필수입니다.")
    @Email(message = "이메일형식으로 입력하셔야합니다.")
    private String email;

    @NotBlank(message = "인증번호를 입력하세요.")
    private String authNum;

    @AssertTrue(message = "인증번호를 다시확인하세요")
    private boolean authNumCheck;

    @NotEmpty(message = "비밀번호는 필수입니다.")
    @Length(min = 8, max = 16, message = "비밀번호는 최소8자 최대16자로 입력하셔야합니다.")
    private String password;

    @NotEmpty(message = "패스워드를 확인해주세요.")
    @Length(min = 8, max = 16)
    private String passwordCheck;

    @NotBlank(message = "주소는 필수입니다.")
    private String address;

    private Role role;

    public Member toEntity(){
        return Member.builder().memberName(memberName).nickname(nickname)
                .email(email).password(password).address(address).role(role).build();
    }
}
