package backend.overhere.domain;

import backend.overhere.dto.domain.InquiryResponseDto;
import jakarta.persistence.*;
import lombok.*;
import org.springframework.data.annotation.CreatedDate;

import java.time.LocalDateTime;

@Entity
@Getter
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Setter
public class Inquiry {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "inquiry_id")
    private Long id;
    private String title;

    @ManyToOne
    @JoinColumn(name="user_id")
    User user;

    @CreatedDate
    @Column(updatable = false)
    private LocalDateTime createdAt;

    private boolean isAnswered;

    private String inquiryType;

    @Column(columnDefinition = "TEXT")
    private String content;

    public InquiryResponseDto inquirytoInquiryResponseDto(){
        return InquiryResponseDto.builder().id(this.getId()).inquiryType(this.getInquiryType()).createdAt(this.getCreatedAt()).isAnswered(this.isAnswered()).title(this.getTitle()).build();
    }
}
