package com.usedmarket.controller;

import com.usedmarket.dto.WishlistItemDto;
import com.usedmarket.dto.WishlistPageDto;
import com.usedmarket.service.WishlistService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.security.Principal;
import java.util.List;

@Controller
@RequiredArgsConstructor
@RequestMapping("/wishlist")
public class WishlistController {

    private final WishlistService wishlistService;

    @ResponseBody
    @PostMapping("/add")
    public ResponseEntity<String> addWishlist(@RequestBody @Valid WishlistItemDto wishlistItemDto,
                                              Principal principal){

        String email = principal.getName();
        try{
            wishlistService.addWishlist(wishlistItemDto, email);

        } catch (IllegalStateException e){
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(e.getMessage());
        }

        return ResponseEntity.ok("관심있는상품에 추가완료");
    }

    @GetMapping("/view")
    public String wishlistPage(Model model, Principal principal){

        String email = principal.getName();
        List<WishlistPageDto> wishlistPageDtoList = wishlistService.getWishlist(email);
        model.addAttribute("wishlistPageDtoList",wishlistPageDtoList);

        return "wishlist/wishlistPage";
    }

    @ResponseBody
    @DeleteMapping("/delete")
    public ResponseEntity<String> deleteWishlist(@RequestBody @Valid WishlistItemDto wishlistItemDto){
        wishlistService.deleteWishlist(wishlistItemDto.getId());

        return ResponseEntity.ok("삭제완료");
    }

}
