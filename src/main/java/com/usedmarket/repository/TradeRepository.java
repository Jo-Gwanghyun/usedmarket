package com.usedmarket.repository;

import com.usedmarket.entity.Trade;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface TradeRepository extends JpaRepository<Trade, Long> {
    List<Trade> findByCreatedByOrRequester(String createdBy, String requester);

    List<Trade> findByItemId(Long itemId);

    Trade findByItemIdAndRequester(Long itemId, String requester);
}
