package backend.overhere.dto.domain.noticedto;

import backend.overhere.domain.Notice;
import lombok.Builder;
import lombok.Getter;

import java.time.LocalDateTime;

@Getter
@Builder
public class NoticeSearchResponseDto {
    private Long id;
    private String title;
    private String content;
    private LocalDateTime createdAt;

    public static NoticeSearchResponseDto from(Notice notice) {
        return NoticeSearchResponseDto.builder()
                .id(notice.getId())
                .title(notice.getTitle())
                .content(notice.getContent())
                .createdAt(notice.getCreatedAt())
                .build();
    }
}
