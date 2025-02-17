package backend.overhere.controller;

import backend.overhere.dto.dbInit.response.urlResponse.ResponseDtoUrl3;
import backend.overhere.dto.dbInit.request.RequestDto;
import backend.overhere.domain.TouristAttraction;
import backend.overhere.service.dbInit.DbInitService;
import backend.overhere.service.dbInit.TouristAttractionService;
import backend.overhere.util.Util;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.io.UnsupportedEncodingException;
import java.net.URI;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

@RestController
@RequestMapping("/api")
@RequiredArgsConstructor
@Tag(name="관광지 DB 초기화 API", description = "공공 API를 통한 로컬 DB 초기화에 대한 설명입니다.")
@Slf4j
public class DbInitController {

    private final DbInitService dbInitService;
    private final TouristAttractionService touristAttractionService;
    private final Util util;

    //numOfRows, pageNo, serviceKey 동적으로 변경해야됨
    //한번에 초기화
    @Operation(summary = "단위 관광지 초기화 ",description = "Path Variable로 받은 관광지의 정보대로 공공API로부터 초기화시킵니다.")
    @GetMapping("/{areaCode}/{numOfRows}/{pageNo}")
    public String initTouristAttraction(@PathVariable String areaCode,@PathVariable String numOfRows, @PathVariable String pageNo) throws UnsupportedEncodingException {
        RequestDto requestDto = RequestDto.builder().areaCode(areaCode).numOfRows(numOfRows).pageNo(pageNo).build();
        ResponseDtoUrl3 responseDtoUrl3 = dbInitService.fetchTouristAttractionData(util.buildEncodedUrl3(requestDto));
        List<TouristAttraction> touristAttractionList = touristAttractionService.toTouristAttractionList(responseDtoUrl3);
        touristAttractionService.saveTouristAttractions(touristAttractionList);
        return "ok";
    }

    @Operation(summary = "다수의 관광지 초기화",description = "Path Variable로 받은 다수의 관광지의 정보대로 공공API로부터 초기화시킵니다.")
    @GetMapping("/perOne/{apiAreaCode}/{numOfRows}/{pageNo}")
    public String initDb(@PathVariable String apiAreaCode, @PathVariable String numOfRows, @PathVariable String pageNo) throws UnsupportedEncodingException {
        try {
            //pageNo에 있는 numOfRows만큼 하나씩 호출
            RequestDto requestDto = RequestDto.builder().areaCode(apiAreaCode).numOfRows(numOfRows).pageNo(pageNo).build();
            URI uri3 = util.buildEncodedUrl3(requestDto);
            log.info("Attraction URI: {}",uri3.toString());
            ResponseDtoUrl3 responseDtoUrl3 = dbInitService.fetchTouristAttractionData(uri3);
            List<TouristAttraction> touristAttractionList = touristAttractionService.toTouristAttractionList(responseDtoUrl3);
            int count = 0;
            //touristAttrcation에서 contentId, contentTypeId 뽑아서 하나씩 저장
            for (TouristAttraction touristAttraction : touristAttractionList) {
                log.info("===================================={}번째 시작=====================================",++count);
                requestDto.setContentId(touristAttraction.getContentId());
                requestDto.setContentTypeId(touristAttraction.getContentTypeId());

                if(touristAttraction.getContentTypeId().equals(Util.SHOPPING) || touristAttraction.getContentTypeId().equals(Util.ACCOMMODATION)){
                    if(touristAttraction.getContentTypeId().equals(Util.SHOPPING)){
                        log.warn("ContentId={} 는 쇼핑, 스킵함",touristAttraction.getContentId());
                    }
                    else if(touristAttraction.getContentTypeId().equals(Util.ACCOMMODATION)){
                        log.warn("ContentId={} 는 숙소, 스킵함",touristAttraction.getContentId());
                    }
                    continue;
                }


                dbInitService.storeDbIndividually(touristAttraction, requestDto);
                log.info("======================================끝============================================");
            }
            log.error("실패 ContentId 리스트 : {} ",Util.failedList.toString());
            log.info("완료");
        } catch (Exception e){
            log.error("TouristAttraction문제");
        }
        return "ok";
    }

    @Operation(summary = "관광지 에러 재시도 초기화",description = "하드코딩으로 저장 실패한 요소를 다시 시도합니다. 재빌드 하세요.")
    @GetMapping("/retry/{areaCode}/{numOfRows}/{pageNo}")
    public String retryByContentId(@PathVariable String areaCode, @PathVariable String numOfRows, @PathVariable String pageNo) throws UnsupportedEncodingException {
        try {
            //재시도해야할 contentId 하드코딩. 이곳을 바꾸시오.
            List<String> retryContentIdList = new ArrayList<>(Arrays.asList());


            //pageNo에 있는 numOfRows만큼 하나씩 호출
            RequestDto requestDto = RequestDto.builder().areaCode(areaCode).numOfRows(numOfRows).pageNo(pageNo).build();
            URI uri3 = util.buildEncodedUrl3(requestDto);
            log.info("Attraction URI: {}",uri3.toString());
            ResponseDtoUrl3 responseDtoUrl3 = dbInitService.fetchTouristAttractionData(uri3);
            List<TouristAttraction> touristAttractionList = touristAttractionService.toTouristAttractionList(responseDtoUrl3);
            int count = 0;
            //touristAttrcation에서 contentId, contentTypeId 뽑아서 하나씩 저장
            for (TouristAttraction touristAttraction : touristAttractionList) {
                log.info("===================================={}번째 시작=====================================",++count);
                requestDto.setContentId(touristAttraction.getContentId());

                if(touristAttraction.getContentTypeId().equals(Util.SHOPPING) && !retryContentIdList.contains(touristAttraction.getContentId())){
                    log.warn("ContentId={} 는 쇼핑, 스킵함",touristAttraction.getContentId());
                    continue;
                }
                requestDto.setContentTypeId(touristAttraction.getContentTypeId());
                dbInitService.storeDbIndividually(touristAttraction, requestDto);
                log.info("======================================끝============================================");
            }
            log.error("실패 ContentId 리스트 : {} ",Util.failedList.toString());
            log.info("완료");
        } catch (Exception e){
            log.error("TouristAttraction문제");
        }
        return "ok";
    }


}
