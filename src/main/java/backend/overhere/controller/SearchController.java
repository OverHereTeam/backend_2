package backend.overhere.controller;

import backend.overhere.common.ResponseStatus;
import backend.overhere.domain.NonObstacleInfo;
import backend.overhere.domain.TouristAttraction;
import backend.overhere.dto.ResponseDto;
import backend.overhere.dto.domain.SearchResponseDto;
import backend.overhere.service.api.NonObstacleInfoService;
import backend.overhere.service.dbInit.TouristAttractionService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/api/v1/search")
@Tag(name="검색 API", description = "각종 검색 API입니다.")
@RequiredArgsConstructor
public class SearchController {
    private final TouristAttractionService touristAttractionService;
    private final NonObstacleInfoService nonObstacleInfoService;

    // 지역, 유형을 가지고 해당 데이터들 페이징 기능
    // 관광지 더 보러가기를 페이지 이동으로 하느냐 스크롤로 하느냐에 따라 달라짐
    @Operation(summary = "무장애 기반 관광지 검색 API",description = "무장애 기반 관광지 검색 API 입니다.")
    @GetMapping("/non-obstacle")
    public ResponseEntity<ResponseDto<List<SearchResponseDto>>> searchTouristAttractionsByType(
            @RequestParam String type,
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "6") int size) {

        Page<NonObstacleInfo> result = nonObstacleInfoService.getNonObstacleInfoByType(type, page, size);
        List<SearchResponseDto> responseDtos = convertToSearchResponseDtos(result.getContent());
        return ResponseDto.settingResponse(HttpStatus.OK, ResponseStatus.SUCCESS, responseDtos);
    }

    @Operation(summary = "관광지 검색 API",description = "지역,유형,검색어를 기반으로 검색합니다.")
    @GetMapping("/tourist-attraction")
    public ResponseEntity<ResponseDto<List<SearchResponseDto>>> searchTouristAttractions(
            @RequestParam(required = false) String areacode,
            @RequestParam(required = false) String type,
            @RequestParam(required = false) String searchParam,
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "12") int size) {

        Page<TouristAttraction> result = touristAttractionService.getAttractionSearch(areacode, type, searchParam,page, size);
        List<SearchResponseDto> responseDtos = convertToSearchResponseDtos(result.getContent());
        return ResponseDto.settingResponse(HttpStatus.OK, ResponseStatus.SUCCESS, responseDtos);
    }

    // 공통된 로직을 처리하는 메서드
    private List<SearchResponseDto> convertToSearchResponseDtos(List<?> content) {
        return content.stream().map(item -> {
            // TouristAttraction 객체가 포함된 경우
            if (item instanceof TouristAttraction) {
                TouristAttraction touristAttraction = (TouristAttraction) item;
                return createSearchResponseDto(touristAttraction);
            }
            // NonObstacleInfo 객체가 포함된 경우
            else if (item instanceof NonObstacleInfo) {
                NonObstacleInfo nonObstacleInfo = (NonObstacleInfo) item;
                TouristAttraction touristAttraction = nonObstacleInfo.getTouristAttraction();
                return createSearchResponseDto(touristAttraction);
            }
            return null;
        }).collect(Collectors.toList());
    }

    // 공통된 DTO 변환 메서드
    private SearchResponseDto createSearchResponseDto(TouristAttraction touristAttraction) {
        NonObstacleInfo nonObstacleInfo = touristAttraction.getNonObstacleInfo();
        return SearchResponseDto.builder()
                .contentTypeId(touristAttraction.getContentTypeId())
                .title(touristAttraction.getTitle())
                .areaCode(touristAttraction.getAreaCode())
                .contentId(touristAttraction.getId())
                .thumbnailUrl(touristAttraction.getThumbnail1())
                .helpdog(nonObstacleInfo.getHelpdog())
                .restroom(nonObstacleInfo.getRestroom())
                .exits(nonObstacleInfo.getExits())
                .parking(nonObstacleInfo.getParking())
                .audioguide(nonObstacleInfo.getAudioguide())
                .wheelchair(nonObstacleInfo.getWheelchair())
                .build();
    }
}

