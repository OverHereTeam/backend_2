package backend.overhere.domain;

import static com.querydsl.core.types.PathMetadataFactory.*;

import com.querydsl.core.types.dsl.*;

import com.querydsl.core.types.PathMetadata;
import javax.annotation.processing.Generated;
import com.querydsl.core.types.Path;
import com.querydsl.core.types.dsl.PathInits;


/**
 * QWeeklyPopularCourse is a Querydsl query type for WeeklyPopularCourse
 */
@Generated("com.querydsl.codegen.DefaultEntitySerializer")
public class QWeeklyPopularCourse extends EntityPathBase<WeeklyPopularCourse> {

    private static final long serialVersionUID = 216804377L;

    public static final QWeeklyPopularCourse weeklyPopularCourse = new QWeeklyPopularCourse("weeklyPopularCourse");

    public final NumberPath<Long> courseId = createNumber("courseId", Long.class);

    public final StringPath courseType = createString("courseType");

    public final NumberPath<Long> id = createNumber("id", Long.class);

    public final StringPath title = createString("title");

    public final ListPath<String, StringPath> touristAttractionNames = this.<String, StringPath>createList("touristAttractionNames", String.class, StringPath.class, PathInits.DIRECT2);

    public final NumberPath<Long> weeklyLikeCount = createNumber("weeklyLikeCount", Long.class);

    public QWeeklyPopularCourse(String variable) {
        super(WeeklyPopularCourse.class, forVariable(variable));
    }

    public QWeeklyPopularCourse(Path<? extends WeeklyPopularCourse> path) {
        super(path.getType(), path.getMetadata());
    }

    public QWeeklyPopularCourse(PathMetadata metadata) {
        super(WeeklyPopularCourse.class, metadata);
    }

}

