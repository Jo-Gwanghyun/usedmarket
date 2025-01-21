package com.usedmarket.entity;

import static com.querydsl.core.types.PathMetadataFactory.*;

import com.querydsl.core.types.dsl.*;

import com.querydsl.core.types.PathMetadata;
import javax.annotation.processing.Generated;
import com.querydsl.core.types.Path;
import com.querydsl.core.types.dsl.PathInits;


/**
 * QOrdersItem is a Querydsl query type for OrdersItem
 */
@Generated("com.querydsl.codegen.DefaultEntitySerializer")
public class QOrdersItem extends EntityPathBase<OrdersItem> {

    private static final long serialVersionUID = 1353564027L;

    private static final PathInits INITS = PathInits.DIRECT2;

    public static final QOrdersItem ordersItem = new QOrdersItem("ordersItem");

    public final QBaseEntity _super = new QBaseEntity(this);

    //inherited
    public final StringPath createdBy = _super.createdBy;

    //inherited
    public final DateTimePath<java.time.LocalDateTime> createTime = _super.createTime;

    public final NumberPath<Long> id = createNumber("id", Long.class);

    public final QItem item;

    public final NumberPath<Integer> itemPrice = createNumber("itemPrice", Integer.class);

    public final QOrders orders;

    //inherited
    public final DateTimePath<java.time.LocalDateTime> updateTime = _super.updateTime;

    public QOrdersItem(String variable) {
        this(OrdersItem.class, forVariable(variable), INITS);
    }

    public QOrdersItem(Path<? extends OrdersItem> path) {
        this(path.getType(), path.getMetadata(), PathInits.getFor(path.getMetadata(), INITS));
    }

    public QOrdersItem(PathMetadata metadata) {
        this(metadata, PathInits.getFor(metadata, INITS));
    }

    public QOrdersItem(PathMetadata metadata, PathInits inits) {
        this(OrdersItem.class, metadata, inits);
    }

    public QOrdersItem(Class<? extends OrdersItem> type, PathMetadata metadata, PathInits inits) {
        super(type, metadata, inits);
        this.item = inits.isInitialized("item") ? new QItem(forProperty("item"), inits.get("item")) : null;
        this.orders = inits.isInitialized("orders") ? new QOrders(forProperty("orders"), inits.get("orders")) : null;
    }

}

