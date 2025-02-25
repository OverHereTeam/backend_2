package backend.overhere.dto.domain.noticedto;

import lombok.Builder;
import lombok.Getter;

import java.time.LocalDateTime;

@Builder
@Getter
public class InquiryDetailResponseDto {
    private Long id;
    private String title;
    private LocalDateTime createdAt;
    private boolean isAnswered;
    private String inquiryType;
}
