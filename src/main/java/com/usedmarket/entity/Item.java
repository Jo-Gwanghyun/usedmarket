package com.usedmarket.entity;

import com.usedmarket.constant.ItemStatus;
import com.usedmarket.dto.ItemDto;
import jakarta.persistence.*;
import lombok.*;

@Entity
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@ToString
public class Item extends BaseEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "item_id")
    private Long id;

    private String itemName;

    private String itemDetail;

    private int itemPrice;

    private String seller;

    @Enumerated(EnumType.STRING)
    private ItemStatus itemStatus;

    @Builder
    public Item(Long id,String itemName, String itemDetail, int itemPrice, ItemStatus itemStatus, String seller){
        this.id = id;
        this.itemName = itemName;
        this.itemDetail = itemDetail;
        this.itemPrice = itemPrice;
        this.itemStatus = itemStatus;
        this.seller = seller;
    }

    public void update(ItemDto itemDto){
        this.itemName = itemDto.getItemName();
        this.itemDetail = itemDto.getItemDetail();
        this.itemPrice = itemDto.getItemPrice();
        this.itemStatus = itemDto.getItemStatus();
    }
}
