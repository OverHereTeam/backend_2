package backend.overhere.service.api;

import backend.overhere.domain.*;
import backend.overhere.dto.domain.AttractionBasicResponseDto;
import backend.overhere.dto.domain.SearchCourseResponseDto;
import backend.overhere.dto.domain.TouristSearchResponseDto;
import backend.overhere.repository.CourseLikeRepository;
import backend.overhere.repository.CourseRepository;
import backend.overhere.repository.LikeRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.NoSuchElementException;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
@Transactional
public class MyPageService {
    private final LikeRepository likeRepository;
    private final CourseLikeRepository courseLikeRepository;
    private final CourseRepository courseRepository;

    public List<TouristSearchResponseDto> loadTouristAttractionsByLike(Long userId, int page, int size){
        Pageable pageable = PageRequest.of(page, size);

        // 사용자 좋아요한 목록을 페이징 처리하여 가져옴
        Page<Like> likePage = likeRepository.findByUserId(userId, pageable);

        return likePage.getContent().stream()
                .map(like -> createSearchResponseDto(like.getTouristAttraction()))
                .collect(Collectors.toList());
    }

    public List<SearchCourseResponseDto> loadCourseByLike(Long userId,int page, int size){
        Pageable pageable = PageRequest.of(page, size);

        // 사용자 좋아요한 목록을 페이징 처리하여 가져옴
        Page<CourseLike> likePage = courseLikeRepository.findByUserId(userId, pageable);

        return likePage.getContent().stream()
                .map(like -> createSearchCourseResponseDto(like.getCourse()))
                .collect(Collectors.toList());
    }


    private TouristSearchResponseDto createSearchResponseDto(TouristAttraction touristAttraction) {
        NonObstacleInfo nonObstacleInfo = touristAttraction.getNonObstacleInfo();
        return TouristSearchResponseDto.builder()
                .contentTypeId(touristAttraction.getContentTypeId())
                .title(touristAttraction.getTitle())
                .areaCode(touristAttraction.getAreaCode())
                .contentId(touristAttraction.getId())
                .thumbnailUrl(touristAttraction.getThumbnail1())
                .helpdog(nonObstacleInfo.getHelpdog())
                .restroom(nonObstacleInfo.getRestroom())
                .exits(nonObstacleInfo.getExits())
                .parking(nonObstacleInfo.getParking())
                .audioguide(nonObstacleInfo.getAudioguide())
                .wheelchair(nonObstacleInfo.getWheelchair())
                .build();
    }

    private SearchCourseResponseDto createSearchCourseResponseDto(Course course) {
        // 특정 Course에 연결된 관광지를 가져옴
        List<TouristAttraction> attractions = courseRepository.findTouristAttractionsByCourseId(course.getId());

        // 관광지가 없으면 예외 발생
        if (attractions.isEmpty()) {
            throw new NoSuchElementException("해당 코스에 등록된 관광지가 없습니다.");
        }

        // DTO 변환
        List<AttractionBasicResponseDto> attractionDtos = attractions.stream()
                .map(this::convertToAttractionBasicResponseDto)
                .collect(Collectors.toList());

        // SearchCourseResponseDto 빌드
        return SearchCourseResponseDto.builder()
                .id(course.getId())
                .courseType(course.getCourseType())
                .title(course.getTitle())
                .briefDescription(course.getBriefDescription())
                .distance(course.getDistance())
                .attractions(attractionDtos) // 변환된 DTO 리스트 설정
                .build();
    }

    private AttractionBasicResponseDto convertToAttractionBasicResponseDto(TouristAttraction attraction) {
        return new AttractionBasicResponseDto(attraction.getTitle(),attraction.getContentId());
    }
}
