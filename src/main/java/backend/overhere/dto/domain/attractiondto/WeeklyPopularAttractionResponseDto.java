package backend.overhere.dto.domain.attractiondto;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class WeeklyPopularAttractionResponseDto {
    private Long touristAttractionId;
    private String areaCode;
    private String title;
    private String thumbnailUrl;
    private Long weeklyLikeCount;
    private Boolean helpdog;
    private Boolean parking;
    private Boolean wheelchair;
    private Boolean restroom;
    private Boolean audioguide;
}
