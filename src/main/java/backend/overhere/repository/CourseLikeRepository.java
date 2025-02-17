package backend.overhere.repository;

import backend.overhere.domain.Course;
import backend.overhere.domain.CourseLike;
import backend.overhere.domain.Like;
import backend.overhere.domain.User;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface CourseLikeRepository extends JpaRepository<CourseLike, Long> {
    boolean existsByUserAndCourse(User user, Course course);
    Page<CourseLike> findByUserId(Long userId, Pageable pageable);
}
