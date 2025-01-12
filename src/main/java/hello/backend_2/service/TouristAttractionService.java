package hello.backend_2.service;

import com.overhere.backend.dao.TouristAttractionRepository;
import com.overhere.backend.domain.NonObstacleInfo;
import com.overhere.backend.domain.TouristAttraction;
import com.overhere.backend.dto.response.ResponseDtoNonObstacleInfo;
import com.overhere.backend.dto.response.ResponseDtoTADetail;
import com.overhere.backend.dto.response.urlResponse.ResponseDtoUrl3;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Stream;

@Service
@RequiredArgsConstructor
public class TouristAttractionService {
    private final TouristAttractionRepository touristAttractionRepository;
    
    @Transactional(readOnly = true)
    public ResponseDtoTADetail findOneDetail(Long touristAttractionId) {
        TouristAttraction findTA = touristAttractionRepository.findById(touristAttractionId)
                .orElseThrow(() -> new RuntimeException("NOT FOUND TOURIST ATTRACTION"));
        NonObstacleInfo findNonObstacleInfo = findTA.getNonObstacleInfo();
        
        // TODO - From 사용하기
        ResponseDtoNonObstacleInfo newNonObstacleInfo = ResponseDtoNonObstacleInfo.builder()
                .helpdog(findNonObstacleInfo.getHelpdog())
                .audioguide(findNonObstacleInfo.getAudioguide())
                .videoguide(findNonObstacleInfo.getVideoguide())
                .exitLocation(findNonObstacleInfo.getExitLocation())
                .publictransport(findNonObstacleInfo.getPublictransport())
                .parking(findNonObstacleInfo.getParking())
                .restroom(findNonObstacleInfo.getRestroom())
                .build();
        
        // TODO - From 사용하기
        return ResponseDtoTADetail.builder()
                .id(touristAttractionId)
                .title(findTA.getTitle())
                .address1(findTA.getAddress1())
                .thumbnail1(findTA.getThumbnail1())
                .thumbnail2(findTA.getThumbnail2())
                .tel(findTA.getTel())
                .nonObstacleInfo(newNonObstacleInfo)
                .build();
    }
    
    // List 전부 Save
    @Transactional
    public void saveTouristAttractions(List<TouristAttraction> touristAttractionList) {
        touristAttractionRepository.saveAll(touristAttractionList);
    }
    
    // Dto 배열 List로 반환
    public List<TouristAttraction> toTouristAttractionList(ResponseDtoUrl3 responseDtoUrl3) {
        List<TouristAttraction> touristAttractionList = new ArrayList<>();
        
        Stream<ResponseDtoUrl3.Item> stream = responseDtoUrl3.getResponse().getBody().getItems().getItem().stream();
        stream.forEach(item -> {
            TouristAttraction touristAttraction = TouristAttraction.builder()
                    .contentId(item.getContentid())
                    .contentTypeId(item.getContenttypeid())
                    .areaCode(item.getAreacode())
                    .cat1(item.getCat1())
                    .cat2(item.getCat2())
                    .cat3(item.getCat3())
                    .thumbnail1(item.getFirstimage())
                    .thumbnail2(item.getFirstimage2())
                    .tel(item.getTel())
                    .title(item.getTitle())
                    .address1(item.getAddr1())
                    .address2(item.getAddr2())
                    .build();
            touristAttractionList.add(touristAttraction);
        });
        
        return touristAttractionList;
    }
}
