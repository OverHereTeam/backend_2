package backend.overhere.service.api;

import backend.overhere.domain.DetailInfo;
import backend.overhere.domain.Gallery;
import backend.overhere.domain.NonObstacleInfo;
import backend.overhere.domain.TouristAttraction;
import backend.overhere.dto.domain.AttractionDetailResponseDto;
import backend.overhere.dto.domain.AttractionInfoResponseDto;
import backend.overhere.dto.domain.GalleryResponseDto;
import backend.overhere.exception.DataAccessException;
import backend.overhere.repository.AttractionRepository;
import backend.overhere.repository.NonObstacleInfoRepository;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
@Transactional
@RequiredArgsConstructor
public class AttractionService {
    private final AttractionRepository attractionRepository;
    private final NonObstacleInfoRepository nonObstacleInfoRepository;


    public AttractionInfoResponseDto loadAttractionInfo(Long touristAttractionId) {
        TouristAttraction touristAttraction = attractionRepository.findById(touristAttractionId)
                .orElseThrow(() -> new DataAccessException("TouristAttraction not found"));

/*        NonObstacleInfo nonObstacleInfo = touristAttraction.getNonObstacleInfo();
        DetailInfo detailInfo = touristAttraction.getDetailInfo();

        return AttractionInfoResponseDto.builder()
                .contentTypeId(touristAttraction.getContentTypeId())
                .contentId(String.valueOf(touristAttraction.getId()))
                .address1(touristAttraction.getAddress1())
                .address2(touristAttraction.getAddress2())
                .tel(touristAttraction.getTel())
                .homepage(touristAttraction.getHomepage())
                .useTime(detailInfo.getUseTime())
                .likeCount((long) touristAttraction.getLikes().size())
                .view(touristAttraction.getView())
                .title(touristAttraction.getTitle())
                .helpDog(nonObstacleInfo.getHelpdog())
                .restRoom(nonObstacleInfo.getRestroom())
                .wheelchair(nonObstacleInfo.getWheelchair())
                .audioGuide(nonObstacleInfo.getAudioguide())
                .parking(nonObstacleInfo.getParking())
                .build();*/

        return AttractionInfoResponseDto.from(touristAttraction);
    }

    public AttractionDetailResponseDto loadAttractionDetail(Long touristAttractionId) {
        TouristAttraction touristAttraction = attractionRepository.findById(touristAttractionId)
                .orElseThrow(() -> new DataAccessException("TouristAttraction not found"));

/*        DetailInfo detailInfo = touristAttraction.getDetailInfo();

        return AttractionDetailResponseDto.builder()
                .contentId(touristAttraction.getId())
                .overView(touristAttraction.getOverview())
                .useFee(detailInfo.getUseFee())
                .brailleBlock(detailInfo.getBraileblock())
                .elevator(detailInfo.getElevator())
                .guideHuman(detailInfo.getGuidehuman())
                .signGuide(detailInfo.getSignguide())
                .stroller(detailInfo.getStroller())
                .lactationroom(detailInfo.getLactationroom())
                .build();*/
        return AttractionDetailResponseDto.from(touristAttraction);
    }


    public List<GalleryResponseDto> loadGallery(Long touristAttractionId) {
        TouristAttraction touristAttraction = attractionRepository.findById(touristAttractionId)
                .orElseThrow(() -> new DataAccessException("TouristAttraction not found"));

        List<Gallery> galleries = touristAttraction.getGalleries();
        if (galleries.isEmpty()) {
            return null;  // 빈 리스트 대신 null 반환
        }

        return galleries.stream()
                .map(GalleryResponseDto::from)  // Gallery → GalleryResponseDto 변환
                .collect(Collectors.toList());
    }





}
