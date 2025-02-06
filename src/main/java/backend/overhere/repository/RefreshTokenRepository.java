package backend.overhere.repository;

import backend.overhere.entity.RefreshToken;
import jakarta.transaction.Transactional;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface RefreshTokenRepository extends JpaRepository<RefreshToken,Long> {

    void deleteByRefresh(String refresh);

    Optional<RefreshToken> findByUserId(String userId);

    // refresh 값으로 RefreshToken을 조회
    Optional<RefreshToken> findByRefresh(String refresh);

    // userId로 expired 필드가 false인 RefreshToken 리스트 조회
    List<RefreshToken> findByUserIdAndExpiredFalse(String userId);



}
