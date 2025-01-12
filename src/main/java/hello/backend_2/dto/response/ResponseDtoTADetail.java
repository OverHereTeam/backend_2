package hello.backend_2.dto.response;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class ResponseDtoTADetail {
    private Long id;
    private String title;
    private String address1;
    private String thumbnail1; // 웹
    private String thumbnail2; // 모바일
    
    /* TODO
        private String overview;
        private String homepage;
        private Double mapx; // GPS 경도
        private Double mapy; // GPS 위도
        private int mapLevel;
        private Gallery gallery;
    */
    
    private String tel;
    private ResponseDtoNonObstacleInfo nonObstacleInfo;
}
