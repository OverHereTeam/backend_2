package backend.overhere.dto.domain;

import lombok.Builder;
import lombok.Getter;

import java.util.List;

@Builder
@Getter
public class SearchCourseResponseDto {
    private Long id;

    private String courseType;

    private String title;

    private String briefDescription;

    private double distance;

    private List<AttractionBasicResponseDto> attractions;
}
