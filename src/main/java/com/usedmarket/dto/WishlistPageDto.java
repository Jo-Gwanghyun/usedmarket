package com.usedmarket.dto;

import com.usedmarket.constant.ItemStatus;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;

@Getter
@Setter
public class WishlistPageDto {
    private Long id;

    private Long itemId;

    private String itemName;

    private int itemPrice;

    private String imageUrl;

    private ItemStatus itemStatus;

    private String nickname;

    private LocalDateTime updateTime;

    public WishlistPageDto(Long id, Long itemId ,String itemName, int itemPrice, String imageUrl, ItemStatus itemStatus ,String nickname,
                           LocalDateTime updateTime){
        this.id = id;
        this.itemId = itemId;
        this.itemName = itemName;
        this.itemPrice = itemPrice;
        this.imageUrl = imageUrl;
        this.itemStatus = itemStatus;
        this.nickname = nickname;
        this.updateTime = updateTime;
    }
}
