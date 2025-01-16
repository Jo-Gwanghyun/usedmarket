package com.usedmarket.dto;

import com.querydsl.core.types.dsl.*;

import com.querydsl.core.types.ConstructorExpression;
import javax.annotation.processing.Generated;

/**
 * com.usedmarket.dto.QItemSellListDto is a Querydsl Projection type for ItemSellListDto
 */
@Generated("com.querydsl.codegen.DefaultProjectionSerializer")
public class QItemSellListDto extends ConstructorExpression<ItemSellListDto> {

    private static final long serialVersionUID = 1887780933L;

    public QItemSellListDto(com.querydsl.core.types.Expression<Long> id, com.querydsl.core.types.Expression<String> itemName, com.querydsl.core.types.Expression<com.usedmarket.constant.ItemStatus> itemStatus, com.querydsl.core.types.Expression<java.time.LocalDateTime> updateTime) {
        super(ItemSellListDto.class, new Class<?>[]{long.class, String.class, com.usedmarket.constant.ItemStatus.class, java.time.LocalDateTime.class}, id, itemName, itemStatus, updateTime);
    }

}

