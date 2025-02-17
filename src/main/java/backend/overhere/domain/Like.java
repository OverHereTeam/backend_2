package backend.overhere.domain;

import backend.overhere.dto.domain.CourseLikeResponseDto;
import backend.overhere.dto.domain.TouristAttractionLikeResponseDto;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Entity
@Table(name="likes")
public class Like {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "like_id")
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name="user_id")
    private User user;

    @ManyToOne(fetch =FetchType.LAZY)
    @JoinColumn(name="tourist_attraction_id")
    private TouristAttraction touristAttraction;

    @ManyToOne(fetch =FetchType.LAZY)
    @JoinColumn(name="course_id")
    private Course course;
    
    public TouristAttractionLikeResponseDto liketoTouristAttractionDto()
    {
        return TouristAttractionLikeResponseDto.builder()
                .likeId(this.id)
                .touristAttractionId(this.touristAttraction.getId())
                .username(this.user.getNickname())
                .build();
    }

    public CourseLikeResponseDto liketoCourseDto()
    {
        return CourseLikeResponseDto.builder()
                .likeId(this.id)
                .courseId(this.course.getId())
                .username(this.user.getNickname())
                .build();
    }

    
}
