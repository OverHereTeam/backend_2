package backend.overhere.dto.domain.attractiondto;

import lombok.AllArgsConstructor;
import lombok.Getter;


@Getter
@AllArgsConstructor
public class AttractionBasicResponseDto {
    private String title;
    private String contentId; //관광지 ID

}
