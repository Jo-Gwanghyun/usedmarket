package com.usedmarket.entity;

import com.usedmarket.constant.ItemStatus;
import jakarta.persistence.*;
import lombok.*;

@Entity
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor
@Builder
public class Item extends BaseEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "item_id")
    private Long id;

    private String itemName;

    private String itemDetail;

    private int itemPrice;

    @Enumerated(EnumType.STRING)
    private ItemStatus itemStatus;
}
