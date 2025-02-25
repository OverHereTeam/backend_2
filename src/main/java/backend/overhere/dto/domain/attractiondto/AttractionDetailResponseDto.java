package backend.overhere.dto.domain.attractiondto;

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
    private Boolean brailleBlock;
    private Boolean elevator;
    private Boolean guideHuman;
    private Boolean signGuide;
    private Boolean stroller;
    private Boolean lactationroom;

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
