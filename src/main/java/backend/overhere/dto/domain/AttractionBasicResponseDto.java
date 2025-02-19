package backend.overhere.dto.domain;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;


@Getter
@AllArgsConstructor
public class AttractionBasicResponseDto {
    private String title;
    private Long contentId; //관광지 ID

}
