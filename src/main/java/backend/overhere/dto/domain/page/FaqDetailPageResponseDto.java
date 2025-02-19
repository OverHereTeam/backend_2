package backend.overhere.dto.domain.page;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;

import java.time.LocalDateTime;
import java.util.List;

@Getter
@AllArgsConstructor
public class FaqDetailPageResponseDto {
    private Integer totalPages;
    private List<PageFaqDetailResponseDto> contents;

    @Getter
    @Builder
    public static class PageFaqDetailResponseDto {
        private Long faqId;
        private String title;
        private String content;
        @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd")
        private LocalDateTime createdAt;

        @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd")
        private LocalDateTime updatedAt;
    }

}
