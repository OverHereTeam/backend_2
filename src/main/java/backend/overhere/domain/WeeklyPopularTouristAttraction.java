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

    // 기타 필요한 정보 (예: 썸네일, 상세설명 등)
    private String thumbnailUrl;



}
