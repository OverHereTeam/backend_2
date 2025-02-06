package backend.overhere.repository;

import backend.overhere.entity.TouristAttraction;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface TouristAttractionRepository extends JpaRepository<TouristAttraction,Long> {
    public TouristAttraction findTouristAttractionByContentId(String contentId);
}
