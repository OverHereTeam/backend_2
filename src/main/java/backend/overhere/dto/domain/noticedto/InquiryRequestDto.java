package backend.overhere.dto.domain.noticedto;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class InquiryRequestDto {
    private String title;

    private String inquiryType;

    private String content;

}
