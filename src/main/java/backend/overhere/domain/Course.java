package backend.overhere.domain;

import backend.overhere.dto.domain.CourseResponseDto;
import backend.overhere.dto.domain.LikeResponseDto;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Entity
@NoArgsConstructor
@Getter
@Builder
@AllArgsConstructor
public class Course {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name="course_id")
    private Long courseId;

    private String courseType;

    private String title;

    private String briefDescription;

    private String overView;

    private String difficulty;

    private double distance;
    public CourseResponseDto CoursetoDto() {
        return CourseResponseDto.builder()
                .courseId(this.courseId)
                .courseType(this.courseType)
                .title(this.title)
                .briefDescription(this.briefDescription)
                .overView(this.overView)
                .difficulty(this.difficulty)
                .distance(this.distance)
                .build();
    }
}
