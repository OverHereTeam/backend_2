package backend.overhere.service.api;

import backend.overhere.domain.NonObstacleInfo;
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

    public Page<NonObstacleInfo> getNonObstacleInfoByType(String type, int page, int size) {
        Pageable pageable = PageRequest.of(page, size);

        // type 값에 따라 메서드 선택
        switch (type) {
            case "helpdog":
                return nonObstacleInfoRepository.findByHelpdogIsNotNull(pageable);
            case "parking":
                return nonObstacleInfoRepository.findByParkingIsNotNull(pageable);
            case "wheelchair":
                return nonObstacleInfoRepository.findByWheelchairIsNotNull(pageable);
            case "restroom":
                return nonObstacleInfoRepository.findByRestroomIsNotNull(pageable);
            case "audioguide":
                return nonObstacleInfoRepository.findByAudioguideIsNotNull(pageable);
            case "exit":
                return nonObstacleInfoRepository.findByExitsIsNotNull(pageable);
            default:
                throw new IllegalArgumentException("Invalid type: " + type);
        }
    }


}
