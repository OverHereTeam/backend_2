package backend.overhere.service.api;

import backend.overhere.domain.WeeklyPopularCourse;
import backend.overhere.dto.domain.coursedto.WeeklyPopularCourseResponseDto;
import backend.overhere.repository.CourseLikeRepository;
import backend.overhere.repository.WeeklyPopularCourseRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class WeeklyPopularCourseService {

    private final CourseLikeRepository courseLikeRepository;
    private final WeeklyPopularCourseRepository weeklyPopularCourseRepo;

    // 집계 업데이트 메서드 (매주 실행)
    public void updateWeeklyPopularCourses() {
        LocalDateTime endDate = LocalDateTime.now();
        LocalDateTime startDate = endDate.minusWeeks(1);

        // 기존 집계 데이터 초기화
        weeklyPopularCourseRepo.deleteAll();

        // 상위 10개 코스 집계 (Pageable로 상위 10개만 조회)
        Pageable top10 = PageRequest.of(0, 10);
        List<Object[]> results = courseLikeRepository.findWeeklyPopularCourses(startDate, endDate, top10);

        List<WeeklyPopularCourse> popularList = new ArrayList<>();

        // 결과 배열 구조:
        // [0] course_id, [1] course_type, [2] title, [3] weeklyLikeCount, [4] touristAttractionTitles
        for (Object[] row : results) {
            Long courseId = ((Number) row[0]).longValue();
            String courseType = (String) row[1];
            String courseTitle = (String) row[2];
            Long weeklyLikeCount = ((Number) row[3]).longValue();
            String attractionsStr = (String) row[4];

            // 쉼표로 구분된 문자열을 리스트로 변환 (null 또는 빈 문자열이면 빈 리스트)
            List<String> attractionNames = (attractionsStr != null && !attractionsStr.isEmpty()) ?
                    Arrays.asList(attractionsStr.split(", ")) : new ArrayList<>();

            WeeklyPopularCourse entry = WeeklyPopularCourse.builder()
                    .courseId(courseId)
                    .courseType(courseType)
                    .title(courseTitle)
                    .weeklyLikeCount(weeklyLikeCount)
                    .touristAttractionNames(attractionNames)
                    .build();

            popularList.add(entry);
        }
        // 집계 결과 저장 (이미 상위 10개만 저장됨)
        weeklyPopularCourseRepo.saveAll(popularList);
    }

    // 인기 코스 반환 메서드
    public List<WeeklyPopularCourseResponseDto> getPopularCourses() {
        List<WeeklyPopularCourse> courses = weeklyPopularCourseRepo.findAllByOrderByWeeklyLikeCountDescTitleAsc();
        return courses.stream()
                .map(entity -> WeeklyPopularCourseResponseDto.builder()
                        .courseId(entity.getCourseId())
                        .courseType(entity.getCourseType())
                        .title(entity.getTitle())
                        .weeklyLikeCount(entity.getWeeklyLikeCount())
                        .touristAttractionNames(entity.getTouristAttractionNames())
                        .build())
                .collect(Collectors.toList());
    }
}