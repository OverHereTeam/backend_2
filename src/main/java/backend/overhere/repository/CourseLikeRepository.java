package backend.overhere.repository;

import backend.overhere.domain.*;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface CourseLikeRepository extends JpaRepository<CourseLike, Long> {
    boolean existsByUserAndCourse(User user, Course course);
    Page<CourseLike> findByUserId(Long userId, Pageable pageable);

    Optional<CourseLike> findByUserAndCourse(User user, Course course);
    Optional<CourseLike> findByUserIdAndCourseId(Long userId, Long courseId);
}
