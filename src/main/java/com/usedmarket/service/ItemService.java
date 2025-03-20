package com.usedmarket.service;

import com.usedmarket.constant.ItemStatus;
import com.usedmarket.constant.TradeStyle;
import com.usedmarket.dto.*;
import com.usedmarket.entity.*;
import com.usedmarket.repository.ItemImageRepository;
import com.usedmarket.repository.ItemRepository;
import com.usedmarket.repository.MemberRepository;
import com.usedmarket.repository.TradeRepository;
import jakarta.persistence.EntityNotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import java.util.ArrayList;
import java.util.List;

@Service
@Transactional
@RequiredArgsConstructor
public class ItemService {

    private final ItemRepository itemRepository;
    private final ItemImageService itemImageService;
    private final ItemImageRepository itemImageRepository;
    private final MemberRepository memberRepository;
    private final WishlistService wishlistService;
    private final TradeRepository tradeRepository;

    public void addItem(ItemDto itemDto, String createdBy, MultipartFile addThumbnailImage, List<MultipartFile> addItemImage) throws Exception {

        long fileSize = 0;

        for(MultipartFile file : addItemImage){
            fileSize += file.getSize();
        }

        if(itemDto.getItemStatus().equals(ItemStatus.SELL)){
            itemDto.setItemStatus(ItemStatus.SELL);
            itemDto.setTradeStyle(TradeStyle.SELL);
        } else {
            itemDto.setItemStatus(ItemStatus.BUY);
            itemDto.setTradeStyle(TradeStyle.BUY);
        }


        Member member = memberRepository.findByEmail(createdBy);
        itemDto.setMember(member);

        Item item = itemDto.toEntity();
        itemRepository.save(item);


        ItemImageDto itemImageDto = new ItemImageDto();
        itemImageDto.setItem(item);


        itemImageDto.setThumbnailImage("Y");
        itemImageDto.setItem(item);
        itemImageService.saveItemImage(itemImageDto,addThumbnailImage);


        if(fileSize > 0){
            for(int i = 0; i < addItemImage.size(); i++){
                itemImageDto.setThumbnailImage("N");
                itemImageDto.setItem(item);
                itemImageService.saveItemImage(itemImageDto, addItemImage.get(i));
            }
        }

    }

    @Transactional(readOnly = true)
    public Page<MainPageDto> getMainPage(ItemSearchDto itemSearchDto, Pageable pageable){
        return itemRepository.getMainPage(itemSearchDto,pageable);
    }

    @Transactional(readOnly = true)
    public Page<ItemSellListDto> getSellListPage(ItemSearchDto itemSearchDto, String createdBy, Pageable pageable){
        return itemRepository.getSellListPage(itemSearchDto,createdBy,pageable);
    }

    @Transactional(readOnly = true)
    public ItemDto getItemPage(Long itemId){

        List<ItemImage> itemImageList = itemImageRepository.findByItemIdOrderByIdAsc(itemId);
        List<ItemImageDto> itemImageDtoList = new ArrayList<>();

        for(ItemImage itemImage : itemImageList){
            ItemImageDto itemImageDto = ItemImageDto.of(itemImage);
            itemImageDtoList.add(itemImageDto);
        }

        Item item = itemRepository.findById(itemId).orElseThrow(EntityNotFoundException::new);
        ItemDto itemDto = ItemDto.of(item);
        itemDto.setItemImageDtoList(itemImageDtoList);

        return itemDto;
    }

    public boolean sellerCheck(Long itemId,String principal){
        Item item = itemRepository.findById(itemId).orElseThrow(EntityNotFoundException::new);
        String createdBy = item.getCreatedBy();

        return principal.equals(createdBy);
    }

    public Long updateItem(ItemDto itemDto, MultipartFile thumbnailImage, List<MultipartFile> updateItemImageList, List<Long> deleteFileId)
            throws Exception {

        Item item = itemRepository.findById(itemDto.getId()).orElseThrow(EntityNotFoundException::new);
        itemDto.setItemStatus(item.getItemStatus());
        itemDto.setTradeStyle(item.getTradeStyle());
        item.update(itemDto);

        long fileSize = 0;

        for(MultipartFile file : updateItemImageList){
            fileSize += file.getSize();
        }

        if(thumbnailImage.getSize() > 0) {
            itemImageService.updateThumbnail(itemDto.getId(),thumbnailImage);
        }

        if(deleteFileId!=null){
            for(int i=0; i<deleteFileId.size();i++){
                itemImageService.deleteItemImage(deleteFileId.get(i));
            }
        }

        if(fileSize > 0){
            for(int i=0; i<updateItemImageList.size(); i++) {
                itemImageService.updateItemImage(itemDto, updateItemImageList.get(i));
            }
        }

        return item.getId();
    }

    @Transactional(readOnly = true)
    public String getNickname(String email){
        return memberRepository.findByEmail(email).getNickname();
    }

    public void deleteItem(Long itemId){

        Long wishlistCheck = wishlistService.findByItemId(itemId);
        if(wishlistCheck != null){
            wishlistService.deleteWishlist(wishlistCheck);
        }

        List<ItemImage> itemImageList = itemImageRepository.findByItemIdOrderByIdAsc(itemId);

        //해당 상품에 이미지파일이 있다면 모든 이미지파일 삭제
        if(itemImageList!=null){
            for(int i=0; i < itemImageList.size(); i++){
                itemImageService.deleteItemImage(itemImageList.get(i).getId());
            }
        }

        itemRepository.deleteById(itemId);
    }

    public void updateItemStatus(Long itemId, ItemStatus itemStatus){
        Item item = itemRepository.findById(itemId).orElseThrow(EntityNotFoundException::new);
        ItemDto itemDto = ItemDto.of(item);
        itemDto.setItemStatus(itemStatus);

        item.update(itemDto);
    }

    @Transactional(readOnly = true)
    public String tradeCheck(String email, Long itemId){
        String nickname = memberRepository.findByEmail(email).getNickname();

        String tradeStatus = "";
        if(tradeRepository.findByItemIdAndRequester(itemId,nickname) != null){
            Trade trade = tradeRepository.findByItemIdAndRequester(itemId,nickname);
            tradeStatus = trade.getRequesterStatus().toString();
        }
        return tradeStatus;
    }
}
