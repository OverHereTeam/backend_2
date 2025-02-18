package backend.overhere.repository;

import backend.overhere.domain.NonObstacleInfo;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface NonObstacleInfoRepository extends JpaRepository<NonObstacleInfo,Long> {


    // 다른 type들에 대해서도 찾아주는 메서드를 추가

    Page<NonObstacleInfo> findByExitsIsNotNull(Pageable pageable);

    Page<NonObstacleInfo> findByParkingIsNotNull(Pageable pageable);

    Page<NonObstacleInfo> findByHelpdogIsNotNull(Pageable pageable);

    Page<NonObstacleInfo> findByWheelchairIsNotNull(Pageable pageable);

    Page<NonObstacleInfo> findByRestroomIsNotNull(Pageable pageable);

    Page<NonObstacleInfo> findByAudioguideIsNotNull(Pageable pageable);
}
