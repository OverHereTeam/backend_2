package backend.overhere.service.api;

import backend.overhere.domain.NonObstacleInfo;
import backend.overhere.domain.enums.ObstacleType;
import backend.overhere.repository.NonObstacleInfoRepository;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

@Service
@Transactional
@RequiredArgsConstructor
public class NonObstacleInfoService {
    private final NonObstacleInfoRepository nonObstacleInfoRepository;

    public Page<NonObstacleInfo> getNonObstacleInfoByType(ObstacleType type, int page, int size) {
        Pageable pageable = PageRequest.of(page, size);

        // type 값에 따라 메서드 선택
        switch (type) {
            case HELPDOG:
                return nonObstacleInfoRepository.findByHelpdogIsNotNull(pageable);
            case PARKING:
                return nonObstacleInfoRepository.findByParkingIsNotNull(pageable);
            case WHEELCHAIR:
                return nonObstacleInfoRepository.findByWheelchairIsNotNull(pageable);
            case RESTROOM:
                return nonObstacleInfoRepository.findByRestroomIsNotNull(pageable);
            case AUDIOGUIDE:
                return nonObstacleInfoRepository.findByAudioguideIsNotNull(pageable);
            case EXITS:
                return nonObstacleInfoRepository.findByExitsIsNotNull(pageable);
            default:
                throw new IllegalArgumentException("Invalid type: " + type);
        }
    }


}
