package backend.overhere.dto.domain;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

import java.util.List;

@AllArgsConstructor
@Getter
public class CourseResponseDto {

    private Integer totalPages;
    private List<PageCourseResponseDto> contents;

    @Getter
    @Builder
    public static class PageCourseResponseDto {
        private Long courseId;
        private String courseType;

        private String title;

        private String briefDescription;

        private String overView;

        private String difficulty;

        private double distance;
    }
}
