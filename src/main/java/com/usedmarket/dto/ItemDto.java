package com.usedmarket.dto;

import com.usedmarket.constant.ItemStatus;
import com.usedmarket.entity.Item;
import com.usedmarket.entity.Member;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Getter;
import lombok.Setter;
import org.modelmapper.ModelMapper;

import java.util.ArrayList;
import java.util.List;

@Getter
@Setter
public class ItemDto {

    private Long id;

    @NotBlank(message = "상품명을 입력하세요.")
    private String itemName;

    @NotBlank(message = "상품설명을 입력하세요.")
    private String itemDetail;

    @NotNull(message = "가격을 입력하세요.")
    @Min(0)
    private int itemPrice;

    private ItemStatus itemStatus;

    private List<ItemImageDto> itemImageDtoList = new ArrayList<>(); // 상품수정시 이미지의 정보를 저장

    private List<Long> itemImageIdList = new ArrayList<>(); // 상품수정시 이미지의 아이디를 담아둠

    private static ModelMapper modelMapper = new ModelMapper();

    private String createdBy;

    public Item toEntity(){
        return Item.builder().id(id).itemName(itemName).itemDetail(itemDetail)
                .itemPrice(itemPrice).itemStatus(itemStatus).build();
    }

    public static ItemDto of(Item item){
        return modelMapper.map(item, ItemDto.class);
    }
}
