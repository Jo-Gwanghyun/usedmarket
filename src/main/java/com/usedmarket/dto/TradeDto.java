package com.usedmarket.dto;

import com.usedmarket.constant.ItemStatus;
import com.usedmarket.constant.TradeStatus;
import com.usedmarket.constant.TradeStyle;
import com.usedmarket.entity.Trade;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;
import org.modelmapper.ModelMapper;

import java.time.LocalDateTime;

@Getter
@Setter
@ToString
public class TradeDto {

    private Long id;

    private Long itemId;

    private String itemName;

    private String createdBy;

    private String requester;

    private TradeStatus tradeStatus;

    private TradeStyle tradeStyle;

    private TradeStatus createdByStatus;

    private TradeStatus requesterStatus;

    private LocalDateTime updateTime;

    private static ModelMapper modelMapper = new ModelMapper();

    public Trade toEntity(){
        return Trade.builder().id(id).itemId(itemId).itemName(itemName).createdBy(createdBy).requester(requester)
                .tradeStatus(tradeStatus).tradeStyle(tradeStyle).createdByStatus(createdByStatus).
                requesterStatus(requesterStatus).build();
    }

    public static TradeDto of(Trade trade){
        return modelMapper.map(trade, TradeDto.class);
    }
}
