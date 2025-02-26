package backend.overhere.service.api;

import backend.overhere.configuration.Jpa.specification.CourseSpecifications;
import backend.overhere.domain.Course;
import backend.overhere.domain.TouristAttraction;
import backend.overhere.dto.domain.attractiondto.TouristAttractionSummaryDto;
import backend.overhere.dto.domain.coursedto.CourseDetailResponse;
import backend.overhere.dto.domain.coursedto.CourseResponseDto;
import backend.overhere.repository.CourseRepository;
import backend.overhere.repository.TouristAttractionRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class CourseService {
    private final CourseRepository courseRepository;
    private final TouristAttractionRepository touristAttractionRepository;
    private final CourseSpecifications courseSpecifications;

    //List 전부 Save
    public void saveCourses(List<Course> courseList) {
        courseRepository.saveAll(courseList);
    }
    //추후 spec추가에 따라 명세를 달리함
    public Page<Course> getCourseSearch( String searchQuery, int page, int size) {
        Pageable pageable = PageRequest.of(page, size);

        Specification<Course> spec = Specification.where(null);
        spec = spec.and(courseSpecifications.searchByQuery(searchQuery));

        return courseRepository.findAll(spec, pageable);
    }
    //코스와 연관된 관광지 리스트
    public CourseDetailResponse getCourseDetail(Long courseId) {
        Course course = courseRepository.findById(courseId).orElse(null);
        if (course == null) {
            // 예외 처리 또는 null 반환 처리
            return null;
        }
        List<TouristAttractionSummaryDto> touristSummaryList = course.getTouristAttractionCourses().stream()
                .map(tac -> {
                    TouristAttraction ta = tac.getTouristAttraction();
                    return TouristAttractionSummaryDto.builder()
                            .touristId(ta.getId())
                            .title(ta.getTitle())                // TouristAttraction 엔티티에 title 필드가 있다고 가정
                            .detailInfo(ta.getOverview())          // 필요한 경우 detailInfo 대신 overview 사용
                            .imageUrl(ta.getThumbnail1())          // 관광지 대표 이미지
                            .nonObstacleInfo(ta.getNonObstacleInfo())
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
                .touristSummary(touristSummaryList)
                .build();
    }

     //좋아요 수가 많은 코스 상위 N개를 조회
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
        Page<Course> coursePage = courseRepository.findAll(
                courseSpecifications.recommendByRegion(region),
                pageable
        );
        return coursePage.map(Course::CoursetoDto);
    }

    public Page<CourseResponseDto> getRecommendedCoursesByAreacode(String areacode, String courseType, int page, int size) {
        Pageable pageable = PageRequest.of(page, size);
        Page<Course> coursePage = courseRepository.findAll(
                courseSpecifications.recommendByAreacodeAndCourseType(areacode, courseType),
                pageable
        );
        return coursePage.map(Course::CoursetoDto);
    }

}
