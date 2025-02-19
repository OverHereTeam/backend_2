package backend.overhere.dto.domain;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;

import java.util.List;


@Getter
@AllArgsConstructor
public class TouristSearchResponseDto {

    private Integer totalPages;
    private List<PageTouristResponseDto> contents;

    @Getter
    @Builder
    public static class PageTouristResponseDto {
        private String title;
        private Integer areaCode;
        private String overView;
        private Long contentId;
        private Integer contentTypeId;
        private String thumbnailUrl;


        private Boolean helpdog;
        private Boolean parking;
        private Boolean wheelchair;
        private Boolean restroom;
        private Boolean audioguide;
        private String exits;
    }


}
