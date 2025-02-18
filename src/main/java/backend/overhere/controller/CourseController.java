package backend.overhere.controller;


import backend.overhere.dto.domain.CourseResponseDto;
import backend.overhere.service.api.CourseService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/api/v1/course")
@RequiredArgsConstructor
public class CourseController {

    private final CourseService courseService;



    /**
     * 좋아요 수가 많은 코스를 추천
     * @param limit 상위 몇 개 코스를 가져올지 (기본값 5)
     */
    @GetMapping("/recommend")
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

}
