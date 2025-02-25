package backend.overhere.service.api.scheduler;

import backend.overhere.service.api.WeeklyPopularAttractionService;
import lombok.RequiredArgsConstructor;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class TouristAttractionScheduler {

    private final WeeklyPopularAttractionService weeklyPopularAttractionService;

    // 매주 월요일 자정에 실행 (크론 표현식: 초 분 시 일 월 요일)
    @Scheduled(cron = "0 0 0 ? * MON")
    public void updateWeeklyPopularTouristAttractions() {
        weeklyPopularAttractionService.updateWeeklyPopularAttractions();
    }
}
