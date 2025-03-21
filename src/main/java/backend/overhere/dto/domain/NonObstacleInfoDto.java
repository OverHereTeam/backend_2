package backend.overhere.dto.domain;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class NonObstacleInfoDto {
    private Long id;
    private Boolean helpdog;
    private Boolean parking;
    private Boolean wheelchair;
    private Boolean restroom;
    private Boolean audioguide;
    private String exits;
    //private Boolean exits 로 바꿔야함
}
