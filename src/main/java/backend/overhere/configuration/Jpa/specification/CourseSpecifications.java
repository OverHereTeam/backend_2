package backend.overhere.configuration.Jpa.specification;

import backend.overhere.domain.Course;
import backend.overhere.domain.CourseLike;
import backend.overhere.domain.TouristAttraction;
import backend.overhere.domain.TouristAttractionCourse;
import jakarta.persistence.criteria.*;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Component;
import org.springframework.util.StringUtils;

@Component
public class CourseSpecifications {

    /**
     * 특정 지역(관광지의 title에 region 포함) 조건 및 좋아요 수 필터를 적용한 명세
     * - 관광지의 title에 region 문자열이 포함되어야 한다.
     * - 코스의 좋아요 수가 1건 초과(즉, 최소 2건 이상)인 경우만 선택
     * - 정렬은 좋아요 수 내림차순, 동률이면 코스 제목 오름차순
     */
    public Specification<Course> recommendByRegion(String region) {
        return (root, query, cb) -> {
            // region 조건: TouristAttractionCourse → TouristAttraction 조인을 통해 관광지 제목에 region 포함
            Join<Object, Object> tacJoin = root.join("touristAttractionCourses", JoinType.INNER);
            Join<Object, Object> taJoin = tacJoin.join("touristAttraction", JoinType.INNER);
            Predicate regionPredicate = cb.like(taJoin.get("title"), "%" + region + "%");

            // 서브쿼리를 이용하여 현재 Course에 연결된 CourseLike 수가 1보다 큰지 판별
            Subquery<Long> subquery = query.subquery(Long.class);
            Root<CourseLike> clRoot = subquery.from(CourseLike.class);
            subquery.select(cb.count(clRoot));
            subquery.where(cb.equal(clRoot.get("course"), root));
            Predicate likesPredicate = cb.greaterThan(subquery, 1L);

            // 정렬: 메인 쿼리일 때만 orderBy 적용
            if (query.getResultType().equals(Course.class)) {
                // LEFT JOIN CourseLike (정렬용)
                Join<Object, Object> clJoin = root.join("courseLikes", JoinType.LEFT);
                query.groupBy(root);
                // 정렬 기준: 좋아요 수 내림차순, 동률이면 제목 오름차순
                query.orderBy(cb.desc(cb.count(clJoin.get("id"))), cb.asc(root.get("title")));
            }
            return cb.and(regionPredicate, likesPredicate);
        };
    }

    /**
     * 검색어를 기반으로 Course의 제목(title)과 개요(overview)를 LIKE 조건으로 검색하는 Specification 반환
     */
    public Specification<Course> searchByQuery(String searchQuery) {
        return (root, query, cb) -> {
            if (!StringUtils.hasText(searchQuery)) {
                return cb.conjunction();
            }
            return cb.or(
                    cb.like(root.get("title"), "%" + searchQuery + "%"),
                    cb.like(root.get("overview"), "%" + searchQuery + "%")
            );
        };
    }
}
