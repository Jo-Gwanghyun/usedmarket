package com.usedmarket.controller;

import com.usedmarket.dto.*;
import com.usedmarket.entity.Item;
import com.usedmarket.entity.ItemImage;
import com.usedmarket.service.ItemImageService;
import com.usedmarket.service.ItemService;
import com.usedmarket.service.MemberService;
import jakarta.persistence.EntityNotFoundException;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.security.Principal;
import java.util.List;
import java.util.Optional;

@Controller
@RequiredArgsConstructor
@RequestMapping("/item")
@Slf4j
public class ItemController {

    private final ItemService itemService;
    private final ItemImageService itemImageService;
    private final MemberService memberService;

    @GetMapping("/new")
    public String addItem(Model model){
        model.addAttribute("itemDto",new ItemDto());
        return "item/addItem";
    }

    @PostMapping("/new")
    public String addItem(@Valid ItemDto itemDto, BindingResult bindingResult, Model model, Principal principal,
                          @RequestParam(required = false) MultipartFile addThumbnailImage,
                          @RequestParam(required = false) List<MultipartFile> addItemImage){

        if(bindingResult.hasErrors()){
            return "item/addItem";
        }

            try{
                String createdBy = principal.getName();
                itemService.addItem(itemDto,createdBy,addThumbnailImage,addItemImage);
            } catch (Exception e) {
                model.addAttribute("errorMessage","에러가 발생하였습니다.");
                return "item/addItem";
            }

        return "redirect:/";
    }

    @GetMapping("/view/{itemId}")
    public String itemPage(@PathVariable("itemId") Long itemId, Principal principal,Model model){

        ItemDto itemDto = itemService.getItemPage(itemId);
        model.addAttribute("itemDto", itemDto);

        if (principal != null) model.addAttribute("sellerCheck", principal.getName());

        return "item/itemPage";
    }

    @GetMapping({"/sellList","/sellList/{page}"})
    public String itemSellList(ItemSearchDto itemSearchDto, @PathVariable("page")Optional<Integer> page,
                               Model model, Principal principal){

        Pageable pageable = PageRequest.of(page.isPresent() ? page.get() : 0,10);

        Page<ItemSellListDto> items = itemService.getSellListPage(itemSearchDto, principal.getName() , pageable);

        model.addAttribute("items",items);
        model.addAttribute("itemSearchDto",itemSearchDto);
        model.addAttribute("maxPage",10);

        return "item/sellItemList";
    }

    @GetMapping("/update/{itemId}")
    public String itemUpdate(@PathVariable("itemId") Long itemId, Principal principal ,Model model){

        //등록자가 아닌사람이 수정페이지에 url로 접근한경우 메인페이지로 리다이렉트
        if (!itemService.sellerCheck(itemId,principal.getName())){
            return "redirect:/";
        }

        ItemDto itemDto = itemService.getItemPage(itemId);
        model.addAttribute("itemDto",itemDto);

        return "item/updateItem";
    }


    @ResponseBody
    @GetMapping("/update/imagelist/{itemId}")
    public List<ItemImageUpdateDto> findByImageList(@RequestBody @PathVariable("itemId") Long itemId){
        return itemImageService.itemImageList(itemId);
    }


    @PostMapping("/update/{itemId}")
    public String itemUpdate(@Valid ItemDto itemDto, BindingResult bindingResult, Model model,
                          @RequestParam(required = false) MultipartFile updateThumbnailImage,
                          @RequestParam(required = false) List<MultipartFile> updateItemImage,
                             @RequestParam(required = false) List<Long> deleteFileId){

        log.info(itemDto.toString());

        if(bindingResult.hasErrors()){
            return "/item/updateItem";
        }

        try{
            itemService.updateItem(itemDto,updateThumbnailImage,updateItemImage,deleteFileId);
        } catch (Exception e) {
            model.addAttribute("errorMessage","에러가 발생하였습니다.");
            return "/item/updateItem";
        }

        return "redirect:/";
    }

    @ResponseBody
    @DeleteMapping("/delete/{itemId}")
    public ResponseEntity<String> itemDelete(@RequestBody @PathVariable("itemId") Long itemId, Principal principal){

        String seller = principal.getName();

        if(!itemService.sellerCheck(itemId,seller)){
            return ResponseEntity.status(HttpStatus.FORBIDDEN).body("삭제권한이 없습니다.");
        }

        itemService.deleteItem(itemId);
        return ResponseEntity.ok().body("삭제 완료");
    }

}
