package com.usedmarket.dto;

import com.querydsl.core.annotations.QueryProjection;
import com.usedmarket.constant.ItemStatus;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;

@Getter
@Setter
public class ItemSellListDto {

    private Long id;

    private String itemName;

    private ItemStatus itemStatus;

    private LocalDateTime updateTime;

    @QueryProjection
    public ItemSellListDto(Long id, String itemName, ItemStatus itemStatus, LocalDateTime updateTime){
        this.id = id;
        this.itemName = itemName;
        this.itemStatus = itemStatus;
        this.updateTime = updateTime;
    }
}
