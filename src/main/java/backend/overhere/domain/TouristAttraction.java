package backend.overhere.domain;

import backend.overhere.dto.domain.TouristSearchResponseDto;
import backend.overhere.dto.domain.attractiondto.TouristAttractionSummaryDto;
import jakarta.persistence.*;
import lombok.*;

import java.util.ArrayList;
import java.util.List;

@Entity
@Getter
@NoArgsConstructor
@AllArgsConstructor
@Setter
@Builder
public class TouristAttraction {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name="tourist_attraction_id")
    private Long id;

    @OneToOne(cascade = CascadeType.ALL)
    @JoinColumn(name="non_obstacle_info_id")
    private NonObstacleInfo nonObstacleInfo;

    @Builder.Default
    @OneToMany(mappedBy = "touristAttraction", cascade = CascadeType.ALL)
    private List<Gallery> galleries = new ArrayList<>();

    @Builder.Default
    @OneToMany(mappedBy = "touristAttraction", cascade = CascadeType.ALL)
    private List<TouristAttractionCourse> nonObstacleInfoList = new ArrayList<>();

    @OneToOne(cascade = CascadeType.ALL)
    @JoinColumn(name="detail_id")
    private DetailInfo detailInfo;

    @OneToMany(mappedBy = "touristAttraction", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<Like> likes = new ArrayList<>();

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

    private Long view;

    @Column(columnDefinition = "TEXT")
    private String homepage;

    private Double mapx;

    private Double mapy;

    private String sigungucode;

    @Column(columnDefinition = "TEXT")
    private String overview;

    //편의 메서드
    public void setNonObstacleInfo(NonObstacleInfo nonObstacleInfo){
        this.nonObstacleInfo=nonObstacleInfo;
        if(nonObstacleInfo!=null){
            nonObstacleInfo.setTouristAttraction(this);
        }
    }

    public void addGalleries(Gallery gallery){
        this.galleries.add(gallery);
        gallery.setTouristAttraction(this);
    }

    public void addLike(Like like) {
        this.likes.add(like);
        like.setTouristAttraction(this);
    }

    public void removeLike(Like like) {
        this.likes.remove(like);
        like.setTouristAttraction(null);
    }

    public TouristSearchResponseDto toSearchResponseDto() {
        return TouristSearchResponseDto.builder()
                .contentTypeId(this.contentTypeId)
                .title(this.title)
                .areaCode(this.areaCode)
                .overView(this.overview)
                .contentId(this.id)
                .thumbnailUrl(this.thumbnail1)
                .build();
    }
    public TouristAttractionSummaryDto toSummaryDto() {
        return TouristAttractionSummaryDto.builder()
                .touristId(this.id)
                .title(this.title)
                .detailInfo(this.overview)
                .imageUrl(this.thumbnail1)
                .build();
    }

}

