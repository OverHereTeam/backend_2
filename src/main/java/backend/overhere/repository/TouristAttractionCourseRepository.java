package backend.overhere.repository;

import backend.overhere.domain.TouristAttractionCourse;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface TouristAttractionCourseRepository extends JpaRepository<TouristAttractionCourse, Long> {


}
