package com.livspace.service_framework.models.entities;

import static com.mysema.query.types.PathMetadataFactory.*;

import com.mysema.query.types.path.*;

import com.mysema.query.types.PathMetadata;
import javax.annotation.Generated;
import com.mysema.query.types.Path;
import com.mysema.query.types.path.PathInits;


/**
 * QAppUpdateConfigEntity is a Querydsl query type for AppUpdateConfigEntity
 */
@Generated("com.mysema.query.codegen.EntitySerializer")
public class QAppUpdateConfigEntity extends EntityPathBase<AppUpdateConfigEntity> {

    private static final long serialVersionUID = -1676933352L;

    public static final QAppUpdateConfigEntity appUpdateConfigEntity = new QAppUpdateConfigEntity("appUpdateConfigEntity");

    public final QBaseEntity _super = new QBaseEntity(this);

    public final ListPath<AppUpdateDetailEntity, QAppUpdateDetailEntity> appUpdateDetail = this.<AppUpdateDetailEntity, QAppUpdateDetailEntity>createList("appUpdateDetail", AppUpdateDetailEntity.class, QAppUpdateDetailEntity.class, PathInits.DIRECT2);

    //inherited
    public final StringPath createdBy = _super.createdBy;

    //inherited
    public final DateTimePath<java.util.Date> createdOn = _super.createdOn;

    public final BooleanPath deleted = createBoolean("deleted");

    public final StringPath downloadUrl = createString("downloadUrl");

    public final ListPath<String, StringPath> eligibleUserEmailIds = this.<String, StringPath>createList("eligibleUserEmailIds", String.class, StringPath.class, PathInits.DIRECT2);

    //inherited
    public final NumberPath<Long> id = _super.id;

    //inherited
    public final DateTimePath<java.util.Date> lastModifiedOn = _super.lastModifiedOn;

    public final EnumPath<com.livspace.service_framework.models.enums.UpdateType> updateType = createEnum("updateType", com.livspace.service_framework.models.enums.UpdateType.class);

    //inherited
    public final NumberPath<Long> version = _super.version;

    public final NumberPath<Long> versionNo = createNumber("versionNo", Long.class);

    public QAppUpdateConfigEntity(String variable) {
        super(AppUpdateConfigEntity.class, forVariable(variable));
    }

    public QAppUpdateConfigEntity(Path<? extends AppUpdateConfigEntity> path) {
        super(path.getType(), path.getMetadata());
    }

    public QAppUpdateConfigEntity(PathMetadata<?> metadata) {
        super(AppUpdateConfigEntity.class, metadata);
    }

}

