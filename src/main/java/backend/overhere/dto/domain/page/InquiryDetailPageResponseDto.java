package backend.overhere.dto.domain.page;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;

import java.time.LocalDateTime;
import java.util.List;

@AllArgsConstructor
@Getter
public class InquiryDetailPageResponseDto {
    private Integer totalPages;
    private List<PageInquiryDetailResponseDto> contents;

    @Getter
    @Builder
    public static class PageInquiryDetailResponseDto {
        private Long id;
        private String title;
        @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd")
        private LocalDateTime createdAt;
        private boolean isAnswered;
        private String inquiryType;
    }
}
