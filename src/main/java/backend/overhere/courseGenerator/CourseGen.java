package backend.overhere.courseGenerator;

import backend.overhere.domain.Course;
import backend.overhere.domain.TouristAttraction;
import backend.overhere.repository.TouristAttractionRepository;
import backend.overhere.service.api.TouristAttractionCourseService;
import backend.overhere.service.api.TouristAttractionService;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.AllArgsConstructor;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.*;
import java.util.stream.Collectors;

import static backend.overhere.util.Util.*;

@RestController
@RequestMapping("/api/v1/course")
@Tag(name="관광지 DB 초기화 API", description = "공공 API를 통한 로컬 DB 초기화에 대한 설명입니다.")
@RequiredArgsConstructor
@Slf4j
public class CourseGen {
    // 지구의 반지름 (단위: km)
    private static final double R = 6371;

    private static final double THRESHOLD=20;

    private final TouristAttractionCourseService touristAttractionCourseService;
    private final ObjectMapper objectMapper;
    private final GptApiClient gptApiClient;
    private final TouristAttractionService touristAttractionService;
    private final TouristAttractionRepository touristAttractionRepository;
    @Operation(summary = "코스 DB 초기화 API",description = "코스를 관광지 데이터를 기반으로 만들고 Course 테이블의 over_view, distance, course_type, brief_description 데이터를 GPT를 통해 파싱후 자동 저장합니다.")
    @GetMapping("/generator")
    public void coureGen(@RequestParam Integer areacode,@RequestParam int iter) {

        for(int i=0; i<iter; i++) {
            try {
                // 예시 관광지 데이터 (DB에서 로드된 데이터라고 가정)
                List<TouristAttraction> allTouristSpots = new ArrayList<>();

                // 추가적인 관광지들을 DB에서 로드하여 allTouristSpots에 추가할 수 있음
                List<TouristAttraction> attractionList = touristAttractionRepository.findByAreaCode(areacode);


                // 부분집합 선택
                List<TouristAttraction> selectedSpots = selectTouristSpots(attractionList, THRESHOLD);
                System.out.println("Selected Tourist Spots:");
                for (TouristAttraction spot : selectedSpots) {
                    System.out.println("ID: " + spot.getId() + ", x: " + spot.getMapx() + ", y: " + spot.getMapy());
                }

                // 최단 경로 구하기 (그리디 알고리즘)
                List<TouristAttraction> shortestPath = findShortestPath(selectedSpots);
                System.out.println("\nShortest Path:");
                for (TouristAttraction spot : shortestPath) {
                    System.out.println("ID: " + spot.getId() + ", x: " + spot.getMapx() + ", y: " + spot.getMapy());
                }

                String response = gptApiClient.sendRequest(shortestPath);
                if(response!=null){
                    // responseText는 실제 JSON 문자열이므로, 이를 다시 JsonNode로 파싱
                    JsonNode courseDetails = objectMapper.readTree(response);

                    // 각 필드 추출 (****CourseType도 해야되는데 아직 안함*******)
                    String courseBriefDescription = courseDetails.path("courseBriefDescription").asText();
                    String courseOverview = courseDetails.path("courseOverview").asText();
                    String difficulty = courseDetails.path("difficulty").asText();
                    String title = courseDetails.path("title").asText();
                    Course course = Course.builder().title(title).difficulty(difficulty).briefDescription(courseBriefDescription).overview(courseOverview).build();
                    touristAttractionCourseService.createCourseWithAttractions(course,shortestPath);

                }
                else {
                    throw new RuntimeException("response==null");
                }
            }
            catch (Exception e) {
                System.out.println("실패");
                continue;
            }

        }

    }

    @AllArgsConstructor
    static class TouristSpot {
        Long id;
        String title;
        String overView;
        String category;
        double x, y;

    }

    public static double calculateDistance(TouristAttraction spot1, TouristAttraction spot2) {
        double lat1=spot1.getMapx();
        double lon1=spot1.getMapy();

        double lat2=spot2.getMapx();
        double lon2=spot2.getMapy();
        // 위도와 경도를 라디안으로 변환
        lat1 = Math.toRadians(lat1);
        lon1 = Math.toRadians(lon1);
        lat2 = Math.toRadians(lat2);
        lon2 = Math.toRadians(lon2);

        // 위도와 경도의 차이
        double dlat = lat2 - lat1;
        double dlon = lon2 - lon1;

        // Haversine 공식
        double a = Math.sin(dlat / 2) * Math.sin(dlat / 2) +
                Math.cos(lat1) * Math.cos(lat2) *
                        Math.sin(dlon / 2) * Math.sin(dlon / 2);
        double c = 2 * Math.atan2(Math.sqrt(a), Math.sqrt(1 - a));

        // 두 지점 간의 거리
        return R * c;
    }

    // 랜덤으로 관광지 뽑기
    private List<TouristAttraction> selectTouristSpots(List<TouristAttraction> attractionList, double threshold) {
        // 여러 가지 조합을 정의 (각 Map은 contentTypeId와 필요한 개수를 나타냄)
        List<Map<String, Integer>> possibleCombinations = new ArrayList<>();
        
        // 조합 1: TOURIST(2), CULTURE_FACILITY(1), RESTAURANT(1)
        Map<String, Integer> combination1 = new HashMap<>();
        combination1.put(TOURIST, 2);
        combination1.put(CULTURE_FACILITY, 2);
        combination1.put(RESTAURANT, 1);
        
        // 조합 2: TOURIST(2), LEPORTS(1), RESTAURANT(1)
        Map<String, Integer> combination2 = new HashMap<>();
        combination2.put(TOURIST, 2);
        combination2.put(CULTURE_FACILITY, 1);
        combination2.put(LEPORTS, 1);
        combination2.put(RESTAURANT, 1);
        
        // 더 많은 조합을 추가할 수 있음
        possibleCombinations.add(combination1);
        possibleCombinations.add(combination2);
        
        // 랜덤하게 하나의 조합 선택
        Random random = new Random();
        Map<String, Integer> requiredTypes = possibleCombinations.get(random.nextInt(possibleCombinations.size()));
        
        // contentTypeId별로 관광지 그룹화
        Map<String, List<TouristAttraction>> groupedAttractions = attractionList.stream()
                .collect(Collectors.groupingBy(attraction ->
                        String.valueOf(attraction.getContentTypeId())));
        
        // 선택된 조합에 따라 관광지 선택
        List<TouristAttraction> selectedSpots = new ArrayList<>();
        
        // 기준점이 될 첫 번째 관광지 선택
        String firstType = requiredTypes.keySet().iterator().next();
        List<TouristAttraction> firstTypeAttractions = groupedAttractions.get(firstType);
        if (firstTypeAttractions == null || firstTypeAttractions.isEmpty()) {
            return null;
        }
        TouristAttraction baseSpot = firstTypeAttractions.get(random.nextInt(firstTypeAttractions.size()));
        selectedSpots.add(baseSpot);
        
        // 나머지 관광지 선택 (거리 제한 적용)
        for (Map.Entry<String, Integer> entry : requiredTypes.entrySet()) {
            String contentTypeId = entry.getKey();
            int count = entry.getValue();
            
            if (contentTypeId.equals(firstType)) {
                count--; // 이미 하나 선택했으므로 카운트 감소
                if (count == 0) continue;
            }
            
            List<TouristAttraction> availableAttractions = groupedAttractions.get(contentTypeId);
            if (availableAttractions == null || availableAttractions.isEmpty()) {
                return null;
            }
            
            // threshold 거리 내의 관광지들만 필터링
            List<TouristAttraction> nearbyAttractions = availableAttractions.stream()
                    .filter(attraction -> calculateDistance(baseSpot, attraction) <= threshold)
                    .collect(Collectors.toList());
            
            if (nearbyAttractions.size() < count) {
                log.warn("Not enough nearby attractions for contentTypeId: {}", contentTypeId);
                return null;
            }
            
            // 거리 내의 관광지들 중에서 랜덤 선택
            List<TouristAttraction> tempList = new ArrayList<>(nearbyAttractions);
            for (int i = 0; i < count; i++) {
                int randomIndex = random.nextInt(tempList.size());
                selectedSpots.add(tempList.get(randomIndex));
                tempList.remove(randomIndex);
            }
        }
        
        // 선택된 관광지들의 순서를 랜덤하게 섞기
        Collections.shuffle(selectedSpots);
        
        return selectedSpots;
    }

    // 가장 가까운 경로 구하기 (그리디 알고리즘)
    private static List<TouristAttraction> findShortestPath(List<TouristAttraction> subset) {
        List<TouristAttraction> path = new ArrayList<>();
        // 첫 번째 관광지를 랜덤하게 선택
        TouristAttraction current = getRandomSpot(subset);
        path.add(current);
        subset.remove(current);

        while (!subset.isEmpty()) {
            TouristAttraction nearest = null;
            double minDist = Double.MAX_VALUE;

            for (TouristAttraction spot : subset) {
                double dist = calculateDistance(current, spot);
                if (dist < minDist) {
                    minDist = dist;
                    nearest = spot;
                }
            }

            path.add(nearest);
            current = nearest;
            subset.remove(nearest);
        }

        return path;
    }

    // 랜덤으로 관광지를 선택하는 메서드
    private static TouristAttraction getRandomSpot(List<TouristAttraction> subset) {
        Random rand = new Random();
        int randomIndex = rand.nextInt(subset.size());  // 0부터 subset.size()-1까지의 랜덤 인덱스
        return subset.get(randomIndex);
    }

    // 기존 메서드 수정
    private List<TouristSpot> convertToTouristSpots(List<TouristAttraction> attractions) {
        return attractions.stream()
                .map(attraction -> new TouristSpot(
                        attraction.getId(),
                        attraction.getTitle(),
                        attraction.getOverview(),
                        String.valueOf(attraction.getContentTypeId()),  // contentTypeId도 포함
                        attraction.getMapx(),
                        attraction.getMapy()
                ))
                .collect(Collectors.toList());
    }

}
