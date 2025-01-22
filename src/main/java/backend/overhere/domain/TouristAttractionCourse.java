package backend.overhere.domain;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Entity
@NoArgsConstructor
@Getter
public class TouristAttractionCourse {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name="tourist_attraction_course_id")
    private Long id;

    @ManyToOne
    @JoinColumn(name = "tourist_attraction_id") // 외래 키로 TouristAttraction 연결
    private TouristAttraction touristAttraction;

    @ManyToOne
    @JoinColumn(name= "course_id")
    private Course course;

}
