package backend.overhere.service.api;

import backend.overhere.domain.Course;
import backend.overhere.domain.NonObstacleInfo;
import backend.overhere.domain.TouristAttraction;
import backend.overhere.domain.TouristAttractionCourse;
import backend.overhere.dto.domain.NonObstacleInfoDto;
import backend.overhere.dto.domain.attractiondto.TouristAttractionSummaryDto;
import backend.overhere.dto.domain.coursedto.CourseDetailResponse;
import backend.overhere.dto.domain.coursedto.CourseResponseDto;
import backend.overhere.dto.domain.coursedto.NonObstacleRequestDto;
import backend.overhere.repository.CourseRepository;
import backend.overhere.repository.TouristAttractionRepository;
import backend.overhere.repository.CourseRepositoryCustom;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class CourseService {
    private final CourseRepository courseRepository;
    private final TouristAttractionRepository touristAttractionRepository;

    // List 전부 Save
    public void saveCourses(List<Course> courseList) {
        courseRepository.saveAll(courseList);
    }

    // QueryDSL을 사용한 검색
    public Page<Course> getCourseSearch(String searchQuery, int page, int size) {
        Pageable pageable = PageRequest.of(page, size);
        List<Course> courses = courseRepository.searchByQuery(searchQuery);
        return new PageImpl<>(courses, pageable, courses.size());
    }

    // 코스와 연관된 관광지 리스트
    public CourseDetailResponse getCourseDetail(Long courseId) {
        Course course = courseRepository.findById(courseId).orElse(null);
        if (course == null) {
            return null;
        }

        // 지역 코드 가져오기
        Integer areaCode = course.getTouristAttractionCourses().stream()
                .findFirst()
                .map(tac -> tac.getTouristAttraction().getAreaCode())
                .orElse(null);

        String region = TouristAttractionService.convertAreaCodeToRegion(areaCode);

        // 썸네일 URL 찾기 - 첫 번째 유효한 썸네일을 사용
        String thumbnailUrl = "";
        for (TouristAttractionCourse tac : course.getTouristAttractionCourses()) {
            TouristAttraction ta = tac.getTouristAttraction();
            if (ta != null && ta.getThumbnail1() != null && !ta.getThumbnail1().isEmpty()) {
                thumbnailUrl = ta.getThumbnail1();
                break;
            }
        }

        List<TouristAttractionSummaryDto> touristSummaryList = course.getTouristAttractionCourses().stream()
                .map(tac -> {
                    TouristAttraction ta = tac.getTouristAttraction();
                    NonObstacleInfo noi = ta.getNonObstacleInfo();

                    NonObstacleInfoDto noiDto = NonObstacleInfoDto.builder()
                            .id(noi.getId())
                            .helpdog(noi.getHelpdog())
                            .parking(noi.getParking())
                            .wheelchair(noi.getWheelchair())
                            .restroom(noi.getRestroom())
                            .audioguide(noi.getAudioguide())
                            .exits(noi.getExits())
                            .build();

                    return TouristAttractionSummaryDto.builder()
                            .touristId(ta.getId())
                            .title(ta.getTitle())
                            .detailInfo(ta.getOverview())
                            .imageUrl(ta.getThumbnail1())
                            .nonObstacleInfo(noiDto)
                            .build();
                })
                .collect(Collectors.toList());

        return CourseDetailResponse.builder()
                .courseId(course.getId())
                .courseType(course.getCourseType())
                .title(course.getTitle())
                .distance(course.getDistance())
                .overView(course.getOverview())
                .difficulty(course.getDifficulty())
                .likeNumber((long) course.getCourseLikes().size())
                .view(course.getView())
                .thumbnailUrl(thumbnailUrl)  // 찾은 썸네일 URL 설정
                .region(region)
                .touristSummary(touristSummaryList)
                .build();
    }

    // 좋아요 수가 많은 코스 상위 N개를 조회
    public List<CourseResponseDto> getMostLikedCourses(int page, int size) {
        Pageable pageable = PageRequest.of(page, size);
        List<Course> courseList = courseRepository.findMostLikedCourses(pageable);

        // 엔티티 -> DTO 변환
        return courseList.stream()
                .map(Course::CoursetoDto)
                .toList();
    }

    public Page<CourseResponseDto> getRecommendedCoursesByRegion(String region, int page, int size) {
        Pageable pageable = PageRequest.of(page, size);
        List<Course> courses = courseRepository.recommendByRegion(region);
        return new PageImpl<>(courses.stream().map(Course::CoursetoDto).collect(Collectors.toList()), pageable, courses.size());
    }

    // 지역코드와 코스타입을 기반으로 추천 코스 조회
    public Page<CourseResponseDto> getRecommendedCoursesByAreacode(Integer areacode, String courseType, int page, int size) {
        Pageable pageable = PageRequest.of(page, size);
        List<Course> courses = courseRepository.recommendByAreacodeAndCourseType(areacode, courseType);
        return new PageImpl<>(courses.stream().map(Course::CoursetoDto).collect(Collectors.toList()), pageable, courses.size());
    }

    // 지역코드와 비장애물 정보를 기반으로 추천 코스 조회
    public Page<CourseResponseDto> getRecommendedCoursesByAreacodeAndNonobstacle(Integer areacode, NonObstacleRequestDto nonObstacleRequestDto, int page, int size) {
        Pageable pageable = PageRequest.of(page, size);
        List<Course> courses = courseRepository.recommendByAreacodeAndNonobstacle(areacode, nonObstacleRequestDto);
        return new PageImpl<>(courses.stream().map(Course::CoursetoDto).collect(Collectors.toList()), pageable, courses.size());
    }
}