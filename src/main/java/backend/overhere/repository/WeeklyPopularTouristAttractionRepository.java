package backend.overhere.repository;

import backend.overhere.domain.WeeklyPopularTouristAttraction;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface WeeklyPopularTouristAttractionRepository extends JpaRepository<WeeklyPopularTouristAttraction, Long> {
    List<WeeklyPopularTouristAttraction> findByAreaCodeOrderByWeeklyLikeCountDescTitleAsc(Integer areaCode);

}
