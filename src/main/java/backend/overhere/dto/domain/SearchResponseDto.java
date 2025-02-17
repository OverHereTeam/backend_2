package backend.overhere.dto.domain;

import lombok.Builder;
import lombok.Getter;

@Builder
@Getter
public class SearchResponseDto {
    private String title;
    private String areaCode;
    private String overView;
    private Long contentId;
    private String contentTypeId;
    private String thumbnailUrl;


    private Boolean helpdog;
    private Boolean parking;
    private Boolean wheelchair;
    private Boolean restroom;
    private Boolean audioguide;
    private String exits;


}
