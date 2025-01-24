package com.usedmarket.service;

import com.usedmarket.constant.ItemStatus;
import com.usedmarket.entity.*;
import com.usedmarket.repository.*;
import jakarta.persistence.EntityNotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@Transactional
@RequiredArgsConstructor
public class MemberDeleteService {
    private final ItemRepository itemRepository;
    private final ItemImageRepository itemImageRepository;
    private final ItemImageService itemImageService;
    private final WishlistItemRepository wishlistItemRepository;
    private final WishlistRepository wishlistRepository;
    private final WishlistService wishlistService;

    public void soldOutStateDelete(Long memberId){
        List<Item> items = itemRepository.findByMemberId(memberId);
        Long itemId;

        if(items != null) {
            for (Item item : items) {
                if (item.getItemStatus().equals(ItemStatus.SOLD_OUT)) {
                    itemId = item.getId();
                    soldOutImageDelete(itemId);
                    itemRepository.deleteById(item.getId());

                }
            }
        }
    }

    public void soldOutImageDelete(Long itemId){
        List<ItemImage> itemImages = itemImageRepository.findByItemIdOrderByIdAsc(itemId);
        for (ItemImage itemImage : itemImages) {
            itemImageService.deleteItemImage(itemImage.getId());
        }
    }

    public void wishlistDelete(Long memberId){
        Wishlist wishlist = wishlistRepository.findByMemberId(memberId);

        if(wishlist!=null) {
            List<WishlistItem> wishlistItems = wishlistItemRepository.findByWishlistId(wishlist.getId());

            if (wishlistItems != null) {
                for (WishlistItem wishlistItem : wishlistItems) {
                    wishlistService.deleteWishlist(wishlistItem.getId());
                }
            }

            wishlistRepository.deleteById(wishlist.getId());
        }
    }

    public boolean findByItem(Long memberId){
        List<Item> items = itemRepository.findByMemberId(memberId);
        return items.isEmpty();
    }
}
