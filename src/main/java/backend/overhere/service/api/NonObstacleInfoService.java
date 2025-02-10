package backend.overhere.service.api;

import backend.overhere.repository.NonObstacleInfoRepository;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@Transactional
@RequiredArgsConstructor
public class NonObstacleInfoService {
    private final NonObstacleInfoRepository nonObstacleInfoRepository;


}
