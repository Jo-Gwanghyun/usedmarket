package com.usedmarket.service;

import com.usedmarket.constant.ItemStatus;
import com.usedmarket.constant.TradeStatus;
import com.usedmarket.constant.TradeStyle;
import com.usedmarket.dto.ItemDto;
import com.usedmarket.dto.TradeDto;
import com.usedmarket.entity.Item;
import com.usedmarket.entity.Trade;
import com.usedmarket.repository.ItemRepository;
import com.usedmarket.repository.TradeRepository;
import jakarta.persistence.EntityNotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;

@Service
@Transactional
@RequiredArgsConstructor
public class TradeService {
    private final TradeRepository tradeRepository;
    private final ItemRepository itemRepository;
    private final ItemService itemService;

    public void addTrade(TradeDto tradeDto) {

        tradeDto.setTradeStatus(TradeStatus.WAIT);
        tradeDto.setCreatedByStatus(TradeStatus.WAIT);
        tradeDto.setRequesterStatus(TradeStatus.WAIT);
        Trade trade = tradeDto.toEntity();
        tradeRepository.save(trade);
    }

    @Transactional(readOnly = true)
    public String getNickname(String email){
        return itemService.getNickname(email);
    }

    @Transactional(readOnly = true)
    public List<TradeDto> findByTrades(String nickname) {


        List<Trade> tradeList = tradeRepository.findByCreatedByOrRequester(nickname,nickname);
        List<TradeDto> tradeDtoList = new ArrayList<>();

        for (Trade trade : tradeList) {
            TradeDto tradeDto = TradeDto.of(trade);
            tradeDtoList.add(tradeDto);
        }

        return tradeDtoList;

    }

    public String tradeConfirm(TradeDto tradeDto) {
        Long tradeId = tradeDto.getId();

        itemService.updateItemStatus(tradeDto.getItemId(), ItemStatus.TRADING);

        Trade trade = tradeRepository.findById(tradeId).orElseThrow(EntityNotFoundException::new);

        if(tradeDto.getTradeStatus().equals(TradeStatus.TRADING)){

            tradeDto = TradeDto.of(trade);
            tradeDto.setTradeStatus(TradeStatus.TRADING);

            trade.update(tradeDto);

            return "승인완료";
        } else {
            tradeRepository.deleteById(trade.getId());

            return "거절완료";
        }

    }

    public String tradeStatus(TradeDto tradeDto) {
        Long tradeId = tradeDto.getId();

        Trade trade = tradeRepository.findById(tradeId).orElseThrow(EntityNotFoundException::new);

            if(tradeDto.getTradeStatus().equals(TradeStatus.WAIT)){
                tradeRepository.deleteById(trade.getId());
                return "취소완료";
            }


        if(tradeDto.getCreatedByStatus().equals(TradeStatus.COMPLETE) && tradeDto.getCreatedBy()!=null){
            tradeDto = TradeDto.of(trade);
            tradeDto.setCreatedByStatus(TradeStatus.COMPLETE);
            trade.update(tradeDto);

            return "구매확정완료";

        } else if(tradeDto.getCreatedByStatus().equals(TradeStatus.CANCEL) && tradeDto.getCreatedBy()!=null){
            tradeDto = TradeDto.of(trade);
            tradeDto.setCreatedByStatus(TradeStatus.CANCEL);
            trade.update(tradeDto);

            return "거래취소완료";

        } else if(tradeDto.getRequesterStatus().equals(TradeStatus.COMPLETE) && tradeDto.getRequester()!=null){
            tradeDto = TradeDto.of(trade);
            tradeDto.setRequesterStatus(TradeStatus.COMPLETE);
            trade.update(tradeDto);

            return "구매확정완료";

        } else{
            tradeDto = TradeDto.of(trade);
            tradeDto.setRequesterStatus(TradeStatus.CANCEL);
            trade.update(tradeDto);

            return "거래취소완료";
        }
    }

    public void tradeComplete(TradeDto tradeDto) {

        Long tradeId = tradeDto.getId();

        Trade trade = tradeRepository.findById(tradeId).orElseThrow(EntityNotFoundException::new);



        if(tradeDto.getCreatedByStatus().equals(TradeStatus.COMPLETE) &&
            tradeDto.getRequesterStatus().equals(TradeStatus.COMPLETE)) {
            tradeDto = TradeDto.of(trade);
            tradeDto.setTradeStatus(TradeStatus.COMPLETE);
            trade.update(tradeDto);

            itemService.updateItemStatus(tradeDto.getItemId(), ItemStatus.SOLD_OUT);

        } else if(tradeDto.getCreatedByStatus().equals(TradeStatus.CANCEL) &&
                    tradeDto.getRequesterStatus().equals(TradeStatus.CANCEL)) {
            tradeDto = TradeDto.of(trade);
            tradeDto.setTradeStatus(TradeStatus.CANCEL);
            trade.update(tradeDto);
        }
    }

    public void soldOutCancel(Long itemId) {

        Item item = itemRepository.findById(itemId).orElseThrow(EntityNotFoundException::new);

        List<Trade> soldOutList = tradeRepository.findByItemId(itemId);

        if(item.getItemStatus().equals(ItemStatus.SOLD_OUT)){
            for(Trade trade : soldOutList){
                if(!trade.getTradeStatus().equals(TradeStatus.COMPLETE)){
                    TradeDto tradeDto = TradeDto.of(trade);
                    tradeDto.setTradeStatus(TradeStatus.CANCEL);
                    trade.update(tradeDto);
                }
            }
        }
    }

    public void tradeBefore(TradeDto tradeDto){
        Trade trade = tradeRepository.findById(tradeDto.getId()).orElseThrow(EntityNotFoundException::new);

        Item item = itemRepository.findById(tradeDto.getItemId()).orElseThrow(EntityNotFoundException::new);

        if(trade.getTradeStatus().equals(TradeStatus.CANCEL)){
            if(trade.getTradeStyle().equals(TradeStyle.SELL)){
                itemService.updateItemStatus(item.getId(),ItemStatus.SELL);
                tradeRepository.deleteById(tradeDto.getId());

            } else if(trade.getTradeStyle().equals(TradeStyle.BUY)){
                itemService.updateItemStatus(item.getId(),ItemStatus.BUY);
                tradeRepository.deleteById(tradeDto.getId());
            }
        }
    }

    public void tradeDelete(TradeDto tradeDto){
        Trade trade = tradeRepository.findById(tradeDto.getId()).orElseThrow(EntityNotFoundException::new);

        if(trade.getTradeStatus().equals(TradeStatus.CANCEL) || trade.getTradeStatus().equals(TradeStatus.COMPLETE)){
            if(tradeDto.getCreatedBy()!=null){
                tradeDto = TradeDto.of(trade);
                tradeDto.setCreatedByStatus(TradeStatus.HIDE);
                trade.update(tradeDto);
            } else {
                tradeDto = TradeDto.of(trade);
                tradeDto.setRequesterStatus(TradeStatus.HIDE);
                trade.update(tradeDto);
            }

            if(trade.getCreatedByStatus().equals(TradeStatus.HIDE) && trade.getRequesterStatus().equals(TradeStatus.HIDE)){
                tradeRepository.deleteById(trade.getId());
            }
        }
    }

}
