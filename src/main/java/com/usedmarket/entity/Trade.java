package com.usedmarket.entity;

import com.usedmarket.constant.ItemStatus;
import com.usedmarket.constant.TradeStatus;
import com.usedmarket.constant.TradeStyle;
import com.usedmarket.dto.TradeDto;
import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Entity
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class Trade extends BaseTimeEntity{

    @Id
    @Column(name = "trade_id")
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    private Long itemId;

    private String itemName;

    private String createdBy;

    private String requester;

    @Enumerated(EnumType.STRING)
    private TradeStyle tradeStyle;

    @Enumerated(EnumType.STRING)
    private TradeStatus tradeStatus;

    @Enumerated(EnumType.STRING)
    private TradeStatus createdByStatus;

    @Enumerated(EnumType.STRING)
    private TradeStatus requesterStatus;

    @Builder
    public Trade(Long id,Long itemId, String itemName, String createdBy, String requester,
                 TradeStyle tradeStyle,TradeStatus tradeStatus,TradeStatus createdByStatus,TradeStatus requesterStatus) {
        this.id = id;
        this.itemId = itemId;
        this.itemName = itemName;
        this.createdBy = createdBy;
        this.requester = requester;
        this.tradeStyle = tradeStyle;
        this.tradeStatus = tradeStatus;
        this.createdByStatus = createdByStatus;
        this.requesterStatus = requesterStatus;
    }

    public void update(TradeDto tradeDto){
        this.tradeStatus = tradeDto.getTradeStatus();
        this.createdByStatus = tradeDto.getCreatedByStatus();
        this.requesterStatus = tradeDto.getRequesterStatus();
    }
}
