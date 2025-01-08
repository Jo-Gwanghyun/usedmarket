package com.usedmarket.service;

import com.usedmarket.dto.ItemImageDto;
import com.usedmarket.entity.ItemImage;
import com.usedmarket.repository.ItemImageRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;
import org.thymeleaf.util.StringUtils;

@Service
@RequiredArgsConstructor
@Transactional
@Slf4j
public class ItemImageService {

    @Value("${itemImageLocation}")
    private String itemImageLocation;

    private final ItemImageRepository itemImageRepository;

    private final ImageFileService imageFileService;

    public void saveItemImage(ItemImageDto itemImageDto, MultipartFile itemImageFile) throws Exception{

        String originImageName = itemImageFile.getOriginalFilename();
        String imageName = "";
        String imageUrl = "";

        if(itemImageFile.getSize() == 0){
            itemImageDto.setOriginImageName("섬네일없음");
            imageUrl = "/image/noImage.jpg";
        } else {
            imageName = imageFileService.uploadFile(itemImageLocation,originImageName,itemImageFile.getBytes());
            imageUrl = "/images/"+imageName;
            itemImageDto.setOriginImageName(originImageName);
        }


        itemImageDto.setImageName(imageName);
        itemImageDto.setImageUrl(imageUrl);
        log.info("item id정보 = "+itemImageDto.getItem().getId());
        log.info("itemImageDto 정보 = "+itemImageDto.toString());
        ItemImage itemImage = itemImageDto.toEntity();
        itemImageRepository.save(itemImage);
    }

}
