package backend.overhere.service.api.scheduler;

import backend.overhere.service.api.WeeklyPopularCourseService;
import lombok.RequiredArgsConstructor;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class WeeklyPopularCourseScheduler {

    private final WeeklyPopularCourseService weeklyPopularCourseService;
    // 매주 월요일 자정에 실행
    @Scheduled(cron = "0 0 0 ? * MON")
    public void updateWeeklyPopularCourses() {
        weeklyPopularCourseService.updateWeeklyPopularCourses();
    }
}