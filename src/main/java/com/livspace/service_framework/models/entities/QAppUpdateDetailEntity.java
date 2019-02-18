package com.livspace.service_framework.models.entities;

import static com.mysema.query.types.PathMetadataFactory.*;

import com.mysema.query.types.path.*;

import com.mysema.query.types.PathMetadata;
import javax.annotation.Generated;
import com.mysema.query.types.Path;
import com.mysema.query.types.path.PathInits;


/**
 * QAppUpdateDetailEntity is a Querydsl query type for AppUpdateDetailEntity
 */
@Generated("com.mysema.query.codegen.EntitySerializer")
public class QAppUpdateDetailEntity extends EntityPathBase<AppUpdateDetailEntity> {

    private static final long serialVersionUID = -1347398425L;

    private static final PathInits INITS = PathInits.DIRECT2;

    public static final QAppUpdateDetailEntity appUpdateDetailEntity = new QAppUpdateDetailEntity("appUpdateDetailEntity");

    public final QBaseEntity _super = new QBaseEntity(this);

    public final QAppUpdateConfigEntity appUpdateConfigEntity;

    //inherited
    public final StringPath createdBy = _super.createdBy;

    //inherited
    public final DateTimePath<java.util.Date> createdOn = _super.createdOn;

    //inherited
    public final NumberPath<Long> id = _super.id;

    //inherited
    public final DateTimePath<java.util.Date> lastModifiedOn = _super.lastModifiedOn;

    public final StringPath message = createString("message");

    //inherited
    public final NumberPath<Long> version = _super.version;

    public QAppUpdateDetailEntity(String variable) {
        this(AppUpdateDetailEntity.class, forVariable(variable), INITS);
    }

    public QAppUpdateDetailEntity(Path<? extends AppUpdateDetailEntity> path) {
        this(path.getType(), path.getMetadata(), path.getMetadata().isRoot() ? INITS : PathInits.DEFAULT);
    }

    public QAppUpdateDetailEntity(PathMetadata<?> metadata) {
        this(metadata, metadata.isRoot() ? INITS : PathInits.DEFAULT);
    }

    public QAppUpdateDetailEntity(PathMetadata<?> metadata, PathInits inits) {
        this(AppUpdateDetailEntity.class, metadata, inits);
    }

    public QAppUpdateDetailEntity(Class<? extends AppUpdateDetailEntity> type, PathMetadata<?> metadata, PathInits inits) {
        super(type, metadata, inits);
        this.appUpdateConfigEntity = inits.isInitialized("appUpdateConfigEntity") ? new QAppUpdateConfigEntity(forProperty("appUpdateConfigEntity")) : null;
    }

}

