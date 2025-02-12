package backend.overhere.dto.domain;

import backend.overhere.domain.DetailInfo;
import backend.overhere.domain.TouristAttraction;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

@Builder
@Getter
@Setter
public class AttractionDetailResponseDto {
    private Long contentId;
    private String overView;
    private String useFee;
    private String brailleBlock;
    private String elevator;
    private String guideHuman;
    private String signGuide;
    private String stroller;
    private String lactationroom;

    public static AttractionDetailResponseDto from(TouristAttraction attraction) {
        DetailInfo detailInfo = attraction.getDetailInfo();
        return AttractionDetailResponseDto.builder()
                .contentId(attraction.getId())
                .overView(attraction.getOverview())
                .useFee(detailInfo.getUseFee())
                .brailleBlock(detailInfo.getBraileblock())
                .elevator(detailInfo.getElevator())
                .guideHuman(detailInfo.getGuidehuman())
                .signGuide(detailInfo.getSignguide())
                .stroller(detailInfo.getStroller())
                .lactationroom(detailInfo.getLactationroom())
                .build();
    }

}
