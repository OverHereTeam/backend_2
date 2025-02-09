package backend.overhere.service.dbInit;

import backend.overhere.dto.dbInit.request.RequestDto;
import backend.overhere.dto.dbInit.response.urlResponse.ResponseDtoUrl10;
import backend.overhere.dto.dbInit.response.urlResponse.ResponseDtoUrl3;
import backend.overhere.dto.dbInit.response.urlResponse.ResponseDtoUrl6;
import backend.overhere.dto.dbInit.response.urlResponse.ResponseDtoUrl9;
import backend.overhere.dto.dbInit.response.urlResponse.url7TypeDto.*;
import backend.overhere.domain.DetailInfo;
import backend.overhere.domain.Gallery;
import backend.overhere.domain.NonObstacleInfo;
import backend.overhere.domain.TouristAttraction;
import backend.overhere.repository.TouristAttractionRepository;
import backend.overhere.util.Util;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.reactive.function.client.WebClient;

import java.io.UnsupportedEncodingException;
import java.net.URI;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Stream;

@Service
@RequiredArgsConstructor
@Slf4j
public class DbInitService {

    private final Util util;
    private final WebClient webClient;
    private final TouristAttractionRepository touristAttractionRepository;

    @Transactional
    public void storeDbIndividually(TouristAttraction touristAttraction, RequestDto requestDto) throws UnsupportedEncodingException {
        boolean flagUrl10 = false;
        boolean flagUrl9 = false;
        boolean flagUrl7 = false;
        boolean flagUrl6 = false;
        //돌려서 하나씩 contentId랑 contentTypeId를 뽑아서
        //무장애 정보, 이미지 정보, 소개정보 조회에 각각 api call
        TouristAttraction ta = touristAttractionRepository.findTouristAttractionByContentId(touristAttraction.getContentId());
        if(ta!=null){
            //이미 DB에 저장되어있으면 스킵
            log.warn("{} 중복",touristAttraction.getContentId());
            return;
        }
        try {
            URI uri10 = util.buildEncodedUrl10(requestDto);
            log.info("무장애정보 {}",uri10.toString());
            ResponseDtoUrl10 responseDtoUrl10 = fetchNonObsatcleInfo(uri10);
            flagUrl10=true;


            URI uri9 = util.buildEncodedUrl9(requestDto);
            log.info("이미지정보 {}",uri9.toString());
            ResponseDtoUrl9 responseDtoUrl9 = fetchGallery(uri9);
            flagUrl9=true;

            //여기에 추가 api call...

            URI uri7 = util.buildEncodedUrl7(requestDto);
            log.info("소개정보 {}",uri7.toString());
            ResponseDtoUrl7 responseDtoUrl7 = fetchDetail(uri7, requestDto.getContentTypeId());
            flagUrl7=true;

            URI uri6 = util.buildEncodedUrl6(requestDto);
            log.info("공통정보조회 {}",uri6.toString());
            ResponseDtoUrl6 responseDtoUrl6 = fetchTouristAttraction2Data(uri6);
            flagUrl6=true;


            //homepage , mapx, mapy , sigungucode, overview 세팅
            settingTouristAttraction(responseDtoUrl6,touristAttraction);

            //NonObstacleInfo 엔터티 세팅
            NonObstacleInfo nonObstacleInfo = settingNonObstacleInfo(responseDtoUrl10);
            touristAttraction.setNonObstacleInfo(nonObstacleInfo);

            //DetailInfo 엔터티 세팅
            settingDetailInfo(responseDtoUrl7,responseDtoUrl10,touristAttraction);

            //각각의 touristAttraction에 대한 Gallery 엔터티 리스트 세팅
            settingAndAddGallery(responseDtoUrl9, touristAttraction);

            //저장
            touristAttractionRepository.save(touristAttraction);
        } catch (Exception e){
            log.error(e.toString());
            log.error("파싱 문제 발견 ContentId: {}",touristAttraction.getContentId());
            Util.failedList.add(touristAttraction.getContentId());
            if(flagUrl10==false){
                log.error("무장애정보 문제");
            }
            if(flagUrl10==true && flagUrl9==false){
                log.error("이미지정보 문제");
            }
            if(flagUrl10==true && flagUrl9==true && flagUrl7==false){
                log.error("소개정보 문제");
            }
            if(flagUrl10==true && flagUrl9==true && flagUrl7==true && flagUrl6==false){
                log.error("공통조회정보 문제");
            }
            if(flagUrl10==true && flagUrl9==true && flagUrl7==true&& flagUrl6==true){
                log.error("Setting 문제");
            }
        }
    }


    public ResponseDtoUrl3 fetchTouristAttractionData(URI encode) {
        return webClient.get().uri(encode).retrieve().bodyToMono(ResponseDtoUrl3.class).block();
    }

    public ResponseDtoUrl6 fetchTouristAttraction2Data(URI encode){
        return webClient.get().uri(encode).retrieve().bodyToMono(ResponseDtoUrl6.class).block();
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
    public ResponseDtoUrl7 fetchDetail(URI encode, String typeId){
        if(typeId.equals(Util.TOURIST)){
            return webClient.get().uri(encode).retrieve().bodyToMono(TouristDto.class).block();
        }
        else if(typeId.equals(Util.ACCOMMODATION)){
            return webClient.get().uri(encode).retrieve().bodyToMono(AccommodationDto.class).block();
        }
        else if(typeId.equals(Util.CULTURE_FACILITY)){
            return webClient.get().uri(encode).retrieve().bodyToMono(CultureFacilityDto.class).block();
        }
        else if(typeId.equals(Util.LEPORTS)){
            return webClient.get().uri(encode).retrieve().bodyToMono(LeportsDto.class).block();
        }
        else if(typeId.equals(Util.FESTIVAL)){
            return webClient.get().uri(encode).retrieve().bodyToMono(FestivalDto.class).block();
        }
        else if(typeId.equals(Util.RESTAURANT)){
            return webClient.get().uri(encode).retrieve().bodyToMono(RestaurantDto.class).block();
        }
        else {
            log.error("ContentType id {} 해당 없음",typeId);
            return null;
        }
    }

    public NonObstacleInfo settingNonObstacleInfo(ResponseDtoUrl10 responseDtoUrl10){
        ResponseDtoUrl10.Item item = responseDtoUrl10.getResponse().getBody().getItems().getItem().get(0);
        return NonObstacleInfo.builder()
                .elevator(emptyToNull(item.getElevator()))
                .restroom(emptyToNull(item.getRestroom()))
                .helpdog(emptyToNull(item.getHelpdog()))
                .audioguide(emptyToNull(item.getAudioguide()))
                .wheelchair(emptyToNull(item.getWheelchair()))
                .build();

    }

    private String emptyToNull(String value) {
        return (value == null || value.isEmpty()) ? null : value;
    }


    public List<Gallery> settingAndAddGallery(ResponseDtoUrl9 responseDtoUrl9,TouristAttraction touristAttraction) {
        // items나 item이 null인 경우 빈 리스트 반환
        if (responseDtoUrl9.getResponse().getBody().getItems() == null) {
            return new ArrayList<>();
        }

        Stream<ResponseDtoUrl9.Item> stream = responseDtoUrl9.getResponse().getBody().getItems().getItem().stream();
        List<Gallery> galleryList = new ArrayList<>();
        stream.forEach(item -> {
            Gallery gallery = Gallery.builder()
                    .touristAttraction(touristAttraction)
                    .imageUrl(item.getOriginimgurl())
                    .build();
            touristAttraction.addGalleries(gallery);
            galleryList.add(gallery);
        });
        return galleryList;

    }

    public TouristAttraction settingTouristAttraction(ResponseDtoUrl6 responseDtoUrl6,TouristAttraction touristAttraction){
        ResponseDtoUrl6.Item item = responseDtoUrl6.getResponse().getBody().getItems().getItem().get(0);
        touristAttraction.setHomepage(item.getHomepage());
        touristAttraction.setMapx(item.getMapx());
        touristAttraction.setMapy(item.getMapy());
        touristAttraction.setSigungucode(item.getSigungucode());
        touristAttraction.setOverview(item.getOverview());
        return touristAttraction;
    }

    public DetailInfo settingDetailInfo(ResponseDtoUrl7 responseDtoUrl7, ResponseDtoUrl10 responseDtoUrl10, TouristAttraction touristAttraction){
        String typeId = touristAttraction.getContentTypeId();
        ResponseDtoUrl10.Item item2 = responseDtoUrl10.getResponse().getBody().getItems().getItem().get(0);
        DetailInfo detailInfo;
        if(typeId.equals(Util.TOURIST)){
            TouristDto dto = (TouristDto) responseDtoUrl7;
            TouristDto.Items temp = dto.getResponse().getBody().getItems();
            if(temp==null){
                return null;
            }
            TouristDto.Item item = temp.getItem().get(0);
             detailInfo = DetailInfo.builder()
                     .useTime(item.getUsetime())
                     .stroller(item2.getStroller())
                     .elevator(item2.getElevator())
                     .lactationroom(item2.getLactationroom())
                     .signguide(item2.getSignguide())
                     .braileblock(item2.getBraileblock())
                     .guidehuman(item2.getGuidehuman())
                     .build();
        }

        else if(typeId.equals(Util.CULTURE_FACILITY)){
            CultureFacilityDto dto = (CultureFacilityDto) responseDtoUrl7;
            CultureFacilityDto.Items temp = dto.getResponse().getBody().getItems();
            if(temp==null){
                return null;
            }
            CultureFacilityDto.Item item = temp.getItem().get(0);
            detailInfo=DetailInfo.builder()
                    .useTime(item.getUsetimeculture()+"\n"+item.getRestdateculture())
                    .useFee(item.getUsefee())
                    .stroller(item2.getStroller())
                    .elevator(item2.getElevator())
                    .lactationroom(item2.getLactationroom())
                    .signguide(item2.getSignguide())
                    .braileblock(item2.getBraileblock())
                    .guidehuman(item2.getGuidehuman())
                    .build();

        }
        else if(typeId.equals(Util.LEPORTS)){
            LeportsDto dto = (LeportsDto) responseDtoUrl7;
            LeportsDto.Items temp = dto.getResponse().getBody().getItems();
            if(temp==null){
                return null;
            }
            LeportsDto.Item item = temp.getItem().get(0);
            detailInfo=DetailInfo.builder()
                    .useTime(item.getOpenperiod()+"\n"+item.getUsetimeleports()+"\n"+item.getRestdateleports())
                    .useFee(item.getUsefeeleports())
                    .stroller(item2.getStroller())
                    .elevator(item2.getElevator())
                    .lactationroom(item2.getLactationroom())
                    .signguide(item2.getSignguide())
                    .braileblock(item2.getBraileblock())
                    .guidehuman(item2.getGuidehuman())
                    .build();

        }
        else if(typeId.equals(Util.FESTIVAL)){
            FestivalDto dto = (FestivalDto) responseDtoUrl7;
            FestivalDto.Items temp = dto.getResponse().getBody().getItems();
            if(temp==null){
                return null;
            }
            FestivalDto.Item item = temp.getItem().get(0);
            detailInfo=DetailInfo.builder()
                    .useTime(item.getEventstartdate()+"\n"+item.getEventenddate())
                    .useFee(item.getUsetimefestival())
                    .stroller(item2.getStroller())
                    .elevator(item2.getElevator())
                    .lactationroom(item2.getLactationroom())
                    .signguide(item2.getSignguide())
                    .braileblock(item2.getBraileblock())
                    .guidehuman(item2.getGuidehuman())
                    .build();

        }
        else if(typeId.equals(Util.RESTAURANT)){
            RestaurantDto dto = (RestaurantDto) responseDtoUrl7;
            RestaurantDto.Items temp = dto.getResponse().getBody().getItems();
            if(temp==null){
                return null;
            }
            RestaurantDto.Item item = temp.getItem().get(0);
            detailInfo=DetailInfo.builder()
                    .useTime(item.getOpentimefood()+"\n"+item.getRestdatefood())
                    .stroller(item2.getStroller())
                    .elevator(item2.getElevator())
                    .lactationroom(item2.getLactationroom())
                    .signguide(item2.getSignguide())
                    .braileblock(item2.getBraileblock())
                    .guidehuman(item2.getGuidehuman())
                    .build();
        }
        else {
            log.error("ContentType id {} 해당 없음",typeId);
            return null;
        }
        touristAttraction.setDetailInfo(detailInfo);
        return detailInfo;
    }
}

