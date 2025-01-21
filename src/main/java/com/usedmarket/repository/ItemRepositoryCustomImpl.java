package com.usedmarket.repository;

import com.querydsl.core.types.dsl.BooleanExpression;
import com.querydsl.jpa.impl.JPAQuery;
import com.querydsl.jpa.impl.JPAQueryFactory;
import com.usedmarket.constant.ItemStatus;
import com.usedmarket.dto.*;
import com.usedmarket.entity.QItem;
import com.usedmarket.entity.QItemImage;
import com.usedmarket.entity.QMember;
import jakarta.persistence.EntityManager;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.support.PageableExecutionUtils;
import org.thymeleaf.util.StringUtils;

import java.util.List;

public class ItemRepositoryCustomImpl implements ItemRepositoryCustom {

    private JPAQueryFactory jpaQueryFactory; // 동적으로 쿼리를 사용하기위함

    public ItemRepositoryCustomImpl(EntityManager entityManager){
        jpaQueryFactory = new JPAQueryFactory(entityManager);
    }

    private BooleanExpression searchItemStatus(ItemStatus itemStatus){
        return itemStatus == null ? null : QItem.item.itemStatus.eq(itemStatus);
    }


    private BooleanExpression searchTypeLike(String searchType, String searchText){

        if(StringUtils.equals("itemName",searchType)){
            return QItem.item.itemName.like("%" + searchText + "%");
        } else if(StringUtils.equals("createdBy",searchType)){
            return QItem.item.createdBy.like("%" + searchText + "%");
        }

        return null;
    }


    @Override
    public Page<MainPageDto> getMainPage(ItemSearchDto itemSearchDto, Pageable pageable) {

        QItem item = QItem.item;
        QItemImage itemImage = QItemImage.itemImage;

        List<MainPageDto> content = jpaQueryFactory
                .select(new QMainPageDto(
                        item.id,
                        item.itemName,
                        item.itemDetail,
                        itemImage.imageUrl,
                        item.itemStatus,
                        item.itemPrice,
                        item.member.nickname,
                        item.updateTime))
                .from(itemImage)
                .join(itemImage.item, item)
                .where(itemImage.thumbnailImage.eq("Y"))
                .where(searchTypeLike(itemSearchDto.getSearchType(), itemSearchDto.getSearchText()))
                .where(searchItemStatus(itemSearchDto.getItemStatus()))
                .orderBy(item.id.desc())
                .offset(pageable.getOffset())
                .limit(pageable.getPageSize())
                .fetch();

        JPAQuery<Long> countQuery = jpaQueryFactory
                .select(item.count())
                .from(itemImage)
                .join(itemImage.item,item)
                .where(itemImage.thumbnailImage.eq("Y"))
                .where(searchTypeLike(itemSearchDto.getSearchType(), itemSearchDto.getSearchText()))
                .where(searchItemStatus(itemSearchDto.getItemStatus()));

        return PageableExecutionUtils.getPage(content,pageable,countQuery::fetchOne);
    }

    @Override
    public Page<ItemSellListDto> getSellListPage(ItemSearchDto itemSearchDto, String createdBy, Pageable pageable) {
        QItem item = QItem.item;

        List<ItemSellListDto> content = jpaQueryFactory
                .select(new QItemSellListDto(item.id,
                        item.itemName,
                        item.itemStatus,
                        item.updateTime))
                .from(item)
                .where(searchTypeLike(itemSearchDto.getSearchType(), itemSearchDto.getSearchText()))
                .where(searchItemStatus(itemSearchDto.getItemStatus()))
                .where(item.createdBy.eq(createdBy))
                .orderBy(item.id.desc())
                .offset(pageable.getOffset())
                .limit(pageable.getPageSize())
                .fetch();

        JPAQuery<Long> countQuery = jpaQueryFactory
                .select(item.count())
                .from(item)
                .where(searchTypeLike(itemSearchDto.getSearchType(), itemSearchDto.getSearchText()))
                .where(searchItemStatus(itemSearchDto.getItemStatus()))
                .where(item.createdBy.eq(createdBy));

        return PageableExecutionUtils.getPage(content, pageable, countQuery::fetchOne);
    }
}
