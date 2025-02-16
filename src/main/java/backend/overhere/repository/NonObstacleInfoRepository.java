package backend.overhere.repository;

import backend.overhere.domain.NonObstacleInfo;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface NonObstacleInfoRepository extends JpaRepository<NonObstacleInfo,Long> {
    // helpdog 값이 null이 아닌 NonObstacleInfo 엔티티를 찾는 메서드
    Page<NonObstacleInfo> findByHelpdogIsTrue(Pageable pageable);

    // 다른 type들에 대해서도 찾아주는 메서드를 추가
    Page<NonObstacleInfo> findByParkingIsTrue(Pageable pageable);
    Page<NonObstacleInfo> findByWheelchairIsTrue(Pageable pageable);
    Page<NonObstacleInfo> findByRestroomIsTrue(Pageable pageable);
    Page<NonObstacleInfo> findByAudioguideIsTrue(Pageable pageable);
    Page<NonObstacleInfo> findByExitsIsNotNull(Pageable pageable);
}
