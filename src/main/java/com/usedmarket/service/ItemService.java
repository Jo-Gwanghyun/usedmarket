package com.usedmarket.service;

import com.usedmarket.constant.ItemStatus;
import com.usedmarket.dto.ItemDto;
import com.usedmarket.dto.ItemImageDto;
import com.usedmarket.dto.ItemSearchDto;
import com.usedmarket.dto.MainPageDto;
import com.usedmarket.entity.Item;
import com.usedmarket.entity.ItemImage;
import com.usedmarket.repository.ItemImageRepository;
import com.usedmarket.repository.ItemRepository;
import jakarta.persistence.EntityExistsException;
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

    public Long addItem(ItemDto itemDto,MultipartFile addThumbnailImage, List<MultipartFile> addItemImage) throws Exception {

        long fileSize = 0;

        for(MultipartFile file : addItemImage){
            fileSize += file.getSize();
        }

        itemDto.setItemStatus(ItemStatus.SELL);

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
    public ItemDto getItemPage(Long itemId){

        List<ItemImage> itemImageList = itemImageRepository.findByItemIdOrderByIdAsc(itemId);
        List<ItemImageDto> itemImageDtoList = new ArrayList<>();

        for(ItemImage itemImage : itemImageList){
            ItemImageDto itemImageDto = ItemImageDto.of(itemImage);
            itemImageDtoList.add(itemImageDto);
        }

        Item item = itemRepository.findById(itemId).orElseThrow(EntityExistsException::new);
        ItemDto itemDto = ItemDto.of(item);
        itemDto.setItemImageDtoList(itemImageDtoList);

        return itemDto;
    }
}