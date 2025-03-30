package backend.overhere.domain;

import static com.querydsl.core.types.PathMetadataFactory.*;

import com.querydsl.core.types.dsl.*;

import com.querydsl.core.types.PathMetadata;
import javax.annotation.processing.Generated;
import com.querydsl.core.types.Path;
import com.querydsl.core.types.dsl.PathInits;


/**
 * QTouristAttraction is a Querydsl query type for TouristAttraction
 */
@Generated("com.querydsl.codegen.DefaultEntitySerializer")
public class QTouristAttraction extends EntityPathBase<TouristAttraction> {

    private static final long serialVersionUID = 1454356607L;

    private static final PathInits INITS = PathInits.DIRECT2;

    public static final QTouristAttraction touristAttraction = new QTouristAttraction("touristAttraction");

    public final StringPath address1 = createString("address1");

    public final StringPath address2 = createString("address2");

    public final NumberPath<Integer> areaCode = createNumber("areaCode", Integer.class);

    public final StringPath cat1 = createString("cat1");

    public final StringPath cat2 = createString("cat2");

    public final StringPath cat3 = createString("cat3");

    public final NumberPath<Long> contentId = createNumber("contentId", Long.class);

    public final NumberPath<Integer> contentTypeId = createNumber("contentTypeId", Integer.class);

    public final QDetailInfo detailInfo;

    public final ListPath<Gallery, QGallery> galleries = this.<Gallery, QGallery>createList("galleries", Gallery.class, QGallery.class, PathInits.DIRECT2);

    public final StringPath homepage = createString("homepage");

    public final NumberPath<Long> id = createNumber("id", Long.class);

    public final ListPath<Like, QLike> likes = this.<Like, QLike>createList("likes", Like.class, QLike.class, PathInits.DIRECT2);

    public final NumberPath<Double> mapx = createNumber("mapx", Double.class);

    public final NumberPath<Double> mapy = createNumber("mapy", Double.class);

    public final QNonObstacleInfo nonObstacleInfo;

    public final ListPath<TouristAttractionCourse, QTouristAttractionCourse> nonObstacleInfoList = this.<TouristAttractionCourse, QTouristAttractionCourse>createList("nonObstacleInfoList", TouristAttractionCourse.class, QTouristAttractionCourse.class, PathInits.DIRECT2);

    public final StringPath overview = createString("overview");

    public final StringPath sigungucode = createString("sigungucode");

    public final StringPath tel = createString("tel");

    public final StringPath thumbnail1 = createString("thumbnail1");

    public final StringPath thumbnail2 = createString("thumbnail2");

    public final StringPath title = createString("title");

    public final NumberPath<Long> view = createNumber("view", Long.class);

    public QTouristAttraction(String variable) {
        this(TouristAttraction.class, forVariable(variable), INITS);
    }

    public QTouristAttraction(Path<? extends TouristAttraction> path) {
        this(path.getType(), path.getMetadata(), PathInits.getFor(path.getMetadata(), INITS));
    }

    public QTouristAttraction(PathMetadata metadata) {
        this(metadata, PathInits.getFor(metadata, INITS));
    }

    public QTouristAttraction(PathMetadata metadata, PathInits inits) {
        this(TouristAttraction.class, metadata, inits);
    }

    public QTouristAttraction(Class<? extends TouristAttraction> type, PathMetadata metadata, PathInits inits) {
        super(type, metadata, inits);
        this.detailInfo = inits.isInitialized("detailInfo") ? new QDetailInfo(forProperty("detailInfo")) : null;
        this.nonObstacleInfo = inits.isInitialized("nonObstacleInfo") ? new QNonObstacleInfo(forProperty("nonObstacleInfo"), inits.get("nonObstacleInfo")) : null;
    }

}

