package backend.overhere.service.api;

import backend.overhere.domain.Course;
import backend.overhere.domain.CourseLike;
import backend.overhere.domain.User;
import backend.overhere.dto.domain.CourseLikeRequestDto;
import backend.overhere.dto.domain.CourseLikeResponseDto;
import backend.overhere.repository.CourseRepository;
import backend.overhere.repository.CourseLikeRepository;
import backend.overhere.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class CourseLikeService {

    private final UserRepository userRepository;
    private final CourseLikeRepository courseLikeRepository;
    private final CourseRepository courseRepository;

    // 좋아요 추가 (생성)
    public CourseLikeResponseDto addCourseLike(CourseLikeRequestDto requestDto) {
        if (isCourseLikedByUser(requestDto.getUserId(), requestDto.getCourseId())) {
            throw new IllegalArgumentException("Like already exists");
        }

        User user = userRepository.findById(requestDto.getUserId())
                .orElseThrow(() -> new IllegalArgumentException("User not found"));
        Course course = courseRepository.findById(requestDto.getCourseId())
                .orElseThrow(() -> new IllegalArgumentException("Course not found"));

        CourseLike like = new CourseLike();
        like.setUser(user);
        like.setCourse(course);
        courseLikeRepository.save(like);

        return like.liketoCourseDto();
    }

    // 좋아요 여부 확인
    @Transactional(readOnly = true)
    public boolean isCourseLikedByUser(Long userId, Long courseId) {
        User user = userRepository.findById(userId).orElse(null);
        Course course = courseRepository.findById(courseId).orElse(null);
        return user != null && course != null
                && courseLikeRepository.existsByUserAndCourse(user, course);

    }
}
