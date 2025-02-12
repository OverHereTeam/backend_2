package backend.overhere.repository;

import backend.overhere.domain.TouristAttraction;

import org.springframework.data.domain.Page;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import org.springframework.data.domain.Pageable;

@Repository
public interface AttractionRepository extends JpaRepository<TouristAttraction,Long> {


}
