package backend.overhere.domain;

import backend.overhere.dto.domain.FaqResponseDto;
import jakarta.persistence.*;
import lombok.*;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedDate;

import java.time.LocalDateTime;

@Entity
@Getter
@Setter
public class Faq {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name="faq_id")
    private Long id;

    private String title;

    @Column(columnDefinition = "TEXT")
    private String content;

    @CreatedDate
    @Column(updatable = false)
    private LocalDateTime createdAt;

    @LastModifiedDate // 엔티티가 수정될 때 자동으로 값 업데이트
    private LocalDateTime updatedAt;

    public FaqResponseDto faqtoFaqResponseDto(){
        return FaqResponseDto.builder().id(this.getId()).title(this.getTitle()).createdAt(this.getCreatedAt()).updatedAt(this.getUpdatedAt()).build();
    }
}
