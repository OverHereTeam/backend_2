package backend.overhere.service.auth;

import backend.overhere.domain.RefreshToken;
import backend.overhere.repository.RefreshTokenRepository;
import backend.overhere.util.JwtUtil;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
//import org.springframework.transaction.annotation.Transactional;

import java.util.Calendar;
import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
@Transactional
@Slf4j
public class RefreshTokenService {

    private final RefreshTokenRepository refreshTokenRepository;

    /**
     * 리프레시 토큰 저장 (기존 토큰 만료 처리 후 새로 저장)
     */
    public RefreshToken createRefreshToken(String userId, String refreshToken) {
        // 기존 유저의 유효한 리프레시 토큰을 모두 만료 처리
        expireAllTokensForUser(userId);

        // 새로운 리프레시 토큰 생성 및 저장
        RefreshToken token = RefreshToken.builder()
                .userId(userId)
                .refresh(refreshToken)
                .expired(false)
                .build();

        return refreshTokenRepository.save(token);
    }

    /**
     * 특정 유저의 기존 리프레시 토큰을 모두 만료 처리
     */
    public void expireAllTokensForUser(String userId) {
        List<RefreshToken> tokens = refreshTokenRepository.findByUserIdAndExpiredFalse(userId);
        if (!tokens.isEmpty()) {
            tokens.forEach(token -> token.setExpired(true));
            refreshTokenRepository.saveAll(tokens);
        }
    }

    /**
     * 리프레시 토큰 검증 및 유효한 토큰 조회
     */
    public Optional<RefreshToken> validateRefreshToken(String userId, String refreshToken) {
        return refreshTokenRepository.findByUserIdAndExpiredFalse(userId)
                .stream()
                .filter(token -> token.getRefresh().equals(refreshToken))
                .findFirst();
    }

    /**
     * 리프레시 토큰으로 조회
     */
    public Optional<RefreshToken> findByRefreshToken(String refreshToken) {
        return refreshTokenRepository.findByRefresh(refreshToken);
    }

    /**
     * 리프레시 토큰 삭제
     */
    public void deleteRefreshToken(String refreshToken) {
        refreshTokenRepository.deleteByRefresh(refreshToken);
    }
}
