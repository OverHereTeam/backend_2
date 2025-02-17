package backend.overhere.dto.domain;

import lombok.Builder;
import lombok.Getter;

import java.time.LocalDateTime;

@Builder
@Getter
public class InquiryResponseDto {
    private Long inquiryId;
}
