package com.usedmarket.dto;

import com.querydsl.core.annotations.QueryProjection;
import com.usedmarket.constant.ItemStatus;
import com.usedmarket.entity.Item;
import lombok.Getter;
import lombok.Setter;

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

    @QueryProjection
    public MainPageDto(Long id, String itemName, String itemDetail, String imageUrl, ItemStatus itemStatus, int itemPrice, String createdBy){
        this.id = id;
        this.itemName = itemName;
        this.itemDetail = itemDetail;
        this.imageUrl = imageUrl;
        this.itemStatus = itemStatus;
        this.itemPrice = itemPrice;
        this.createdBy = createdBy;
    }
}
