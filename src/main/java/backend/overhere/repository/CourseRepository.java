package backend.overhere.repository;

import backend.overhere.domain.Course;
import backend.overhere.domain.TouristAttraction;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface CourseRepository extends JpaRepository<Course, Long>, JpaSpecificationExecutor<Course> {
    @Query("SELECT ca.touristAttraction FROM TouristAttractionCourse ca WHERE ca.course.id = :courseId")
    List<TouristAttraction> findTouristAttractionsByCourseId(Long courseId);

    // // 좋아요 수가 많은 코스를 내림차순으로 정렬
    @Query("SELECT c FROM Course c LEFT JOIN c.courseLikes cl " +
            "GROUP BY c " +
            "ORDER BY COUNT(cl.id) DESC, c.title ASC")
    List<Course> findMostLikedCourses(Pageable pageable);


}
