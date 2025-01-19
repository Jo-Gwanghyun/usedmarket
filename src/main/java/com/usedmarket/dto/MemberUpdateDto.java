package com.usedmarket.dto;

import com.usedmarket.entity.Member;
import lombok.Getter;
import lombok.Setter;
import org.modelmapper.ModelMapper;

@Getter
@Setter
public class MemberUpdateDto {

    private Long id;

    private String memberName;

    private String nickname;

    private String password;

    private String passwordCheck;

    private String address;

    static ModelMapper modelMapper = new ModelMapper();

    public static MemberUpdateDto of(Member member) {
        return modelMapper.map(member, MemberUpdateDto.class);
    }

}
