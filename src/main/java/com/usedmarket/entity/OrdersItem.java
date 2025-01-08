package com.usedmarket.entity;

import jakarta.persistence.*;
import lombok.*;

@Entity
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor
@Getter
@Builder
public class OrdersItem extends BaseEntity{
    //Orders와 Item의 연결테이블 엔티티
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "orders_item_id")
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "order_id")
    private Orders orders;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "item_id")
    private Item item;

    private int itemPrice;
}
