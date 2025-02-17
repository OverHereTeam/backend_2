package backend.overhere.domain;

import backend.overhere.dto.domain.NoticeRequestDto;
import backend.overhere.dto.domain.NoticeResponseDto;
import jakarta.persistence.*;
import lombok.*;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedDate;

import java.time.LocalDateTime;

@Entity
@Getter
@Builder
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class Notice {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name="notice_id")
    private Long id;

    private String title;

    @Column(columnDefinition = "TEXT")
    private String content;

    @CreatedDate
    @Column(updatable = false)
    private LocalDateTime createdAt;

    @LastModifiedDate // 엔티티가 수정될 때 자동으로 값 업데이트
    private LocalDateTime updatedAt;

    public NoticeResponseDto noticetoNoticeResponseDto(){
        return NoticeResponseDto.builder().id(this.getId()).title(this.getTitle()).build();
    }

}
