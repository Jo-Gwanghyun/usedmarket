package com.usedmarket.entity;

import com.usedmarket.constant.Role;
import jakarta.persistence.*;
import lombok.*;


@Entity
@Table(name = "member")
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Getter
public class Member extends BaseTimeEntity{
    @Id
    @Column(name = "member_id")
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    private String memberName;

    @Column(unique = true)
    private String email;

    @Column(unique = true)
    private String nickname;

    private String password;

    private String address;

    @Enumerated(EnumType.STRING)
    private Role role;

    @Builder
    public Member(String memberName, String nickname, String email, String password, String address, Role role) {
        this.memberName = memberName;
        this.nickname = nickname;
        this.email = email;
        this.password = password;
        this.address = address;
        this.role = role;
    }

    public void update(String memberName, String nickname, String password, String address){
        this.memberName = memberName;
        this.nickname = nickname;
        this.password = password;
        this.address = address;
    }

}
