package com.usedmarket.repository;

import com.usedmarket.entity.Wishlist;
import org.springframework.data.jpa.repository.JpaRepository;

public interface WishlistRepository extends JpaRepository<Wishlist,Long> {
    Wishlist findByMemberId(Long memberId);
}
