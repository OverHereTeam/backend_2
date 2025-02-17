package backend.overhere.repository;

import backend.overhere.domain.Inquiry;
import backend.overhere.domain.Notice;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface InquiryRepository extends JpaRepository<Inquiry, Integer> {

    Page<Inquiry> findByUserIdOrderByCreatedAtDesc(Long userId,Pageable pageable);
}
