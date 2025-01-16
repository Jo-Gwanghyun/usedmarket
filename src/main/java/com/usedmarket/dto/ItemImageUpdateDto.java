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
public class ItemImageUpdateDto {
    private Long id;

    private String thumbnailImage;

    private String imageName;

    private String originImageName;

    private String imageUrl;

    static ModelMapper modelMapper = new ModelMapper();

    public static ItemImageUpdateDto of(ItemImage itemImage) {
        return modelMapper.map(itemImage, ItemImageUpdateDto.class);
    }
}
