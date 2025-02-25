package backend.overhere.service.api;

import backend.overhere.configuration.Jpa.specification.CourseSpecifications;
import backend.overhere.domain.Course;
import backend.overhere.domain.CourseLike;
import backend.overhere.domain.TouristAttraction;
import backend.overhere.domain.TouristAttractionCourse;
import backend.overhere.dto.domain.coursedto.CourseResponseDto;
import backend.overhere.repository.CourseRepository;
import backend.overhere.repository.TouristAttractionRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.context.annotation.Import;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.test.context.ActiveProfiles;

import java.util.*;


import static org.assertj.core.api.Assertions.assertThat;

@DataJpaTest
@ActiveProfiles("test")
@Import({CourseService.class, CourseSpecifications.class})
public class CourseServiceTest {


    @Autowired
    private CourseRepository courseRepository;

    @Autowired
    private TouristAttractionRepository touristAttractionRepository;

    @Autowired
    private CourseService courseService;

    @BeforeEach
    public void setup() {
        // 테스트 간 데이터 간섭을 막기 위해 저장된 데이터를 모두 삭제
        courseRepository.deleteAll();
        touristAttractionRepository.deleteAll();
    }

    @Test
    public void testSaveCoursesAndSearch() {
        // given: 관광지 코스를 의미하는 Course 엔티티 생성
        Course course1 = Course.builder()
                .courseType("관광 코스")
                .title("서울 야경 투어")
                .briefDescription("서울의 대표 야경 코스")
                .overview("서울의 야경을 감상할 수 있는 다양한 장소를 연결한 투어")
                .difficulty("쉬움")
                .distance(3.5)
                .build();

        Course course2 = Course.builder()
                .courseType("관광 코스")
                .title("역사 탐방 코스")
                .briefDescription("한국의 역사를 느낄 수 있는 코스")
                .overview("경복궁, 창덕궁 등 주요 역사적 명소를 포함")
                .difficulty("보통")
                .distance(4.2)
                .build();

        List<Course> courseList = Arrays.asList(course1, course2);
        courseService.saveCourses(courseList);

        // when: 검색어 없이 전체 조회 -> 검색어가 없으면 conjunction() 적용되어 전체 조회됨
        Page<Course> pageAll = courseService.getCourseSearch("", 0, 10);
        assertThat(pageAll.getTotalElements()).isEqualTo(2);

        // when: "서울" 검색 -> "서울 야경 투어"가 매칭되어야 함
        Page<Course> pageSeoul = courseService.getCourseSearch("서울", 0, 10);
        assertThat(pageSeoul.getTotalElements()).isEqualTo(1);
        Course foundCourseSeoul = pageSeoul.getContent().get(0);
        assertThat(foundCourseSeoul.getTitle()).contains("서울");

        // when: "역사" 검색 -> "역사 탐방 코스"가 매칭되어야 함
        Page<Course> pageHistory = courseService.getCourseSearch("역사", 0, 10);
        assertThat(pageHistory.getTotalElements()).isEqualTo(1);
        Course foundCourseHistory = pageHistory.getContent().get(0);
        assertThat(foundCourseHistory.getTitle()).contains("역사");
    }


    @Test
    @DisplayName("좋아요 기반 추천: 상위 N개 코스 조회")
    public void testGetMostLikedCourses() {
        // Given: 세 개의 코스 생성
        Course course1 = Course.builder()
                .courseType("관광 코스")
                .title("서울 야경 투어")
                .briefDescription("서울 야경")
                .difficulty("쉬움")
                .distance(3.5)
                .build();
        // course1에 좋아요 3건 추가
        course1.getCourseLikes().addAll(Arrays.asList(
                CourseLike.builder().course(course1).build(),
                CourseLike.builder().course(course1).build(),
                CourseLike.builder().course(course1).build()
        ));

        Course course2 = Course.builder()
                .courseType("관광 코스")
                .title("역사 탐방 코스")
                .briefDescription("역사 탐방")
                .difficulty("보통")
                .distance(4.2)
                .build();
        // course2에 좋아요 5건 추가
        course2.getCourseLikes().addAll(Arrays.asList(
                CourseLike.builder().course(course2).build(),
                CourseLike.builder().course(course2).build(),
                CourseLike.builder().course(course2).build(),
                CourseLike.builder().course(course2).build(),
                CourseLike.builder().course(course2).build()
        ));

        Course course3 = Course.builder()
                .courseType("관광 코스")
                .title("가나다 코스")
                .briefDescription("가나다")
                .difficulty("어려움")
                .distance(2.0)
                .build();
        // course3에 좋아요 5건 추가
        course3.getCourseLikes().addAll(Arrays.asList(
                CourseLike.builder().course(course3).build(),
                CourseLike.builder().course(course3).build(),
                CourseLike.builder().course(course3).build(),
                CourseLike.builder().course(course3).build(),
                CourseLike.builder().course(course3).build()
        ));

        // 저장
        courseRepository.saveAll(Arrays.asList(course1, course2, course3));

        // When: 좋아요 기반 상위 3개 코스 조회
        List<CourseResponseDto> result = courseService.getMostLikedCourses(0,3);

        // Then:
        // course2와 course3는 좋아요 5건으로 동률인데, 제목 오름차순("가나다 코스" < "역사 탐방 코스")가 적용되어 course3가 먼저 나온다.
        assertThat(result).hasSize(3);
        assertThat(result.get(0).getTitle()).isEqualTo("가나다 코스");
        assertThat(result.get(1).getTitle()).isEqualTo("역사 탐방 코스");
        assertThat(result.get(2).getTitle()).isEqualTo("서울 야경 투어");
    }

    @Test
    @DisplayName("지역 기반 추천: 특정 지역에서 좋아요 수 높은 코스 페이징 조회")
    public void testGetRecommendedCoursesByRegion() {
        // Given:
        // 1. 관광지 생성 (예: 제목에 "서울" 포함)
        TouristAttraction taSeoul = TouristAttraction.builder()
                .title("서울 명소")
                .build();
        touristAttractionRepository.save(taSeoul);

        // 2. 코스 1: "서울 야경 투어", 좋아요 2건, 관광지 연결
        Course course1 = Course.builder()
                .courseType("관광 코스")
                .title("서울 야경 투어")
                .briefDescription("야경 감상")
                .difficulty("쉬움")
                .distance(3.5)
                .build();
        TouristAttractionCourse tac1 = TouristAttractionCourse.builder()
                .touristAttraction(taSeoul)
                .course(course1)
                .orders(1)
                .build();
        course1.getTouristAttractionCourses().add(tac1);
        course1.getCourseLikes().addAll(Arrays.asList(
                CourseLike.builder().course(course1).build()
        ));

        // 3. 코스 2: "역사 탐방 코스", 좋아요 5건, 관광지 연결 (관광지 제목에 "서울" 포함)
        Course course2 = Course.builder()
                .courseType("관광 코스")
                .title("역사 탐방 코스")
                .briefDescription("역사 탐방")
                .difficulty("보통")
                .distance(4.2)
                .build();
        TouristAttractionCourse tac2 = TouristAttractionCourse.builder()
                .touristAttraction(taSeoul)
                .course(course2)
                .orders(2)
                .build();
        course2.getTouristAttractionCourses().add(tac2);
        course2.getCourseLikes().addAll(Arrays.asList(
                CourseLike.builder().course(course2).build(),
                CourseLike.builder().course(course2).build(),
                CourseLike.builder().course(course2).build(),
                CourseLike.builder().course(course2).build(),
                CourseLike.builder().course(course2).build()
        ));

        // 4. 코스 3: "서울 문화 코스", 좋아요 5건, 관광지 연결
        Course course3 = Course.builder()
                .courseType("관광 코스")
                .title("서울 문화 코스")
                .briefDescription("문화 체험")
                .difficulty("보통")
                .distance(3.0)
                .build();
        TouristAttractionCourse tac3 = TouristAttractionCourse.builder()
                .touristAttraction(taSeoul)
                .course(course3)
                .orders(3)
                .build();
        course3.getTouristAttractionCourses().add(tac3);
        course3.getCourseLikes().addAll(Arrays.asList(
                CourseLike.builder().course(course3).build(),
                CourseLike.builder().course(course3).build(),
                CourseLike.builder().course(course3).build(),
                CourseLike.builder().course(course3).build(),
                CourseLike.builder().course(course3).build()
        ));
        assertThat(course1.getCourseLikes().size()).isEqualTo(1);
        assertThat(course2.getCourseLikes().size()).isEqualTo(5);
        assertThat(course3.getCourseLikes().size()).isEqualTo(5);
        // 저장: Cascade 설정에 따라 TouristAttractionCourse와 CourseLike는 함께 저장
        courseRepository.saveAll(Arrays.asList(course1, course2, course3));

        // When: "서울" 지역명을 기준으로 page 0, size 2 페이징 조회
        Pageable pageable = PageRequest.of(0, 2);
        Page<CourseResponseDto> resultPage = courseService.getRecommendedCoursesByRegion("서울", 0, 2);

        // Then:
        // 총 결과 건수가 2건이어야 하며, 좋아요 수 내림차순, 동률일 경우 제목 오름차순 정렬
        // course2와 course3는 좋아요 5건으로 동률 → 제목 "역사 탐방 코스" vs "서울 문화 코스"
        // 오름차순이면 "역사 탐방 코스"가 먼저 나온다.
        assertThat(resultPage.getTotalElements()).isEqualTo(2);
        List<CourseResponseDto> list = resultPage.getContent();
        assertThat(list.get(0).getTitle()).isEqualTo("서울 문화 코스");
        assertThat(list.get(1).getTitle()).isEqualTo("역사 탐방 코스");
    }
}