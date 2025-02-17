package backend.overhere.domain;

import backend.overhere.dto.domain.InquiryResponseDto;
import com.fasterxml.jackson.annotation.JsonFormat;
import jakarta.persistence.*;
import lombok.*;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import java.time.LocalDateTime;

@Entity
@Getter
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Setter
@EntityListeners(AuditingEntityListener.class)
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
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd HH:mm")
    private LocalDateTime createdAt;

    private boolean isAnswered;

    private String inquiryType;

    @Column(columnDefinition = "TEXT")
    private String content;

    public InquiryResponseDto inquirytoInquiryResponseDto(){
        return InquiryResponseDto.builder().inquiryId(this.getId()).build();
    }
}
