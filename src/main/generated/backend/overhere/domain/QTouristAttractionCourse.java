package backend.overhere.domain;

import static com.querydsl.core.types.PathMetadataFactory.*;

import com.querydsl.core.types.dsl.*;

import com.querydsl.core.types.PathMetadata;
import javax.annotation.processing.Generated;
import com.querydsl.core.types.Path;
import com.querydsl.core.types.dsl.PathInits;


/**
 * QTouristAttractionCourse is a Querydsl query type for TouristAttractionCourse
 */
@Generated("com.querydsl.codegen.DefaultEntitySerializer")
public class QTouristAttractionCourse extends EntityPathBase<TouristAttractionCourse> {

    private static final long serialVersionUID = -1998263942L;

    private static final PathInits INITS = PathInits.DIRECT2;

    public static final QTouristAttractionCourse touristAttractionCourse = new QTouristAttractionCourse("touristAttractionCourse");

    public final QCourse course;

    public final NumberPath<Long> id = createNumber("id", Long.class);

    public final NumberPath<Integer> orders = createNumber("orders", Integer.class);

    public final QTouristAttraction touristAttraction;

    public QTouristAttractionCourse(String variable) {
        this(TouristAttractionCourse.class, forVariable(variable), INITS);
    }

    public QTouristAttractionCourse(Path<? extends TouristAttractionCourse> path) {
        this(path.getType(), path.getMetadata(), PathInits.getFor(path.getMetadata(), INITS));
    }

    public QTouristAttractionCourse(PathMetadata metadata) {
        this(metadata, PathInits.getFor(metadata, INITS));
    }

    public QTouristAttractionCourse(PathMetadata metadata, PathInits inits) {
        this(TouristAttractionCourse.class, metadata, inits);
    }

    public QTouristAttractionCourse(Class<? extends TouristAttractionCourse> type, PathMetadata metadata, PathInits inits) {
        super(type, metadata, inits);
        this.course = inits.isInitialized("course") ? new QCourse(forProperty("course")) : null;
        this.touristAttraction = inits.isInitialized("touristAttraction") ? new QTouristAttraction(forProperty("touristAttraction"), inits.get("touristAttraction")) : null;
    }

}

