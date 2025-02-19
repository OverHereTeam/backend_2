package backend.overhere.repository;

import backend.overhere.domain.NonObstacleInfo;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface NonObstacleInfoRepository extends JpaRepository<NonObstacleInfo,Long> {


    // 다른 type들에 대해서도 찾아주는 메서드를 추가

    Page<NonObstacleInfo> findByExitsIsTrue(Pageable pageable);

    Page<NonObstacleInfo> findByParkingIsTrue(Pageable pageable);

    Page<NonObstacleInfo> findByHelpdogIsTrue(Pageable pageable);

    Page<NonObstacleInfo> findByWheelchairIsTrue(Pageable pageable);

    Page<NonObstacleInfo> findByRestroomIsTrue(Pageable pageable);

    Page<NonObstacleInfo> findByAudioguideIsTrue(Pageable pageable);
}
