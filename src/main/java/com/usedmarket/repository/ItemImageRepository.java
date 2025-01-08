package com.usedmarket.repository;

import com.usedmarket.entity.ItemImage;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface ItemImageRepository extends JpaRepository<ItemImage, Long> {
    List<ItemImage> findByItemIdOrderByIdAsc(Long itemId); // 이미지 id의 오름차순으로 정렬
}
