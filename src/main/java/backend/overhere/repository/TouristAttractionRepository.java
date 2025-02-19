package backend.overhere.repository;

import backend.overhere.domain.TouristAttraction;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface TouristAttractionRepository extends JpaRepository<TouristAttraction,Long> , JpaSpecificationExecutor<TouristAttraction> {
    public TouristAttraction findTouristAttractionByContentId(Long contentId);

    // area_code를 기반으로 데이터를 가져오는 메서드 (페이징 포함)
    Page<TouristAttraction> findByAreaCode(Integer areaCode, Pageable pageable);

    List<TouristAttraction> findByAreaCode(Integer areaCode);

    Page<TouristAttraction> findByContentTypeId(String type, Pageable pageable);

    Page<TouristAttraction> findByAreaCodeAndContentTypeId(Integer areaCode, String type,Pageable pageable);

    // 이름순 내림차순으로 정렬하면서 페이징 처리 (메서드 이름을 통한 쿼리 유추)
    Page<TouristAttraction> findAllByOrderByTitleDesc(Pageable pageable);

}
