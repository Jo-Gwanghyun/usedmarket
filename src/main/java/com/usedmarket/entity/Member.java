package com.usedmarket.entity;

import com.usedmarket.constant.Role;
import jakarta.persistence.*;
import lombok.*;


@Entity
@Table(name = "member")
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor
@Builder
@Getter
@Setter
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
}
