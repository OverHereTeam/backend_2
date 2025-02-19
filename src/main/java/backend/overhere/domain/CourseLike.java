package backend.overhere.domain;

import backend.overhere.dto.domain.CourseLikeResponseDto;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Entity
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

    public CourseLikeResponseDto liketoCourseDto()
    {
        return CourseLikeResponseDto.builder()
                .likeId(this.id)
                .username(this.user.getNickname())
                .courseId(this.course.getId())
                .build();
    }

}
