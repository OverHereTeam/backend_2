package backend.overhere.dto.domain.coursedto;


import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class CourseObstacleInfo { //원하는 코스찾기용 무장애정보

    private Boolean helpdog;
    //10번
    private Boolean parking;
    private Boolean wheelchair;
    private Boolean restroom;
    private Boolean audioguide;
    private String exits;
}
