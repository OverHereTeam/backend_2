package backend.overhere.domain;

import backend.overhere.dto.domain.coursedto.CourseLikeResponseDto;
import com.fasterxml.jackson.annotation.JsonFormat;
import jakarta.persistence.*;
import lombok.*;
import org.springframework.data.annotation.CreatedDate;

import java.time.LocalDateTime;

@Getter
@Setter
@Entity
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class CourseLike {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "like_id")
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name="user_id")
    private User user;

    @ManyToOne(fetch =FetchType.LAZY)
    @JoinColumn(name="course_id")
    private Course course;

    @CreatedDate
    @Column(updatable = false)
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd HH:mm")
    private LocalDateTime createdAt;

    public CourseLikeResponseDto liketoCourseDto()
    {
        return CourseLikeResponseDto.builder()
                .likeId(this.id)
                .username(this.user.getNickname())
                .build();
    }
}
