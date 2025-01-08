package com.usedmarket.dto;

import com.usedmarket.entity.Item;
import com.usedmarket.entity.ItemImage;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;
import org.modelmapper.ModelMapper;

@Getter
@Setter
@ToString
public class ItemImageDto {

    private Long id;

    private String thumbnailImage;

    private String imageName;

    private String originImageName;

    private String imageUrl;

    private Item item;

    static ModelMapper modelMapper = new ModelMapper();

    public ItemImage toEntity(){
        return ItemImage.builder().id(id).thumbnailImage(thumbnailImage).imageName(imageName).originImageName(originImageName)
                .imageUrl(imageUrl).item(item).build();
    }

    public static ItemImageDto of(ItemImage itemImage){
        return modelMapper.map(itemImage, ItemImageDto.class);
    }
}
