package backend.overhere.dto.response;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class ResponseDtoNonObstacleInfo {
    private String helpdog; // 안내견 동반 가능 여부
    private String audioguide; // 오디오 가이드
    private String videoguide; // 비디오 가이드
    private String exitLocation; // 출입 통로
    private String publictransport; // 접근 경로
    private String parking; // 장애인 주차구역
    private String restroom; // 장애인 화장실
}
