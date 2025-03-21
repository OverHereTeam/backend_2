package backend.overhere.dto.domain.coursedto;

import lombok.Builder;
import lombok.Data;

import java.util.List;

@Data
@Builder
public class WeeklyPopularCourseResponseDto {
    private Long courseId;
    private String courseType;
    private String region;
    private String title;
    private Long weeklyLikeCount;
    private CourseInfo courseInfo;
    private List<String> touristAttractionNames;
    private String thumbnailUrl;  // 썸네일 URL 필드 추가

    @Data
    @Builder
    public static class CourseInfo {
        private String distance;
        private String number;
        private String difficulty;
    }

    // 편의 메서드 업데이트
    public static WeeklyPopularCourseResponseDto of(
            Long courseId,
            String courseType,
            String region,
            String title,
            Long weeklyLikeCount,
            String distance,
            String difficulty,
            List<String> touristAttractionNames,
            String thumbnailUrl) {

        return WeeklyPopularCourseResponseDto.builder()
                .courseId(courseId)
                .courseType(courseType)
                .region(region)
                .title(title)
                .weeklyLikeCount(weeklyLikeCount)
                .courseInfo(CourseInfo.builder()
                        .distance(distance)
                        .number(String.valueOf(touristAttractionNames.size()))
                        .difficulty(difficulty)
                        .build())
                .touristAttractionNames(touristAttractionNames)
                .thumbnailUrl(thumbnailUrl)
                .build();
    }
}