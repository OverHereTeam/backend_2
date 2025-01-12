package hello.backend_2.service;

import com.overhere.backend.dto.request.RequestDto;
import com.overhere.backend.dto.response.urlResponse.ResponseDtoUrl10;
import com.overhere.backend.dto.response.urlResponse.ResponseDtoUrl3;
import com.overhere.backend.dto.response.urlResponse.ResponseDtoUrl7;
import com.overhere.backend.dto.response.urlResponse.ResponseDtoUrl9;
import com.overhere.backend.domain.NonObstacleInfo;
import com.overhere.backend.domain.TouristAttraction;
import com.overhere.backend.dao.TouristAttractionRepository;
import com.overhere.backend.util.Util;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.client.WebClient;

import java.io.UnsupportedEncodingException;
import java.net.URI;

@Service
@RequiredArgsConstructor
public class DbInitService {

    private final Util util;
    private final WebClient webClient;
    private final TouristAttractionRepository touristAttractionRepository;

    public void storeDbIndividually(TouristAttraction touristAttraction, RequestDto requestDto) throws UnsupportedEncodingException {
        //돌려서 하나씩 contentId랑 contentTypeId를 뽑아서
        //무장애 정보, 이미지 정보, 소개정보 조회에 각각 api call
        URI uri = util.buildEncodedUrl10(requestDto);
        ResponseDtoUrl10 responseDtoUrl10 = fetchNonObsatcleInfo(uri);

        //여기에 추가 api call...

        //NonObstacleInfo 엔터티 세팅
        NonObstacleInfo nonObstacleInfo = settingNonObstacleInfo(responseDtoUrl10);
        touristAttraction.setNonObstacleInfo(nonObstacleInfo);
        touristAttractionRepository.save(touristAttraction);
    }


    public ResponseDtoUrl3 fetchTouristAttractionData(URI encode) {
        return webClient.get().uri(encode).retrieve().bodyToMono(ResponseDtoUrl3.class).block();
    }

    //무장애정보 api Response Dto 반환
    public ResponseDtoUrl10 fetchNonObsatcleInfo(URI encode){
        return webClient.get().uri(encode).retrieve().bodyToMono(ResponseDtoUrl10.class).block();
    }

    //이미지 정보 api Response Dto 반환
    public ResponseDtoUrl9 fetchGallery(URI encode){
        return webClient.get().uri(encode).retrieve().bodyToMono(ResponseDtoUrl9.class).block();
    }

    //소개정보 조회 api Response Dto 반환
    public ResponseDtoUrl7 fetchIntroduce(URI encode){
        return webClient.get().uri(encode).retrieve().bodyToMono(ResponseDtoUrl7.class).block();
    }

    public NonObstacleInfo settingNonObstacleInfo(ResponseDtoUrl10 responseDtoUrl10){
        NonObstacleInfo nonObstacleInfo;
        ResponseDtoUrl10.Item item = responseDtoUrl10.getResponse().getBody().getItems().getItem().get(0);
        return NonObstacleInfo.builder()
                .parking(item.getParking())
                .route(item.getRoute())
                .publictransport(item.getPublictransport())
                .ticketoffice(item.getTicketoffice())
                .promotion(item.getPromotion())
                .wheelchair(item.getWheelchair())
                .exitLocation(item.getExit())
                .elevator(item.getElevator())
                .restroom(item.getRestroom())
                .auditorium(item.getAuditorium())
                .room(item.getRoom())
                .handicapetc(item.getHandicapetc())
                .braileblock(item.getBraileblock())
                .helpdog(item.getHelpdog())
                .guidehuman(item.getGuidehuman())
                .audioguide(item.getAudioguide())
                .bigprint(item.getBigprint())
                .brailepromotion(item.getBrailepromotion())
                .guidesystem(item.getGuidesystem())
                .blindhandicapetc(item.getBlindhandicapetc())
                .signguide(item.getSignguide())
                .videoguide(item.getVideoguide())
                .hearingroom(item.getHearingroom())
                .hearinghandicapetc(item.getHearinghandicapetc())
                .stroller(item.getStroller())
                .lactationroom(item.getLactationroom())
                .babysparechair(item.getBabysparechair())
                .infantsfamilyetc(item.getInfantsfamilyetc())
                .build();

    }
}
