package backend.overhere.dto.domain.attractiondto;

import backend.overhere.domain.TouristAttraction;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

@Builder
@Getter
@Setter
public class  AttractionInfoResponseDto {
    // 관광지 유형
    private String contentTypeId;

    // 관광지 Id
    private String contentId;

    // 관광지 주소
    private String address1;

    // 관광지 세부주소
    private String address2;

    // 전화번호
    private String tel;

    // 홈페이지
    private String homepage;

    // 시간
    private String useTime;

    // 좋아요
    private Long likeCount;

    // 조회수
    private Long view;

    // 관광지 이름
    private String title;

    // 무장애 편의정보
    private Boolean helpDog;

    private Boolean restRoom;

    private Boolean wheelchair;

    private Boolean audioGuide;

    private Boolean parking;

    public static AttractionInfoResponseDto from(TouristAttraction attraction) {
        return AttractionInfoResponseDto.builder()
                .contentTypeId(attraction.getContentTypeId())
                .contentId(String.valueOf(attraction.getId()))
                .address1(attraction.getAddress1())
                .address2(attraction.getAddress2())
                .tel(attraction.getTel())
                .homepage(attraction.getHomepage())
                .useTime(attraction.getDetailInfo().getUseTime())
                .likeCount((long) attraction.getLikes().size())
                .view(attraction.getView())
                .title(attraction.getTitle())
                .helpDog(attraction.getNonObstacleInfo().getHelpdog())
                .restRoom(attraction.getNonObstacleInfo().getRestroom())
                .wheelchair(attraction.getNonObstacleInfo().getWheelchair())
                .audioGuide(attraction.getNonObstacleInfo().getAudioguide())
                .parking(attraction.getNonObstacleInfo().getParking())
                .build();
    }

}
