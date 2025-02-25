package backend.overhere.repository;

import backend.overhere.domain.WeeklyPopularTouristAttraction;
import org.springframework.data.jpa.repository.JpaRepository;

public interface WeeklyPopularTouristAttractionRepository extends JpaRepository<WeeklyPopularTouristAttraction, Long> {
    // 필요한 경우 지역별 조회 등 추가 메서드 정의 가능
}
