package com.usedmarket.repository;

import com.usedmarket.dto.ItemSearchDto;
import com.usedmarket.dto.ItemSellListDto;
import com.usedmarket.dto.MainPageDto;
import com.usedmarket.entity.Item;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

public interface ItemRepositoryCustom {

    Page<MainPageDto> getMainPage(ItemSearchDto itemSearchDto, Pageable pageable);

    Page<ItemSellListDto> getSellListPage(ItemSearchDto itemSearchDto, String nickname, Pageable pageable);
}
