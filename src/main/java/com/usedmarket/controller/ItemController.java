package com.usedmarket.controller;

import com.usedmarket.dto.ItemDto;
import com.usedmarket.service.ItemService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

@Controller
@RequiredArgsConstructor
@RequestMapping("/item")
@Slf4j
public class ItemController {

    private final ItemService itemService;

    @GetMapping("/new")
    public String addItem(Model model){
        model.addAttribute("itemDto",new ItemDto());
        return "/item/addItem";
    }

    @PostMapping("/new")
    public String addItem(@Valid ItemDto itemDto, BindingResult bindingResult, Model model,
                          @RequestParam(required = false) MultipartFile addThumbnailImage,
                          @RequestParam(required = false) List<MultipartFile> addItemImage){

        log.info(itemDto.toString());

        if(bindingResult.hasErrors()){
            return "/item/addItem";
        }

            try{
                itemService.addItem(itemDto,addThumbnailImage,addItemImage);
            } catch (Exception e) {
                model.addAttribute("errorMessage","에러가 발생하였습니다.");
                return "/item/addItem";
            }

        return "redirect:/";
    }

    @GetMapping("/{itemId}")
    public String itemPage(@PathVariable("itemId") Long itemId, Model model){
        ItemDto itemDto = itemService.getItemPage(itemId);
        model.addAttribute("itemDto",itemDto);    
        return "/item/itemPage";
    }

}
