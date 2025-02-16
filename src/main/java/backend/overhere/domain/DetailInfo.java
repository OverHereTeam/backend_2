package backend.overhere.domain;

import jakarta.persistence.*;
import lombok.*;


@Entity
@Setter
@Getter
@NoArgsConstructor
@Builder
@AllArgsConstructor
public class DetailInfo {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "detail_id")
    private Long id;

/*    //14
    private String usetimeculture;
    private String restdateculture;
    private String usefee;

    //15
    private String eventstartdate;
    private String eventenddate;
    private String usetimefestival;

    //12
    private String usetime;

    //28
    private String openperiod;
    private String usetimeleports;
    private String restdateleports;
    private String usefeeleports;

    //39
    private String opentimefood;
    private String restdatefood;*/

    //------------------------------------------------------
    @Column(columnDefinition = "TEXT")
    private String useTime;
    @Column(columnDefinition = "TEXT")
    private String useFee;



    //nonObstacleInfo에서 가져와 추가해야할 것들

    private Boolean stroller;

    private Boolean elevator;

    private Boolean lactationroom;

    private Boolean signguide;

    private Boolean braileblock;

    private Boolean guidehuman;





}
