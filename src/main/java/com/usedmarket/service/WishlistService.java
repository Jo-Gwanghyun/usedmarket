package com.usedmarket.service;

import com.usedmarket.dto.WishlistDto;
import com.usedmarket.dto.WishlistItemDto;
import com.usedmarket.dto.WishlistPageDto;
import com.usedmarket.entity.Item;
import com.usedmarket.entity.Member;
import com.usedmarket.entity.Wishlist;
import com.usedmarket.entity.WishlistItem;
import com.usedmarket.repository.ItemRepository;
import com.usedmarket.repository.MemberRepository;
import com.usedmarket.repository.WishlistItemRepository;
import com.usedmarket.repository.WishlistRepository;
import jakarta.persistence.EntityNotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class WishlistService {

    private final ItemRepository itemRepository;
    private final MemberRepository memberRepository;
    private final WishlistRepository wishlistRepository;
    private final WishlistItemRepository wishlistItemRepository;

    public void addWishlist(WishlistItemDto wishlistItemDto, String email){
        Item item = itemRepository.findById(wishlistItemDto.getItemId()).orElseThrow(EntityNotFoundException::new);
        //관심있는 상품에 추가할 상품을 조회

        Member member = memberRepository.findByEmail(email);
        System.out.println(member.getId());

        Wishlist wishlist = wishlistRepository.findByMemberId(member.getId()); // 현재 로그인한 사용자의 관심상품조회

        if(wishlist == null){ //처음으로 관심상품에 추가하는경우 엔티티를 새로 생성함
            WishlistDto wishlistDto = new WishlistDto();
            wishlistDto.setMember(member);

            wishlist = wishlistDto.toEntity();

            wishlistItemDto.setWishlist(wishlist);
            wishlistRepository.save(wishlist);
        } else {
            wishlistItemDto.setWishlist(wishlist);
        }

        WishlistItem checkWishlistItem = wishlistItemRepository.findByWishlistIdAndItemId(wishlist.getId(), item.getId());
        //위시리스트에 해당상품 존재여부 확인

        if(checkWishlistItem != null){
            throw new IllegalStateException("이미 상품이 존재합니다."); //해당삼품이 존재할경우 클라이언트측에 알림
        }

        wishlistItemDto.setItem(item);

        WishlistItem wishlistItem = wishlistItemDto.toEntity();
        wishlistItemRepository.save(wishlistItem);
    }

    @Transactional(readOnly = true)
    public List<WishlistPageDto> getWishlist(String email){

        List<WishlistPageDto> wishlistPageDtoList = new ArrayList<>();

        Member member = memberRepository.findByEmail(email);
        Wishlist wishlist = wishlistRepository.findByMemberId(member.getId());

        if(wishlist == null){
            return wishlistPageDtoList;
        }

        wishlistPageDtoList = wishlistItemRepository.findWishlistPageDtoList(wishlist.getId());

        return wishlistPageDtoList;
    }

    @Transactional(readOnly = true)
    public Long findByItemId(Long itemId){
        WishlistItem wishlistItem = wishlistItemRepository.findByItemId(itemId);

        if(wishlistItem != null){
            return wishlistItem.getId();
        } else {
            return null;
        }

    }

    public void deleteWishlist(Long wishlistItemId){
        WishlistItem wishlistItem = wishlistItemRepository.findById(wishlistItemId).orElseThrow(EntityNotFoundException::new);
        wishlistItemRepository.delete(wishlistItem);
    }
}
