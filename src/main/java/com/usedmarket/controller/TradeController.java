package com.usedmarket.controller;

import com.usedmarket.constant.TradeStatus;
import com.usedmarket.dto.TradeDto;
import com.usedmarket.service.TradeService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.security.Principal;
import java.util.List;

@Controller
@RequiredArgsConstructor
@RequestMapping("/trade")
public class TradeController {
    private final TradeService tradeService;

    @ResponseBody
    @PostMapping("/add")
    public ResponseEntity<String> addTrade(@RequestBody TradeDto tradeDto) {
        tradeService.addTrade(tradeDto);
        return ResponseEntity.ok(tradeDto.getTradeStatus().toString());
    }

    @GetMapping("/view")
    public String trade(Principal principal, Model model) {

        String nickname = tradeService.getNickname(principal.getName());
        List<TradeDto> tradeDtoList = tradeService.findByTrades(nickname);

        model.addAttribute("nickname", nickname);
        model.addAttribute("tradeDtoList", tradeDtoList);

        return "trade/tradePage";
    }

    @ResponseBody
    @PostMapping("/confirm")
    public ResponseEntity<String> tradeConfirm(@RequestBody TradeDto tradeDto) {
        return ResponseEntity.ok(tradeService.tradeConfirm(tradeDto));
    }

    @ResponseBody
    @PostMapping("/status")
    public ResponseEntity<String> tradeStatus(@RequestBody TradeDto tradeDto) {

        String result = tradeService.tradeStatus(tradeDto);

        if(!tradeDto.getTradeStatus().equals(TradeStatus.WAIT)){
            tradeService.tradeComplete(tradeDto);
        }

        tradeService.soldOutCancel(tradeDto.getItemId());
        return ResponseEntity.ok(result);
    }

    @ResponseBody
    @PostMapping("/before")
    public void tradeBefore(@RequestBody TradeDto tradeDto){
        tradeService.tradeBefore(tradeDto);
    }

    @ResponseBody
    @PostMapping("/delete")
    public void tradeDelete(@RequestBody TradeDto tradeDto){
        tradeService.tradeDelete(tradeDto);
    }
}
