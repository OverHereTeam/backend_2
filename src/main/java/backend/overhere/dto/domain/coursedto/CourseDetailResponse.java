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

    private String overView; //상세설명

    private String difficulty;

    private double distance;


    private List<TouristAttractionSummaryDto> touristSummary = new ArrayList<>();




}
