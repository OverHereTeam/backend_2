package backend.overhere.domain;

import static com.querydsl.core.types.PathMetadataFactory.*;

import com.querydsl.core.types.dsl.*;

import com.querydsl.core.types.PathMetadata;
import javax.annotation.processing.Generated;
import com.querydsl.core.types.Path;


/**
 * QWeeklyPopularTouristAttraction is a Querydsl query type for WeeklyPopularTouristAttraction
 */
@Generated("com.querydsl.codegen.DefaultEntitySerializer")
public class QWeeklyPopularTouristAttraction extends EntityPathBase<WeeklyPopularTouristAttraction> {

    private static final long serialVersionUID = -1623114373L;

    public static final QWeeklyPopularTouristAttraction weeklyPopularTouristAttraction = new QWeeklyPopularTouristAttraction("weeklyPopularTouristAttraction");

    public final NumberPath<Integer> areaCode = createNumber("areaCode", Integer.class);

    public final BooleanPath audioguide = createBoolean("audioguide");

    public final BooleanPath helpdog = createBoolean("helpdog");

    public final NumberPath<Long> id = createNumber("id", Long.class);

    public final BooleanPath parking = createBoolean("parking");

    public final BooleanPath restroom = createBoolean("restroom");

    public final StringPath thumbnailUrl = createString("thumbnailUrl");

    public final StringPath title = createString("title");

    public final NumberPath<Long> touristAttractionId = createNumber("touristAttractionId", Long.class);

    public final NumberPath<Long> weeklyLikeCount = createNumber("weeklyLikeCount", Long.class);

    public final BooleanPath wheelchair = createBoolean("wheelchair");

    public QWeeklyPopularTouristAttraction(String variable) {
        super(WeeklyPopularTouristAttraction.class, forVariable(variable));
    }

    public QWeeklyPopularTouristAttraction(Path<? extends WeeklyPopularTouristAttraction> path) {
        super(path.getType(), path.getMetadata());
    }

    public QWeeklyPopularTouristAttraction(PathMetadata metadata) {
        super(WeeklyPopularTouristAttraction.class, metadata);
    }

}

