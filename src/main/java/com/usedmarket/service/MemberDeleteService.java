package com.usedmarket.service;

import com.usedmarket.constant.ItemStatus;
import com.usedmarket.entity.Item;
import com.usedmarket.entity.ItemImage;
import com.usedmarket.repository.ItemImageRepository;
import com.usedmarket.repository.ItemRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@Transactional
@RequiredArgsConstructor
public class MemberDeleteService {
    private final ItemRepository itemRepository;
    private final ItemImageRepository itemImageRepository;
    private final ItemImageService itemImageService;

    public void soldOutStateDelete(Long memberId){
        List<Item> items = itemRepository.findByMemberId(memberId);
        Long itemId;

        for (Item item : items) {
            if (item.getItemStatus().equals(ItemStatus.SOLD_OUT)) {
                itemId = item.getId();
                soldOutImageDelete(itemId);
                itemRepository.deleteById(item.getId());

            }
        }
    }

    public void soldOutImageDelete(Long itemId){
        List<ItemImage> itemImages = itemImageRepository.findByItemIdOrderByIdAsc(itemId);
        for (ItemImage itemImage : itemImages) {
            Long imageId = itemImage.getId();
            itemImageService.deleteItemImage(imageId);
        }
    }

    public boolean findByItem(Long memberId){
        List<Item> items = itemRepository.findByMemberId(memberId);
        return items.isEmpty();
    }
}
