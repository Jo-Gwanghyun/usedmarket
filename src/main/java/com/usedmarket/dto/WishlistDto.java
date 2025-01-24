package com.usedmarket.dto;

import com.usedmarket.entity.Member;
import com.usedmarket.entity.Wishlist;
import lombok.Getter;
import lombok.Setter;

@Setter
@Getter
public class WishlistDto {
    private Member member;

    public Wishlist toEntity(){
        return Wishlist.builder().member(member).build();
    }
}
