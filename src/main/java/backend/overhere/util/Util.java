package backend.overhere.util;


import backend.overhere.dto.request.RequestDto;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.util.UriComponentsBuilder;

import java.io.UnsupportedEncodingException;
import java.net.URI;
import java.net.URLEncoder;
import java.util.ArrayList;
import java.util.List;

@Configuration
public class Util {

    public static final String DOMAIN = "https://apis.data.go.kr/B551011/KorWithService1";

    public static final String ACCOMMODATION ="32";
    public static final String CULTURE_FACILITY ="14";
    public static final String FESTIVAL ="15";
    public static final String LEPORTS ="28";
    public static final String RESTAURANT ="39";
    public static final String TOURIST ="12";
    public static final String SHOPPING = "38";

    public static List<String> failedList = new ArrayList<>();

    @Value("${api.key}")
    private String key;

    //3번 OP code URL Build
    public URI buildEncodedUrl3(RequestDto requestDto) throws UnsupportedEncodingException {
        String encodedKey = URLEncoder.encode(key, "UTF-8");
        return UriComponentsBuilder.fromUriString(DOMAIN+"/areaBasedList1")
                .queryParam("serviceKey",encodedKey)
                .queryParam("arrange", "A")
                .queryParam("_type", "json")
                .queryParam("numOfRows", requestDto.getNumOfRows())
                .queryParam("areaCode", requestDto.getAreaCode())
                .queryParam("pageNo", requestDto.getPageNo())
                .queryParam("MobileOS", "ETC")
                .queryParam("MobileApp", "TEST")
                .build(true)
                .toUri();
    }

    //6번 OP code URL Build
    public URI buildEncodedUrl6(RequestDto requestDto) throws UnsupportedEncodingException {
        String encodedKey = URLEncoder.encode(key, "UTF-8");
        return UriComponentsBuilder.fromUriString(DOMAIN+"/detailCommon1")
                .queryParam("serviceKey",encodedKey)
                .queryParam("defaultYN", "Y") //웹사이트 주소
                .queryParam("areacodeYN","Y") //시군구 코드
                .queryParam("mapinfoYN","Y") //mapx , mapy
                .queryParam("overviewYN","Y") //한줄설명
                .queryParam("_type", "json")
                .queryParam("contentId", requestDto.getContentId())
                .queryParam("MobileOS", "ETC")
                .queryParam("MobileApp", "TEST")
                .build(true)
                .toUri();
    }

    //10번 OP code URL Build
    public URI buildEncodedUrl10(RequestDto requestDto) throws UnsupportedEncodingException{
        String encodedKey = URLEncoder.encode(key, "UTF-8");
        return UriComponentsBuilder.fromUriString(DOMAIN+"/detailWithTour1")
                .queryParam("serviceKey",encodedKey)
                .queryParam("_type", "json")
                .queryParam("contentId", requestDto.getContentId())
                .queryParam("MobileOS", "ETC")
                .queryParam("MobileApp", "TEST")
                .build(true)
                .toUri();
    }

    //9번 OP code URL Build
    public URI buildEncodedUrl9(RequestDto requestDto) throws UnsupportedEncodingException{
        String encodedKey = URLEncoder.encode(key, "UTF-8");
        return UriComponentsBuilder.fromUriString(DOMAIN+"/detailImage1")
                .queryParam("serviceKey",encodedKey)
                .queryParam("_type", "json")
                .queryParam("subImageYN","Y")
                .queryParam("contentId", requestDto.getContentId())
                .queryParam("MobileOS", "ETC")
                .queryParam("MobileApp", "TEST")
                .build(true)
                .toUri();
    }

    //7번 OP code URL Build
    public URI buildEncodedUrl7(RequestDto requestDto) throws UnsupportedEncodingException{
        String encodedKey = URLEncoder.encode(key, "UTF-8");
        return UriComponentsBuilder.fromUriString(DOMAIN+"/detailIntro1")
                .queryParam("serviceKey",encodedKey)
                .queryParam("_type", "json")
                .queryParam("contentId", requestDto.getContentId())
                .queryParam("contentTypeId",requestDto.getContentTypeId())
                .queryParam("MobileOS", "ETC")
                .queryParam("MobileApp", "TEST")
                .build(true)
                .toUri();
    }
}
