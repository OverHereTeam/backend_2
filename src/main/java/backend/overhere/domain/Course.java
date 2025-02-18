package backend.overhere.domain;

import backend.overhere.dto.domain.CourseResponseDto;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.util.ArrayList;
import java.util.List;

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

    //코스 <-> 좋아요
    @OneToMany(mappedBy = "course", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<CourseLike> courseLikes = new ArrayList<>();

    private double distance;
    public CourseResponseDto CoursetoDto() {
        return CourseResponseDto.builder()
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
