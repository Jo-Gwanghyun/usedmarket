package com.usedmarket.dto;

import com.querydsl.core.types.dsl.*;

import com.querydsl.core.types.ConstructorExpression;
import javax.annotation.processing.Generated;

/**
 * com.usedmarket.dto.QMainPageDto is a Querydsl Projection type for MainPageDto
 */
@Generated("com.querydsl.codegen.DefaultProjectionSerializer")
public class QMainPageDto extends ConstructorExpression<MainPageDto> {

    private static final long serialVersionUID = -1382236672L;

    public QMainPageDto(com.querydsl.core.types.Expression<Long> id, com.querydsl.core.types.Expression<String> itemName, com.querydsl.core.types.Expression<String> itemDetail, com.querydsl.core.types.Expression<String> imageUrl, com.querydsl.core.types.Expression<com.usedmarket.constant.ItemStatus> itemStatus, com.querydsl.core.types.Expression<Integer> itemPrice, com.querydsl.core.types.Expression<String> createdBy, com.querydsl.core.types.Expression<java.time.LocalDateTime> updateTime) {
        super(MainPageDto.class, new Class<?>[]{long.class, String.class, String.class, String.class, com.usedmarket.constant.ItemStatus.class, int.class, String.class, java.time.LocalDateTime.class}, id, itemName, itemDetail, imageUrl, itemStatus, itemPrice, createdBy, updateTime);
    }

}

