package backend.overhere.repository;

import backend.overhere.domain.Course;
import backend.overhere.domain.Like;
import backend.overhere.domain.TouristAttraction;
import backend.overhere.domain.User;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface LikeRepository extends JpaRepository<Like, Long> {
    Optional<Like> findByUserAndTouristAttraction(User user, TouristAttraction touristAttraction);
    Optional<Like> findByUserIdAndTouristAttractionId(Long userId, Long attractionId);
    boolean existsByUserAndTouristAttraction(User user, TouristAttraction touristAttraction);
    Page<Like> findByUserId(Long userId, Pageable pageable);
}




