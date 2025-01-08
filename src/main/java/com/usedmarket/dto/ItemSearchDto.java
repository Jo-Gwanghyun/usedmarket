package com.usedmarket.dto;

import com.usedmarket.constant.ItemStatus;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class ItemSearchDto {

    private ItemStatus itemStatus;

    private String searchType;

    private String searchText = "";
}
