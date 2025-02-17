package backend.overhere.domain;

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
    private Long id;

    private String courseType;

    private String title;

    @Column(columnDefinition = "TEXT")
    private String briefDescription;

    @Column(columnDefinition = "TEXT")
    private String overView;

    private String difficulty;

    private double distance;

}
