package backend.overhere.dto.domain.page;

import backend.overhere.dto.domain.AttractionBasicResponseDto;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;

import java.util.List;

@AllArgsConstructor
@Getter
public class SearchCoursePageResponseDto {

    private Integer totalPages;
    private List<PageCourseResponseDto> contents;

    @Getter
    @Builder
    public static class PageCourseResponseDto {
        private Long id;

        private String courseType;

        private String title;

        private String briefDescription;

        private double distance;

        private List<AttractionBasicResponseDto> attractions;
    }
}
