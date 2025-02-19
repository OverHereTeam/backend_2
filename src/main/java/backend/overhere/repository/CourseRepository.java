package backend.overhere.repository;

import backend.overhere.domain.*;
import org.springframework.data.domain.Page;
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


}
