package com.usedmarket.dto;

import com.querydsl.core.annotations.QueryProjection;
import com.usedmarket.constant.ItemStatus;
import com.usedmarket.entity.Item;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;

@Getter
@Setter
public class MainPageDto {

    private Long id;

    private String itemName;

    private String itemDetail;

    private String imageUrl;

    private ItemStatus itemStatus;

    private String frontImage;

    private int itemPrice;

    private String createdBy;

    private LocalDateTime updateTime;

    @QueryProjection
    public MainPageDto(Long id, String itemName, String itemDetail, String imageUrl, ItemStatus itemStatus, int itemPrice, String createdBy, LocalDateTime updateTime){
        this.id = id;
        this.itemName = itemName;
        this.itemDetail = itemDetail;
        this.imageUrl = imageUrl;
        this.itemStatus = itemStatus;
        this.itemPrice = itemPrice;
        this.createdBy = createdBy;
        this.updateTime = updateTime;
    }
}
