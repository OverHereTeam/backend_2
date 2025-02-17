package backend.overhere.dto.domain;

import jakarta.persistence.Lob;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class InquiryRequestDto {
    private String title;

    private String inquiryType;

    private Long userId;

    private String content;

}
