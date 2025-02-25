package backend.overhere.dto.domain.attractiondto;

import lombok.*;

@Getter
@Setter
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class TouristAttractionSummaryDto {
    
    private Long TouristId;
    private String Title;
    private String Description;
    
    private Boolean helpdog;
    private Boolean parking;
    private Boolean wheelchair;
    private Boolean restroom;
    private Boolean audioguide;
    private String exits;
}
