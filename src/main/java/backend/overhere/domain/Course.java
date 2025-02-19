package backend.overhere.domain;

import backend.overhere.dto.domain.CourseResponseDto;
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
    private Long id;

    private String courseType;

    private String title;

    private String briefDescription;

    private String overview;

    private String difficulty;

    private double distance;
    public CourseResponseDto.PageCourseResponseDto CoursetoDto() {
        return CourseResponseDto.PageCourseResponseDto.builder()
                .courseId(this.id)
                .courseType(this.courseType)
                .title(this.title)
                .briefDescription(this.briefDescription)
                .overView(this.overview)
                .difficulty(this.difficulty)
                .distance(this.distance)
                .build();
    }


}
