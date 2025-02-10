package backend.overhere.domain;

import jakarta.persistence.*;
import lombok.*;

@Entity
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class NonObstacleInfo {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name="non_obstacle_info_id")
    private Long id;

    @OneToOne(mappedBy = "nonObstacleInfo")
    private TouristAttraction touristAttraction;

    private String helpdog;

    //10번
    private String parking;
    private String wheelchair;
    private String restroom;
    private String audioguide;


}
