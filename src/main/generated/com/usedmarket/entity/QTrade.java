package com.usedmarket.entity;

import static com.querydsl.core.types.PathMetadataFactory.*;

import com.querydsl.core.types.dsl.*;

import com.querydsl.core.types.PathMetadata;
import javax.annotation.processing.Generated;
import com.querydsl.core.types.Path;


/**
 * QTrade is a Querydsl query type for Trade
 */
@Generated("com.querydsl.codegen.DefaultEntitySerializer")
public class QTrade extends EntityPathBase<Trade> {

    private static final long serialVersionUID = 631760801L;

    public static final QTrade trade = new QTrade("trade");

    public final QBaseTimeEntity _super = new QBaseTimeEntity(this);

    public final StringPath createdBy = createString("createdBy");

    public final EnumPath<com.usedmarket.constant.TradeStatus> createdByStatus = createEnum("createdByStatus", com.usedmarket.constant.TradeStatus.class);

    //inherited
    public final DateTimePath<java.time.LocalDateTime> createTime = _super.createTime;

    public final NumberPath<Long> id = createNumber("id", Long.class);

    public final NumberPath<Long> itemId = createNumber("itemId", Long.class);

    public final StringPath itemName = createString("itemName");

    public final StringPath requester = createString("requester");

    public final EnumPath<com.usedmarket.constant.TradeStatus> requesterStatus = createEnum("requesterStatus", com.usedmarket.constant.TradeStatus.class);

    public final EnumPath<com.usedmarket.constant.TradeStatus> tradeStatus = createEnum("tradeStatus", com.usedmarket.constant.TradeStatus.class);

    public final EnumPath<com.usedmarket.constant.TradeStyle> tradeStyle = createEnum("tradeStyle", com.usedmarket.constant.TradeStyle.class);

    //inherited
    public final DateTimePath<java.time.LocalDateTime> updateTime = _super.updateTime;

    public QTrade(String variable) {
        super(Trade.class, forVariable(variable));
    }

    public QTrade(Path<? extends Trade> path) {
        super(path.getType(), path.getMetadata());
    }

    public QTrade(PathMetadata metadata) {
        super(Trade.class, metadata);
    }

}

