package backend.overhere.repository;

import backend.overhere.domain.Course;
import backend.overhere.domain.Like;
import backend.overhere.domain.TouristAttraction;
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
public interface LikeRepository extends JpaRepository<Like, Long> {
    Optional<Like> findByUserAndTouristAttraction(User user, TouristAttraction touristAttraction);
    Optional<Like> findByUserIdAndTouristAttractionId(Long userId, Long attractionId);
    boolean existsByUserAndTouristAttraction(User user, TouristAttraction touristAttraction);
    Page<Like> findByUserId(Long userId, Pageable pageable);


    //각 지역(areaCode)에서 지난 일주일 동안 관광지별 좋아요 수를 집계하여 내림차순 정렬


    @Query(value = "SELECT ta.tourist_attraction_id, ta.area_code, ta.title, ta.thumbnail1, COUNT(l.like_id) as weeklyLikeCount, " +
            "noi.helpdog, noi.parking, noi.wheelchair, noi.restroom, noi.audioguide " +
            "FROM likes l " +
            "JOIN tourist_attraction ta ON l.tourist_attraction_id = ta.tourist_attraction_id " +
            "JOIN non_obstacle_info noi ON ta.non_obstacle_info_id = noi.non_obstacle_info_id " +
            "WHERE ta.area_code = :areaCode " +
            "AND l.created_at BETWEEN :startDate AND :endDate " +
            "GROUP BY ta.tourist_attraction_id, ta.area_code, ta.title, ta.thumbnail1, " +
            "noi.helpdog, noi.parking, noi.wheelchair, noi.restroom, noi.audioguide " +
            "ORDER BY weeklyLikeCount DESC, ta.title ASC ",
            nativeQuery = true)
    List<Object[]> findWeeklyPopularAttractionsByAreaCode(@Param("areaCode") Integer areaCode,
                                                          @Param("startDate") LocalDateTime startDate,
                                                          @Param("endDate") LocalDateTime endDate,
                                                          Pageable pageable);
}




