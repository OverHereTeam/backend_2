package backend.overhere.dto.domain;

import lombok.Builder;

import java.time.LocalDateTime;

@Builder
public class InquiryResponseDto {
    private Long id;
    private String title;
    private LocalDateTime createdAt;
    private boolean isAnswered;
    private String inquiryType;

}
