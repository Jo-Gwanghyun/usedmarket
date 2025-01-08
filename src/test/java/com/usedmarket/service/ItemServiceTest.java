package com.usedmarket.service;

import com.usedmarket.constant.ItemStatus;
import com.usedmarket.dto.ItemDto;
import com.usedmarket.entity.Item;
import com.usedmarket.entity.ItemImage;
import com.usedmarket.repository.ItemImageRepository;
import com.usedmarket.repository.ItemRepository;
import jakarta.persistence.EntityNotFoundException;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.mock.web.MockMultipartFile;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
@Transactional
public class ItemServiceTest {

    @Autowired
    ItemService itemService;

    @Autowired
    ItemRepository itemRepository;

    @Autowired
    ItemImageRepository itemImageRepository;

    //MockMultipartFile로 가상의 파일을 만듬
    List<MultipartFile> createMultipartFiles() throws Exception{

        List<MultipartFile> multipartFileList = new ArrayList<>();

        for(int i=0;i<6;i++){
            String path = "C:/marketItemImage";
            String imageName = "test"+i+".jpg";
            MockMultipartFile multipartFile = new MockMultipartFile(path,imageName,"jpg",new byte[]{1,2,3});
            multipartFileList.add(multipartFile);
        }
        return multipartFileList;
    }


    @Test
    @DisplayName("상품등록")
    @WithMockUser(username = "mhc0404@naver.com",roles = "ADMIN")
    void saveItemOnlyImage() throws Exception{
        ItemDto itemDto = new ItemDto();
        itemDto.setItemName("상품1");
        itemDto.setItemDetail("상품설명1");
        itemDto.setItemPrice(10000);

        List<MultipartFile> multipartFileList = createMultipartFiles();
        MockMultipartFile multipartFile = new MockMultipartFile("C:/marketItemImage","testImage.jpg","jpg",new byte[]{0});

        Long itemId = itemService.addItem(itemDto,multipartFile,multipartFileList);

        List<ItemImage> itemImageList = itemImageRepository.findByItemIdOrderByIdAsc(itemId);

        Item item = itemRepository.findById(itemId).orElseThrow(EntityNotFoundException::new);

        assertEquals(itemDto.getItemName(), item.getItemName());
        assertEquals(itemDto.getItemDetail(), item.getItemDetail());
        assertEquals(itemDto.getItemPrice(), item.getItemPrice());
        assertEquals(itemDto.getItemStatus(), item.getItemStatus());
        assertEquals(multipartFileList.get(0).getOriginalFilename(), itemImageList.get(1).getOriginImageName());
        assertEquals(multipartFileList.get(1).getOriginalFilename(), itemImageList.get(2).getOriginImageName());
        assertEquals(multipartFileList.get(2).getOriginalFilename(), itemImageList.get(3).getOriginImageName());
    }

}
