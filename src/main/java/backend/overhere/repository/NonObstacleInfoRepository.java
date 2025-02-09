package backend.overhere.repository;

import backend.overhere.domain.NonObstacleInfo;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface NonObstacleInfoRepository extends JpaRepository<NonObstacleInfo,Long> {
}
