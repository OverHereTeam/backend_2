package backend.overhere.domain;

import static com.querydsl.core.types.PathMetadataFactory.*;

import com.querydsl.core.types.dsl.*;

import com.querydsl.core.types.PathMetadata;
import javax.annotation.processing.Generated;
import com.querydsl.core.types.Path;
import com.querydsl.core.types.dsl.PathInits;


/**
 * QNonObstacleInfo is a Querydsl query type for NonObstacleInfo
 */
@Generated("com.querydsl.codegen.DefaultEntitySerializer")
public class QNonObstacleInfo extends EntityPathBase<NonObstacleInfo> {

    private static final long serialVersionUID = 268999952L;

    private static final PathInits INITS = PathInits.DIRECT2;

    public static final QNonObstacleInfo nonObstacleInfo = new QNonObstacleInfo("nonObstacleInfo");

    public final BooleanPath audioguide = createBoolean("audioguide");

    public final StringPath exits = createString("exits");

    public final BooleanPath helpdog = createBoolean("helpdog");

    public final NumberPath<Long> id = createNumber("id", Long.class);

    public final BooleanPath parking = createBoolean("parking");

    public final BooleanPath restroom = createBoolean("restroom");

    public final QTouristAttraction touristAttraction;

    public final BooleanPath wheelchair = createBoolean("wheelchair");

    public QNonObstacleInfo(String variable) {
        this(NonObstacleInfo.class, forVariable(variable), INITS);
    }

    public QNonObstacleInfo(Path<? extends NonObstacleInfo> path) {
        this(path.getType(), path.getMetadata(), PathInits.getFor(path.getMetadata(), INITS));
    }

    public QNonObstacleInfo(PathMetadata metadata) {
        this(metadata, PathInits.getFor(metadata, INITS));
    }

    public QNonObstacleInfo(PathMetadata metadata, PathInits inits) {
        this(NonObstacleInfo.class, metadata, inits);
    }

    public QNonObstacleInfo(Class<? extends NonObstacleInfo> type, PathMetadata metadata, PathInits inits) {
        super(type, metadata, inits);
        this.touristAttraction = inits.isInitialized("touristAttraction") ? new QTouristAttraction(forProperty("touristAttraction"), inits.get("touristAttraction")) : null;
    }

}

