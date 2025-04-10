package backend.overhere.repository;

import backend.overhere.domain.Course;
import backend.overhere.domain.TouristAttraction;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

//querydsl 사용을 위해
@Repository
public interface CourseRepository extends JpaRepository<Course, Long>, CourseRepositoryCustom {
    @Query("SELECT ca.touristAttraction FROM TouristAttractionCourse ca WHERE ca.course.id = :courseId")
    List<TouristAttraction> findTouristAttractionsByCourseId(Long courseId);

    // // 좋아요 수가 많은 코스를 내림차순으로 정렬
    @Query("SELECT c FROM Course c LEFT JOIN c.courseLikes cl " +
            "GROUP BY c " +
            "ORDER BY COUNT(cl.id) DESC, c.title ASC")
    List<Course> findMostLikedCourses(Pageable pageable);


    // ID 리스트에 해당하는 코스 전부 삭제
    void deleteAllByIdIn(List<Long> ids);

}
