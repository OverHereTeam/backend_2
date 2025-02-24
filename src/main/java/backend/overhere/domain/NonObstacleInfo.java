package backend.overhere.domain;

import jakarta.persistence.*;
import lombok.*;

@Entity
@Data
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

    private String parking;
    private String route;
    private String publictransport;
    private String ticketoffice;
    private String promotion;
    private String wheelchair;
    private String exitLocation;
    private String elevator;
    private String restroom;
    private String auditorium;
    private String room;
    private String handicapetc;
    private String braileblock;
    private String helpdog;
    private String guidehuman;
    private String audioguide;
    private String bigprint;
    private String brailepromotion;
    private String guidesystem;
    private String blindhandicapetc;
    private String signguide;
    private String videoguide;
    private String hearingroom;
    private String hearinghandicapetc;
    private String stroller;
    private String lactationroom;
    private String babysparechair;
    private String infantsfamilyetc;

}
