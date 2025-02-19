package backend.overhere.service.api;

import backend.overhere.domain.Course;
import backend.overhere.domain.CourseLike;
import backend.overhere.domain.Like;
import backend.overhere.domain.User;
import backend.overhere.dto.domain.CourseLikeRequestDto;
import backend.overhere.dto.domain.CourseLikeResponseDto;
import backend.overhere.dto.domain.TouristAttractionLikeRequestDto;
import backend.overhere.dto.domain.TouristAttractionLikeResponseDto;
import backend.overhere.repository.CourseRepository;
import backend.overhere.repository.CourseLikeRepository;
import backend.overhere.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;
import java.util.NoSuchElementException;

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

    // 좋아요 전체 조회 (읽기 전용 트랜잭션)
    @Transactional(readOnly = true)
    public List<CourseLikeResponseDto> loadCourseLikeAllByUserId(Long userId) {
        User user = userRepository.findById(userId)
                .orElseThrow(() -> new NoSuchElementException("User not found"));
        List<CourseLike> likes = user.getCourseLikes();
        List<CourseLikeResponseDto> dtoList = new ArrayList<>();
        for (CourseLike like : likes) {
            dtoList.add(like.liketoCourseDto());
        }
        return dtoList;
    }

    // 좋아요 업데이트 (예시: 토글 형식 - 좋아요가 있으면 삭제, 없으면 추가)
    public CourseLikeResponseDto updateCourseLike(CourseLikeRequestDto requestDto) {
        if (isCourseLikedByUser(requestDto.getUserId(), requestDto.getCourseId())) {
            // 이미 좋아요가 존재하면 삭제 후, 반환값은 삭제 전의 데이터 혹은 null 처리
            deleteCourseLike(requestDto);
            return null; // 삭제 후에는 좋아요가 없는 상태
        } else {
            // 좋아요가 없으면 추가
            return addCourseLike(requestDto);
        }
    }

    // 좋아요 삭제
    public void deleteCourseLike(CourseLikeRequestDto requestDto) {
        CourseLike like = courseLikeRepository.findByUserIdAndCourseId(
                requestDto.getUserId(),
                requestDto.getCourseId()
        ).orElseThrow(() -> new IllegalArgumentException("Like not found"));
        courseLikeRepository.delete(like);
    }


}
