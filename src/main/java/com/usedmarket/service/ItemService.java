package com.usedmarket.service;

import com.usedmarket.constant.ItemStatus;
import com.usedmarket.dto.*;
import com.usedmarket.entity.Item;
import com.usedmarket.entity.ItemImage;
import com.usedmarket.entity.Member;
import com.usedmarket.repository.ItemImageRepository;
import com.usedmarket.repository.ItemRepository;
import com.usedmarket.repository.MemberRepository;
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

    public Long addItem(ItemDto itemDto,String createdBy, MultipartFile addThumbnailImage, List<MultipartFile> addItemImage) throws Exception {

        long fileSize = 0;

        for(MultipartFile file : addItemImage){
            fileSize += file.getSize();
        }

        itemDto.setItemStatus(ItemStatus.SELL);

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

        return item.getId();
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

    public void deleteItem(Long itemId){
        List<ItemImage> itemImageList = itemImageRepository.findByItemIdOrderByIdAsc(itemId);

        //해당 상품에 이미지파일이 있다면 모든 이미지파일 삭제
        if(itemImageList!=null){
            for(int i=0; i < itemImageList.size(); i++){
                itemImageService.deleteItemImage(itemImageList.get(i).getId());
            }
        }

        itemRepository.deleteById(itemId);
    }
}
