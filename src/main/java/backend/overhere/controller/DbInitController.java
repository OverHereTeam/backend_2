package backend.overhere.controller;

import backend.overhere.dto.dbInit.response.urlResponse.ResponseDtoUrl3;
import backend.overhere.dto.dbInit.request.RequestDto;
import backend.overhere.entity.TouristAttraction;
import backend.overhere.service.dbInit.DbInitService;
import backend.overhere.service.dbInit.TouristAttractionService;
import backend.overhere.util.Util;
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
@Slf4j
public class DbInitController {

    private final DbInitService dbInitService;
    private final TouristAttractionService touristAttractionService;
    private final Util util;

    //numOfRows, pageNo, serviceKey 동적으로 변경해야됨
    //한번에 초기화
    @GetMapping("/{areaCode}/{numOfRows}/{pageNo}")
    public String initTouristAttraction(@PathVariable String areaCode,@PathVariable String numOfRows, @PathVariable String pageNo) throws UnsupportedEncodingException {
        RequestDto requestDto = RequestDto.builder().areaCode(areaCode).numOfRows(numOfRows).pageNo(pageNo).build();
        ResponseDtoUrl3 responseDtoUrl3 = dbInitService.fetchTouristAttractionData(util.buildEncodedUrl3(requestDto));
        List<TouristAttraction> touristAttractionList = touristAttractionService.toTouristAttractionList(responseDtoUrl3);
        touristAttractionService.saveTouristAttractions(touristAttractionList);
        return "ok";
    }

    @GetMapping("/perOne/{areaCode}/{numOfRows}/{pageNo}")
    public String initDb(@PathVariable String areaCode, @PathVariable String numOfRows, @PathVariable String pageNo) throws UnsupportedEncodingException {
        try {
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

                if(touristAttraction.getContentTypeId().equals(Util.SHOPPING) || touristAttraction.getContentTypeId().equals(Util.ACCOMMODATION)){
                    if(touristAttraction.getContentTypeId().equals(Util.SHOPPING)){
                        log.warn("ContentId={} 는 쇼핑, 스킵함",touristAttraction.getContentId());
                    }
                    else if(touristAttraction.getContentTypeId().equals(Util.ACCOMMODATION)){
                        log.warn("ContentId={} 는 숙소, 스킵함",touristAttraction.getContentId());
                    }
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

    @GetMapping("/retry/{areaCode}/{numOfRows}/{pageNo}")
    public String retryByContentId(@PathVariable String areaCode, @PathVariable String numOfRows, @PathVariable String pageNo) throws UnsupportedEncodingException {
        try {
            //재시도해야할 contentId 하드코딩. 이곳을 바꾸시오.
            List<String> retryContentIdList = new ArrayList<>(Arrays.asList("2842316", "3102167", "3102081", "2738030", "2757033", "2772398", "2015575", "3337718", "2010024", "3044556", "2472724", "3456352", "3446579", "2652445", "2841151", "1181087", "131760", "3102223", "2714276", "126100", "1871270", "403847", "2840343", "1763616", "126162", "2670980", "2782560", "2609839", "2633894", "143126", "2638520", "1754453", "130023", "2610293", "126017", "1058437", "2599737", "126647", "131641", "2010407", "3367497", "130696", "126733", "2732402", "1986664", "2637847", "1923400", "2531514", "1957631", "3044607", "2010235", "143008", "143129", "2876473", "1954014", "3449443", "126227", "126179", "126067", "126134", "2840127", "2716049", "3404355", "2902633", "126095", "127035", "130019", "2814159", "1019843", "127669", "133005", "3449336", "2706622", "317503", "2814160", "127921", "3044608", "1875453", "2790490", "2821443", "2406101", "2501595", "2782636", "2850704", "1910479", "2832313", "132974", "126143", "1999964", "130669", "2599775", "2763768", "130024", "1181485", "129743", "2629570", "2757058", "1965448", "2660035", "2950932", "129207", "2043682", "129430", "2600371", "2597615", "126113", "1846900", "1621189", "3428441", "1433244", "130482", "130016", "2738010", "2771020", "2733119", "232286", "2725113", "2589653", "3346664", "2717337", "1702606", "2609519", "2531372", "2932934", "130894", "126201", "2840458", "2706613", "2996749", "126093", "3044612", "134653", "3049824", "3427320", "1964043", "2841027", "3102085", "3049911", "3442528", "1873982", "3030491", "1963952", "2841536", "3446183", "2017499", "2590647", "1181011", "894027", "2564458", "126126", "1754420", "130028", "129772", "127339", "130310", "2679247", "2436209", "2031666", "2650221", "134983", "1368335", "2912810", "2628004", "2616064", "765272", "2633900", "2610500", "2600313", "1883104", "3456342", "129843", "130020", "130702", "128718", "128765", "127710", "1955053", "2704122", "1131061", "129717", "127698", "894087", "2762521", "130026", "2725120", "2017268", "2638521", "1839077", "2674000", "130029", "1838591", "1778114", "2912274", "2554513", "2606219", "2469220", "2606217", "2671162", "130701", "2619631", "894158", "2633895", "2785776", "705612", "1621252", "130103", "130015", "2716143", "128182", "130314", "129161", "3446972", "2912899", "2832850", "126212", "407291", "3102067", "2009556", "126763", "2724650", "133964", "1245789", "133520", "134644", "1829262", "346655", "2721931", "3447823", "1059119", "130509", "126178", "1839066", "128936", "1755827", "2676283", "1939439", "128714", "250374", "2753977", "130017", "2714724", "3446220", "2842460", "2753398", "988449", "2603509", "126090", "1873769", "130673", "128155", "791432", "2629039", "2629038", "2733381", "2705361", "1779330", "2629036", "2842356", "2721929", "2705820", "2714108", "128026", "2406210", "1882538", "138363", "133521", "2758262", "129025", "3351914", "3101955", "2598962", "2662610", "2721964", "2732161", "126097", "128680", "1028910", "1986948", "3444721", "3452841", "2456795", "126223", "2996003", "2414453", "126020", "142915", "2725101", "134313", "2757018", "3090441", "578954", "126155", "1830280", "1779251", "2616158", "2660019", "2002505", "2729992", "2646267", "2814161", "130101", "2753371", "1399407", "3444315", "2663350", "2554548", "1621437", "1621319", "126021", "130666", "129051", "130846", "578999", "1838602", "2600182", "1883367", "1804600", "1838310", "130021", "2357984", "126092", "2842529", "2842571", "2717548", "2932169", "3345111", "3440184", "2708675", "136641", "2756836", "142894", "142898", "131558", "142893", "2705818", "2756965", "2950402", "778402", "3102075", "128636", "2840546", "3444369", "2638529", "3044644", "130246", "129712", "2911693", "2406280", "131513", "2383570", "2048796", "2660060", "2469112", "1771900", "3044646", "130150", "2780019", "2048454", "1762425", "2603504", "3357395", "2049478", "2554511", "2753376", "134170", "136208", "2756585", "2616061", "403857", "2834353", "129168", "127131", "2614561", "2606232", "2840533", "756132", "3044675", "3044677", "131413", "2633891", "126094", "129142", "2765063", "2760939", "3451999", "127619", "3453420", "1762129", "252656", "126694", "2736397", "129015", "2020977", "2756706", "2770693", "142892", "2017064", "2902799", "2472690", "970667", "2605878", "403845", "2629725", "1041317"));


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
