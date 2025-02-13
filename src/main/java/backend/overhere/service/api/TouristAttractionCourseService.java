package backend.overhere.service.api;

import backend.overhere.domain.Course;
import backend.overhere.domain.TouristAttraction;
import backend.overhere.domain.TouristAttractionCourse;
import backend.overhere.repository.CourseRepository;
import backend.overhere.repository.TouristAttractionCourseRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@RequiredArgsConstructor
@Transactional
public class TouristAttractionCourseService {
    private final TouristAttractionCourseRepository touristAttractionCourseRepository;
    private final CourseRepository courseRepository;

    public void createCourseWithAttractions(Course course, List<TouristAttraction> touristAttractions){
        courseRepository.save(course);
        int order =0;
        for (TouristAttraction touristAttraction : touristAttractions) {
            TouristAttractionCourse touristAttractionCourse = TouristAttractionCourse.builder()
                    .touristAttraction(touristAttraction)
                    .course(course)
                    .orders(order++)
                    .build();

            // 중간 테이블인 TouristAttractionCourse를 저장
            touristAttractionCourseRepository.save(touristAttractionCourse);
        }
    }

}
