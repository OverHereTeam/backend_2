package backend.overhere.dto.domain.coursedto;

import lombok.Builder;
import lombok.Data;

import java.util.List;

@Data
@Builder
public class WeeklyPopularCourseResponseDto {
    private Long courseId;
    private String courseType;
    private String title;
    private Long weeklyLikeCount;
    private List<String> touristAttractionNames;
}