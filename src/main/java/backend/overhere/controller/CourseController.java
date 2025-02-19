package backend.overhere.controller;


import backend.overhere.dto.domain.CourseResponseDto;
import backend.overhere.service.api.CourseService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/api/v1/course")
@RequiredArgsConstructor
@Tag(name="코스  API", description = "코스 찾기 기능이 있는 API입니다.")
public class CourseController {

    private final CourseService courseService;

    /**
     * 단순 좋아요 수가 많은 코스를 추천
     * @param limit 상위 몇 개 코스를 가져올지 (기본값 5)
     */
    @Operation(summary = "베스트 코스찾기 ",description = "좋아요가 많은 코스를 5개 반환 limit로 늘릴 수 있음")
    @GetMapping("/like-based")
    public ResponseEntity<List<CourseResponseDto>> recommendCourses(
            @RequestParam(defaultValue = "5") int limit) {

        // 좋아요 순 코스 조회
        List<CourseResponseDto> recommended = courseService.getMostLikedCourses(limit);

        // 결과가 없으면 204 No Content, 있으면 200 OK
        if (recommended.isEmpty()) {
            return ResponseEntity.noContent().build();
        }
        return ResponseEntity.ok(recommended);
    }


     /**
     * 지역별 추천 코스 API
     * 특정 지역 이름(관광지 title 기준)을 파라미터로 받아 해당 지역에 속하는 관광지를 포함한 코스 중,
     * 좋아요 수를 기준으로 정렬하여 페이징된 결과를 반환한다.
     */
    @GetMapping("/recommend/region")
    @Operation(summary = "지역별 코스찾기 ",description = "지역별로 좋아요가 많은 코스를 페이징해서 반환")
    public ResponseEntity<Page<CourseResponseDto>> getRecommendedCoursesByRegion(
            @RequestParam String region,
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "10") int size) {
        Page<CourseResponseDto> result = courseService.getRecommendedCoursesByRegion(region, page, size);
        if (result.isEmpty()) {
            return ResponseEntity.noContent().build();
        }
        return ResponseEntity.ok(result);
    }
}
