package backend.overhere.service.api;

import backend.overhere.configuration.Jpa.specification.CourseSpecifications;
import backend.overhere.domain.Course;
import backend.overhere.dto.domain.CourseResponseDto;
import backend.overhere.dto.domain.page.CoursePageResponseDto;
import backend.overhere.repository.CourseRepository;
import backend.overhere.repository.TouristAttractionRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;

import java.util.List;

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

     //좋아요 수가 많은 코스 상위 N개를 조회
    public  List<CourseResponseDto> getMostLikedCourses(int limit) {
        Pageable pageable = PageRequest.of(0, limit);
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

}
