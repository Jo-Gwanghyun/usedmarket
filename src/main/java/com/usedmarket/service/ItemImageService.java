package com.usedmarket.service;

import com.usedmarket.dto.ItemDto;
import com.usedmarket.dto.ItemImageDto;
import com.usedmarket.dto.ItemImageUpdateDto;
import com.usedmarket.entity.ItemImage;
import com.usedmarket.repository.ItemImageRepository;
import jakarta.persistence.EntityNotFoundException;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;
import org.thymeleaf.util.StringUtils;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

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
            itemImageDto.setOriginImageName("대표이미지없음");
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

    @Transactional(readOnly = true)
    public List<ItemImageUpdateDto> itemImageList(Long itemId){
        List<ItemImage> itemImageList =  itemImageRepository.findByItemIdOrderByIdAsc(itemId);
        ItemImage itemImage;

        List<ItemImageUpdateDto> itemImageUpdateDtos = new ArrayList<>();

        for(int i=0; i<itemImageList.size();i++){
            itemImage = itemImageList.get(i);

            ItemImageUpdateDto itemImageDto = ItemImageUpdateDto.of(itemImage);
            itemImageUpdateDtos.add(itemImageDto);
        }

        System.out.println("itemImageDtoList =>"+itemImageUpdateDtos);

        return itemImageUpdateDtos;
    }

    public void updateThumbnail(Long itemImageId, MultipartFile itemImage) throws Exception {

            ItemImage thumbnailImage = itemImageRepository.findByItemIdAndThumbnailImage(itemImageId,"Y");

            if(!thumbnailImage.getOriginImageName().equals("대표이미지없음")) {
                //기존 대표이미지 파일 삭제
                imageFileService.deleteFile(itemImageLocation + "/" + thumbnailImage.getImageName());
            }

                String originImageName = itemImage.getOriginalFilename();

                String imageName = imageFileService.uploadFile(itemImageLocation, originImageName,
                        itemImage.getBytes());

                String imageUrl = "/images/"+imageName;

                //dirty checking 방식을 이용하여 기존 대표이미지 수정
                thumbnailImage.updateItemImage(imageName, originImageName,imageUrl);
    }

    public void updateItemImage(ItemDto itemDto, MultipartFile updateItemImage) throws Exception {

        ItemImageDto itemImageDto = new ItemImageDto();

        String originImageName = updateItemImage.getOriginalFilename();
        String imageName = imageFileService.uploadFile(itemImageLocation, originImageName, updateItemImage.getBytes());
        String imageUrl = "/images/"+imageName;

        itemImageDto.setItem(itemDto.toEntity());
        itemImageDto.setThumbnailImage("N");
        itemImageDto.setOriginImageName(originImageName);
        itemImageDto.setImageName(imageName);
        itemImageDto.setImageUrl(imageUrl);
        ItemImage itemImage = itemImageDto.toEntity();

        itemImageRepository.save(itemImage);

    }

    public void deleteItemImage(Long itemImageId){
        ItemImage deleteImage = itemImageRepository.findById(itemImageId).orElseThrow(EntityNotFoundException::new);
        String imageUrl = itemImageLocation + "/" + deleteImage.getImageName();

        //폴더내 파일을 삭제
        imageFileService.deleteFile(imageUrl);

        itemImageRepository.deleteById(itemImageId);
    }

}
