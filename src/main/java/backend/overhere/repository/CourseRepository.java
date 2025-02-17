package backend.overhere.repository;

import backend.overhere.domain.Course;
import backend.overhere.domain.TouristAttraction;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface CourseRepository extends JpaRepository<Course, Long> {
    @Query("SELECT ca.touristAttraction FROM TouristAttractionCourse ca WHERE ca.course.id = :courseId")
    List<TouristAttraction> findTouristAttractionsByCourseId(Long courseId);

}
