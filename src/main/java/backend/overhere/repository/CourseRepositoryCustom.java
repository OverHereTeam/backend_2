package backend.overhere.repository;

import backend.overhere.domain.Course;
import backend.overhere.dto.domain.coursedto.NonObstacleRequestDto;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface CourseRepositoryCustom {

    List<Course> recommendByRegion(String region);
    List<Course> recommendByAreacodeAndCourseType(Integer areacode, String courseType);
    List<Course> recommendByAreacodeAndNonobstacle(Integer areacode, NonObstacleRequestDto nonObstacleRequestDto);
    List<Course> searchByQuery(String searchQuery);
}
