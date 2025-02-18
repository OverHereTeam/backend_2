package backend.overhere.service.api;

import backend.overhere.configuration.security.handler.OauthLoginSuccessHandler;
import backend.overhere.domain.Course;
import backend.overhere.repository.CourseRepository;
import backend.overhere.repository.TouristAttractionRepository;
import backend.overhere.util.Util;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.context.annotation.Import;
import org.springframework.data.domain.Page;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.transaction.annotation.Transactional;

import java.util.*;


import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.*;

@DataJpaTest
@ActiveProfiles("test")
@Import(CourseService.class) // 해당 Service 클래스만 스캔하여 빈으로 등록
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
}