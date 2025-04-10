package backend.overhere.domain;

import static com.querydsl.core.types.PathMetadataFactory.*;

import com.querydsl.core.types.dsl.*;

import com.querydsl.core.types.PathMetadata;
import javax.annotation.processing.Generated;
import com.querydsl.core.types.Path;
import com.querydsl.core.types.dsl.PathInits;


/**
 * QCourse is a Querydsl query type for Course
 */
@Generated("com.querydsl.codegen.DefaultEntitySerializer")
public class QCourse extends EntityPathBase<Course> {

    private static final long serialVersionUID = 1966893973L;

    public static final QCourse course = new QCourse("course");

    public final StringPath briefDescription = createString("briefDescription");

    public final ListPath<CourseLike, QCourseLike> courseLikes = this.<CourseLike, QCourseLike>createList("courseLikes", CourseLike.class, QCourseLike.class, PathInits.DIRECT2);

    public final StringPath courseType = createString("courseType");

    public final StringPath difficulty = createString("difficulty");

    public final NumberPath<Double> distance = createNumber("distance", Double.class);

    public final NumberPath<Long> id = createNumber("id", Long.class);

    public final StringPath overview = createString("overview");

    public final StringPath title = createString("title");

    public final ListPath<TouristAttractionCourse, QTouristAttractionCourse> touristAttractionCourses = this.<TouristAttractionCourse, QTouristAttractionCourse>createList("touristAttractionCourses", TouristAttractionCourse.class, QTouristAttractionCourse.class, PathInits.DIRECT2);

    public final NumberPath<Long> view = createNumber("view", Long.class);

    public QCourse(String variable) {
        super(Course.class, forVariable(variable));
    }

    public QCourse(Path<? extends Course> path) {
        super(path.getType(), path.getMetadata());
    }

    public QCourse(PathMetadata metadata) {
        super(Course.class, metadata);
    }

}

