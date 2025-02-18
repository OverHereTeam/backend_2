package backend.overhere.service.api;

import backend.overhere.domain.Course;
import backend.overhere.domain.Course;
import backend.overhere.repository.AttractionRepository;
import backend.overhere.repository.CourseRepository;
import backend.overhere.repository.TouristAttractionRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import java.util.List;

@Service
@RequiredArgsConstructor
public class CourseService {
    private final CourseRepository courseRepository;
    private final TouristAttractionRepository touristAttractionRepository;

    //List 전부 Save
    public void saveCourses(List<Course> courseList) {
        courseRepository.saveAll(courseList);
    }
    //추후 spec추가에 따라 명세를 달리함
    public Page<Course> getCourseSearch( String searchQuery, int page, int size) {
        Pageable pageable = PageRequest.of(page, size);

        Specification<Course> spec = Specification.where(null);
        spec = spec.and(hasSearchQuery(searchQuery));

        return courseRepository.findAll(spec, pageable);
    }
    private Specification<Course> hasSearchQuery(String searchQuery ) {
        return (root, query, criteriaBuilder) -> {
            if (!StringUtils.hasText(searchQuery)) {
                return criteriaBuilder.conjunction(); // 검색어가 없으면 항상 참
            }
            return criteriaBuilder.or(
                    criteriaBuilder.like(root.get("title"), "%" + searchQuery + "%"),
                    criteriaBuilder.like(root.get("overview"), "%" + searchQuery + "%")
            );
        };
    }

}
