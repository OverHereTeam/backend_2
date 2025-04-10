package backend.overhere.repository;

import backend.overhere.domain.*;
import backend.overhere.dto.domain.coursedto.NonObstacleRequestDto;
import com.querydsl.core.BooleanBuilder;
import com.querydsl.jpa.impl.JPAQueryFactory;
import jakarta.persistence.EntityManager;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;
import org.springframework.util.StringUtils;

import java.util.ArrayList;
import java.util.List;

@Repository
public class CourseRepositoryImpl implements CourseRepositoryCustom {

    private final JPAQueryFactory queryFactory;

    public CourseRepositoryImpl(EntityManager em) {
        this.queryFactory = new JPAQueryFactory(em);
    }

    @Override
    public List<Course> recommendByRegion(String region) {
        QCourse course = QCourse.course;
        QTouristAttraction touristAttraction = QTouristAttraction.touristAttraction;
        QTouristAttractionCourse touristAttractionCourse = QTouristAttractionCourse.touristAttractionCourse;
        QCourseLike courseLike = QCourseLike.courseLike;

        return queryFactory.selectFrom(course)
                .join(course.touristAttractionCourses, touristAttractionCourse)
                .join(touristAttractionCourse.touristAttraction, touristAttraction)
                .where(touristAttraction.title.contains(region)
                        .and(course.courseLikes.size().gt(1)))
                .orderBy(course.courseLikes.size().desc(), course.title.asc())
                .fetch();
    }

    @Override
    public List<Course> recommendByAreacodeAndCourseType(Integer areacode, String courseType) {
        QCourse course = QCourse.course;
        QTouristAttraction touristAttraction = QTouristAttraction.touristAttraction;
        QTouristAttractionCourse touristAttractionCourse = QTouristAttractionCourse.touristAttractionCourse;
        QCourseLike courseLike = QCourseLike.courseLike;

        return queryFactory.selectFrom(course)
                .join(course.touristAttractionCourses, touristAttractionCourse)
                .join(touristAttractionCourse.touristAttraction, touristAttraction)
                .where(course.courseType.eq(courseType)
                        .and(touristAttraction.areaCode.eq(areacode))
                        .and(course.courseLikes.size().gt(1)))
                .orderBy(course.courseLikes.size().desc(), course.title.asc())
                .fetch();
    }
    //
    @Override
    public List<Course> recommendByAreacodeAndNonobstacle(Integer areacode, NonObstacleRequestDto nonObstacleRequestDto) {
        QCourse course = QCourse.course;
        QTouristAttraction touristAttraction = QTouristAttraction.touristAttraction;
        QTouristAttractionCourse touristAttractionCourse = QTouristAttractionCourse.touristAttractionCourse;
        QCourseLike courseLike = QCourseLike.courseLike;

        BooleanBuilder builder = new BooleanBuilder();
        builder.and(touristAttraction.areaCode.eq(areacode));

        if (nonObstacleRequestDto.getHelpdog() != null) {
            builder.and(touristAttraction.nonObstacleInfo.helpdog.eq(nonObstacleRequestDto.getHelpdog()));
        }
        if (nonObstacleRequestDto.getParking() != null) {
            builder.and(touristAttraction.nonObstacleInfo.parking.eq(nonObstacleRequestDto.getParking()));
        }
        if (nonObstacleRequestDto.getWheelchair() != null) {
            builder.and(touristAttraction.nonObstacleInfo.wheelchair.eq(nonObstacleRequestDto.getWheelchair()));
        }
        if (nonObstacleRequestDto.getRestroom() != null) {
            builder.and(touristAttraction.nonObstacleInfo.restroom.eq(nonObstacleRequestDto.getRestroom()));
        }
        if (nonObstacleRequestDto.getAudioguide() != null) {
            builder.and(touristAttraction.nonObstacleInfo.audioguide.eq(nonObstacleRequestDto.getAudioguide()));
        }
        if (nonObstacleRequestDto.getExits() != null) {
            builder.and(touristAttraction.nonObstacleInfo.exits.eq(nonObstacleRequestDto.getExits()));
        }

        return queryFactory.selectFrom(course)
                .join(course.touristAttractionCourses, touristAttractionCourse)
                .join(touristAttractionCourse.touristAttraction, touristAttraction)
                .where(builder.and(course.courseLikes.size().gt(1)))
                .orderBy(course.courseLikes.size().desc(), course.title.asc())
                .fetch();
    }

    @Override
    public List<Course> searchByQuery(String searchQuery) {
        QCourse course = QCourse.course;

        if (!StringUtils.hasText(searchQuery)) {
            return new ArrayList<>();
        }

        return queryFactory.selectFrom(course)
                .where(course.title.contains(searchQuery)
                        .or(course.overview.contains(searchQuery)))
                .fetch();
    }
}