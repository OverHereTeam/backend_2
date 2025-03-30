package backend.overhere.domain;

import static com.querydsl.core.types.PathMetadataFactory.*;

import com.querydsl.core.types.dsl.*;

import com.querydsl.core.types.PathMetadata;
import javax.annotation.processing.Generated;
import com.querydsl.core.types.Path;


/**
 * QDetailInfo is a Querydsl query type for DetailInfo
 */
@Generated("com.querydsl.codegen.DefaultEntitySerializer")
public class QDetailInfo extends EntityPathBase<DetailInfo> {

    private static final long serialVersionUID = -1859543271L;

    public static final QDetailInfo detailInfo = new QDetailInfo("detailInfo");

    public final BooleanPath braileblock = createBoolean("braileblock");

    public final BooleanPath elevator = createBoolean("elevator");

    public final BooleanPath guidehuman = createBoolean("guidehuman");

    public final NumberPath<Long> id = createNumber("id", Long.class);

    public final BooleanPath lactationroom = createBoolean("lactationroom");

    public final BooleanPath signguide = createBoolean("signguide");

    public final BooleanPath stroller = createBoolean("stroller");

    public final StringPath useFee = createString("useFee");

    public final StringPath useTime = createString("useTime");

    public QDetailInfo(String variable) {
        super(DetailInfo.class, forVariable(variable));
    }

    public QDetailInfo(Path<? extends DetailInfo> path) {
        super(path.getType(), path.getMetadata());
    }

    public QDetailInfo(PathMetadata metadata) {
        super(DetailInfo.class, metadata);
    }

}

