package backend.overhere.repository;

import backend.overhere.domain.WeeklyPopularCourse;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface WeeklyPopularCourseRepository extends JpaRepository<WeeklyPopularCourse, Long> {

    // 집계 테이블에 저장된 모든 인기 코스를 정렬하여 반환
    List<WeeklyPopularCourse> findAllByOrderByWeeklyLikeCountDescTitleAsc();
}