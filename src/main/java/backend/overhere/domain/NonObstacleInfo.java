package backend.overhere.domain;

import jakarta.persistence.*;
import lombok.*;

@Entity
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class NonObstacleInfo {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name="non_obstacle_info_id")
    private Long id;

    @OneToOne(mappedBy = "nonObstacleInfo")
    private TouristAttraction touristAttraction;

    private String helpdog;
    private String elevator;
    private String wheelchair;
    private String restroom;
    private String audioguide;
/*
    private String stroller;
    private String lactationroom;
    private String signguide;
    private String braileblock;
    private String guidehuman;
    private String route;
    private String publictransport;
    private String ticketoffice;
    private String promotion;
    private String wheelchair;
    private String exitLocation;
    private String restroom;
    private String auditorium;
    private String room;
    private String handicapetc;
    private String audioguide;
    private String bigprint;
    private String brailepromotion;
    private String guidesystem;
    private String blindhandicapetc;
    private String videoguide;
    private String hearingroom;
    private String hearinghandicapetc;
    private String babysparechair;
    private String infantsfamilyetc;
*/

}
