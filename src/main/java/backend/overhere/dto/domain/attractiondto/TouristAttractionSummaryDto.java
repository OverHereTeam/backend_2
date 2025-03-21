package backend.overhere.dto.domain.attractiondto;

import backend.overhere.domain.NonObstacleInfo;
import backend.overhere.dto.domain.NonObstacleInfoDto;
import lombok.*;

@Getter
@Setter
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class TouristAttractionSummaryDto {
    
    private Long touristId;
    private String title;
    private String detailInfo;
    private String imageUrl;
    private NonObstacleInfoDto nonObstacleInfo;

}
