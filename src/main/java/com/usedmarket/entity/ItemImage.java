package com.usedmarket.entity;

import jakarta.persistence.*;
import lombok.*;

@Entity
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Getter
public class ItemImage extends BaseEntity{

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "item_image_id")
    private Long id;

    private String thumbnailImage;

    private String imageName;

    private String originImageName;

    private String imageUrl;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "item_id")
    private Item item;

    @Builder
    public ItemImage(Long id, String thumbnailImage, String imageName, String originImageName, String imageUrl, Item item){
        this.id = id;
        this.thumbnailImage = thumbnailImage;
        this.imageName = imageName;
        this.originImageName = originImageName;
        this.imageUrl = imageUrl;
        this.item = item;
    }

    public void updateItemImage(String imageName, String originImageName, String imageUrl){

        this.imageName = imageName;
        this.originImageName = originImageName;
        this.imageUrl = imageUrl;
    }
}
