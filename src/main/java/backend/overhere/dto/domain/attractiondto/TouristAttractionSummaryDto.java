package backend.overhere.dto.domain.attractiondto;

import backend.overhere.domain.NonObstacleInfo;
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
    private NonObstacleInfo nonObstacleInfo;



}
