package com.usedmarket.dto;

import com.usedmarket.entity.Item;
import com.usedmarket.entity.Wishlist;
import com.usedmarket.entity.WishlistItem;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class WishlistItemDto {

    private Long id;

    private Wishlist wishlist;

    private Item item;

    private Long itemId;

    public WishlistItem toEntity(){
        return WishlistItem.builder().wishlist(wishlist).item(item).build();
    }
}
