package backend.overhere.domain;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.util.ArrayList;
import java.util.List;

@Entity
@Getter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class TouristAttraction {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "tourist_attraction_id")
    private Long id;
    
    @OneToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "non_obstacle_info_id")
    private NonObstacleInfo nonObstacleInfo;
    
    @OneToMany(mappedBy = "touristAttraction", cascade = CascadeType.ALL)
    private List<TouristAttractionCourse> nonObstacleInfoList = new ArrayList<>();
    
    private String contentId;
    
    private String contentTypeId;
    
    private String areaCode;
    
    private String cat1;
    
    private String cat2;
    
    private String cat3;
    
    private String thumbnail1;
    
    private String thumbnail2;
    
    private String title;
    
    private String tel;
    
    private String address1;
    
    private String address2;
    
    private Long likeCount;
    
    private Long view;
    
    /* TODO
        private String overview;
        private String homepage;
        private Double mapx; // GPS 경도
        private Double mapy; // GPS 위도
        private int mapLevel;
        private Gallery gallery;
    */
    
    // 편의 메서드
    public void setNonObstacleInfo(NonObstacleInfo nonObstacleInfo) {
        this.nonObstacleInfo = nonObstacleInfo;
        if (nonObstacleInfo != null) {
            nonObstacleInfo.setTouristAttraction(this);
        }
    }
    
}
