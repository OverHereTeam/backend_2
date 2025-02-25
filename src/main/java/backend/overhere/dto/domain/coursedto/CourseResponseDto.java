package backend.overhere.dto.domain.coursedto;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

@Builder
@Getter
@Setter
public class CourseResponseDto {

    private Long courseId;
    private String courseType;

    private String title;

    private String briefDescription;

    private String difficulty;

    private double distance;
}
