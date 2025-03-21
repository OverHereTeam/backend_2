package backend.overhere.dto.domain.coursedto;


import backend.overhere.domain.TouristAttraction;
import backend.overhere.dto.domain.attractiondto.TouristAttractionSummaryDto;
import lombok.*;

import java.util.ArrayList;
import java.util.List;


@Getter
@Setter
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class CourseDetailResponse {
    private Long courseId;
    private String courseType;
    private Long likeNumber;
    private String title;
    private Long view;
    private String overView;
    private String difficulty;
    private Double distance;
    private String region; // 지역 정보 추가
    private String thumbnailUrl; // 첫 번째 관광지의 썸네일 URL
    private List<TouristAttractionSummaryDto> touristSummary = new ArrayList<>();
}