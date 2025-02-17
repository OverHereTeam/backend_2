package backend.overhere.controller;

import backend.overhere.common.ResponseStatus;
import backend.overhere.domain.Course;
import backend.overhere.domain.NonObstacleInfo;
import backend.overhere.domain.TouristAttraction;
import backend.overhere.dto.ResponseDto;
import backend.overhere.dto.domain.CourseResponseDto;
import backend.overhere.dto.domain.TouristSearchResponseDto;
import backend.overhere.service.api.CourseService;
import backend.overhere.service.api.NonObstacleInfoService;
import backend.overhere.service.api.TouristAttractionService;
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
@RequiredArgsConstructor
public class SearchController {
    private final TouristAttractionService touristAttractionService;
    private final NonObstacleInfoService nonObstacleInfoService;
    private final CourseService courseService;

//    // 지역, 유형을 가지고 해당 데이터들 페이징 기능
//    @GetMapping("/non-obstacle")
//    public ResponseEntity<ResponseDto<List<TouristSearchResponseDto>>> searchTouristAttractionsByType(
//            @RequestParam ObstacleType type,
//            @RequestParam(defaultValue = "0") int page,
//            @RequestParam(defaultValue = "6") int size) {
//
//        Page<NonObstacleInfo> result = nonObstacleInfoService.getNonObstacleInfoByType(type, page, size);
//        List<TouristSearchResponseDto> responseDtos = convertToSearchResponseDtos(result.getContent());
//        return ResponseDto.settingResponse(HttpStatus.OK, ResponseStatus.SUCCESS, responseDtos);
//    }
    @GetMapping("/tourist")
    public ResponseEntity<ResponseDto<List<TouristSearchResponseDto>>> searchTouristAttractions(
            @RequestParam(required = false) String areacode,
            @RequestParam(required = false) String type,
            @RequestParam(required = false) String searchParam,
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "12") int size) {

        Page<TouristAttraction> result = touristAttractionService.getAttractionSearch(areacode, type, searchParam,page, size);
        List<TouristSearchResponseDto> responseDtos = convertToSearchResponseDtos(result.getContent());
        return ResponseDto.settingResponse(HttpStatus.OK, ResponseStatus.SUCCESS, responseDtos);
    }
    @GetMapping("/course")
    public ResponseEntity<List<CourseResponseDto>> searchCourse(
            @RequestParam String searchQuery,
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "12") int size)
    {
        Page<Course> result = courseService.getCourseSearch(searchQuery, page, size);
        List<CourseResponseDto> dtoList = result.getContent().stream()
                .map(Course::CoursetoDto)
                .toList();
        if (dtoList.isEmpty()) {
            return ResponseEntity.noContent().build(); // 204 No Content 반환
        }
        return ResponseEntity.ok(dtoList); // 200 OK 반환
    }



//    @GetMapping("/tourist-attraction-title")
//    public ResponseEntity<List<TouristSearchResponseDto>> searchTouristAttractionsByTitle(
//            @RequestParam String searchQuery,
//            @RequestParam(defaultValue = "0") int page,
//            @RequestParam(defaultValue = "12") int size) {
//
//        Page<TouristAttraction> result = touristAttractionService.getAttractionsByTitle(searchQuery, page, size);
//        List<TouristSearchResponseDto> responseDtos = convertToSearchResponseDtos(result.getContent());
//        return ResponseEntity.ok(responseDtos);
//    }

    // 페이지 객체를 DtoList로 변형하는 메소드
    private List<TouristSearchResponseDto> convertToSearchResponseDtos(List<?> content) {
        return content.stream().map(item -> {
            // TouristAttraction 객체가 포함된 경우
            if (item instanceof TouristAttraction) {
                TouristAttraction touristAttraction = (TouristAttraction) item;
                return touristAttraction.toSearchResponseDto();
            }
            // NonObstacleInfo 객체가 포함된 경우
            else if (item instanceof NonObstacleInfo) {
                NonObstacleInfo nonObstacleInfo = (NonObstacleInfo) item;
                TouristAttraction touristAttraction = nonObstacleInfo.getTouristAttraction();
                return touristAttraction.toSearchResponseDto();
            }
            return null;
        }).collect(Collectors.toList());
    }


}

