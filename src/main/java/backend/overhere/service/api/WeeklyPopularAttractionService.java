package backend.overhere.service.api;

import backend.overhere.domain.WeeklyPopularTouristAttraction;
import backend.overhere.repository.LikeRepository;
import backend.overhere.repository.WeeklyPopularTouristAttractionRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

@Service
@RequiredArgsConstructor
public class WeeklyPopularAttractionService {

    private final LikeRepository likeRepository;
    private final WeeklyPopularTouristAttractionRepository weeklyPopularRepo;

    // 고정된 지역 코드 리스트
    private final List<String> fixedAreaCodes = Arrays.asList(
            "1", "2", "3", "4", "5", "6", "7", "8", "31", "32", "33", "34", "35", "36", "37", "38", "39"
    );
    public void updateWeeklyPopularAttractions() {
        // 지난 일주일 기간 계산
        LocalDateTime endDate = LocalDateTime.now();
        LocalDateTime startDate = endDate.minusWeeks(1);

        // 기존 집계 데이터 초기화
        weeklyPopularRepo.deleteAll();

        List<WeeklyPopularTouristAttraction> popularList = new ArrayList<>();

        // 고정된 지역 코드 리스트 순회
        for (String areaCode : fixedAreaCodes) {
            Pageable top20 = PageRequest.of(0, 20);
            List<Object[]> results = likeRepository.findWeeklyPopularAttractionsByAreaCode(areaCode, startDate, endDate, top20);

            // 결과 배열 구조:
            // [0] tourist_attraction_id
            // [1] area_code
            // [2] title
            // [3] thumbnail1
            // [4] weeklyLikeCount
            // [5] helpdog
            // [6] parking
            // [7] wheelchair
            // [8] restroom
            // [9] audioguide
            for (Object[] row : results) {
                Long touristAttractionId = ((Number) row[0]).longValue();
                String area = (String) row[1];
                String title = (String) row[2];
                String thumbnailUrl = (String) row[3];
                Long likeCount = ((Number) row[4]).longValue();
                Boolean helpdog = (Boolean) row[5];
                Boolean parking = (Boolean) row[6];
                Boolean wheelchair = (Boolean) row[7];
                Boolean restroom = (Boolean) row[8];
                Boolean audioguide = (Boolean) row[9];

                WeeklyPopularTouristAttraction entry = WeeklyPopularTouristAttraction.builder()
                        .areaCode(area)
                        .touristAttractionId(touristAttractionId)
                        .title(title)
                        .thumbnailUrl(thumbnailUrl)
                        .weeklyLikeCount(likeCount)
                        .helpdog(helpdog)
                        .parking(parking)
                        .wheelchair(wheelchair)
                        .restroom(restroom)
                        .audioguide(audioguide)
                        .build();
                popularList.add(entry);
            }
        }
        // 집계 결과 저장
        weeklyPopularRepo.saveAll(popularList);
    }
}

