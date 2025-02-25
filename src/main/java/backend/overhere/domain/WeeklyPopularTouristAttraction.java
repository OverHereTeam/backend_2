package backend.overhere.domain;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import lombok.*;

@Entity
@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class WeeklyPopularTouristAttraction {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    // 지역 코드 (ex. areacode)
    private String areaCode;

    // 관광지 id
    private Long touristAttractionId;

    // 관광지 제목
    private String title;

    // 지난 일주일간 좋아요 수
    private Long weeklyLikeCount;

    // 썸네일
    private String thumbnailUrl;

    // NonObstacleInfo의 boolean 정보
    private Boolean helpdog;
    private Boolean parking;
    private Boolean wheelchair;
    private Boolean restroom;
    private Boolean audioguide;

}
