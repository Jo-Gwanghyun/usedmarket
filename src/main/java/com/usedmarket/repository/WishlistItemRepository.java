package com.usedmarket.repository;

import com.usedmarket.dto.WishlistPageDto;
import com.usedmarket.entity.WishlistItem;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface WishlistItemRepository extends JpaRepository<WishlistItem,Long> {

    WishlistItem findByWishlistIdAndItemId(Long wishlistId, Long itemId); //wishlistId와 itemId로 wishlist 내 상품이 존재하는지 조회

    @Query("select new com.usedmarket.dto.WishlistPageDto(wi.id, i.id ,i.itemName, i.itemPrice, im.imageUrl, i.itemStatus ,i.member.nickname , i.updateTime)"
            + "from WishlistItem wi, ItemImage im " + "join wi.item i " + "where wi.wishlist.id = :wishlistId "
            + "and im.item.id = wi.item.id " + "and im.thumbnailImage = 'Y' " + "order by wi.updateTime desc")
    List<WishlistPageDto> findWishlistPageDtoList(Long wishlistId);

    List<WishlistItem> findByWishlistId(Long wishlistId);
}
