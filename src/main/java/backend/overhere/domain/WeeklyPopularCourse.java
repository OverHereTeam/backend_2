package backend.overhere.domain;

import jakarta.persistence.*;
import lombok.*;

import java.util.List;

@Entity
@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class WeeklyPopularCourse {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    // 코스 ID
    private Long courseId;

    // 코스 타입 및 제목
    private String courseType;
    private String title;

    // 지난 일주일간 좋아요 집계 수
    private Long weeklyLikeCount;

    // 해당 코스와 연관된 모든 TouristAttraction의 title 리스트
    @ElementCollection
    @CollectionTable(name = "weekly_popular_course_ta_titles", joinColumns = @JoinColumn(name = "weekly_popular_course_id"))
    @Column(name = "tourist_attraction_title")
    private List<String> touristAttractionNames;
}
