package backend.overhere.repository;

import backend.overhere.domain.Course;
import backend.overhere.domain.CourseLike;
import backend.overhere.domain.Like;
import backend.overhere.domain.User;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

@Repository
public interface CourseLikeRepository extends JpaRepository<CourseLike, Long> {
    boolean existsByUserAndCourse(User user, Course course);
    Page<CourseLike> findByUserId(Long userId, Pageable pageable);


    //GROUP_CONCAT 함수를 사용하여 해당 코스와 연결된 TouristAttraction의 title들을 쉼표(“, “)로 연결

    @Query(value = "SELECT c.course_id, c.course_type, c.title, COUNT(cl.like_id) as weeklyLikeCount, " +
            "GROUP_CONCAT(DISTINCT ta.title ORDER BY ta.title ASC SEPARATOR ', ') AS touristAttractionTitles " +
            "FROM course c " +
            "JOIN course_like cl ON cl.course_id = c.course_id " +
            "LEFT JOIN tourist_attraction_course tac ON tac.course_id = c.course_id " +
            "LEFT JOIN tourist_attraction ta ON ta.tourist_attraction_id = tac.tourist_attraction_id " +
            "WHERE cl.created_at BETWEEN :startDate AND :endDate " +
            "GROUP BY c.course_id, c.course_type, c.title " +
            "ORDER BY weeklyLikeCount DESC, c.title ASC",
            nativeQuery = true)
    List<Object[]> findWeeklyPopularCourses(@Param("startDate") LocalDateTime startDate,
                                            @Param("endDate") LocalDateTime endDate,
                                            Pageable pageable);

    Optional<CourseLike> findByUserAndCourse(User user, Course course);
    Optional<CourseLike> findByUserIdAndCourseId(Long userId, Long courseId);

    // 특정 유저의 모든 코스 좋아요 조회
    List<CourseLike> findAllByUser(User user);

    // 특정 유저의 모든 코스 좋아요 삭제
    void deleteAllByUser(User user);
}
