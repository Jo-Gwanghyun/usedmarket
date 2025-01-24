package com.usedmarket.entity;

import static com.querydsl.core.types.PathMetadataFactory.*;

import com.querydsl.core.types.dsl.*;

import com.querydsl.core.types.PathMetadata;
import javax.annotation.processing.Generated;
import com.querydsl.core.types.Path;
import com.querydsl.core.types.dsl.PathInits;


/**
 * QWishlistItem is a Querydsl query type for WishlistItem
 */
@Generated("com.querydsl.codegen.DefaultEntitySerializer")
public class QWishlistItem extends EntityPathBase<WishlistItem> {

    private static final long serialVersionUID = -1305266789L;

    private static final PathInits INITS = PathInits.DIRECT2;

    public static final QWishlistItem wishlistItem = new QWishlistItem("wishlistItem");

    public final QBaseEntity _super = new QBaseEntity(this);

    //inherited
    public final StringPath createdBy = _super.createdBy;

    //inherited
    public final DateTimePath<java.time.LocalDateTime> createTime = _super.createTime;

    public final NumberPath<Long> id = createNumber("id", Long.class);

    public final QItem item;

    //inherited
    public final DateTimePath<java.time.LocalDateTime> updateTime = _super.updateTime;

    public final QWishlist wishlist;

    public QWishlistItem(String variable) {
        this(WishlistItem.class, forVariable(variable), INITS);
    }

    public QWishlistItem(Path<? extends WishlistItem> path) {
        this(path.getType(), path.getMetadata(), PathInits.getFor(path.getMetadata(), INITS));
    }

    public QWishlistItem(PathMetadata metadata) {
        this(metadata, PathInits.getFor(metadata, INITS));
    }

    public QWishlistItem(PathMetadata metadata, PathInits inits) {
        this(WishlistItem.class, metadata, inits);
    }

    public QWishlistItem(Class<? extends WishlistItem> type, PathMetadata metadata, PathInits inits) {
        super(type, metadata, inits);
        this.item = inits.isInitialized("item") ? new QItem(forProperty("item"), inits.get("item")) : null;
        this.wishlist = inits.isInitialized("wishlist") ? new QWishlist(forProperty("wishlist"), inits.get("wishlist")) : null;
    }

}

