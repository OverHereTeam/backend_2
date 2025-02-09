package backend.overhere.service.api;

import backend.overhere.domain.Like;
import backend.overhere.domain.TouristAttraction;
import backend.overhere.domain.User;
import backend.overhere.dto.domain.LikeResponseDto;
import backend.overhere.repository.LikesRepository;
import backend.overhere.repository.UserRepository;
import backend.overhere.repository.TouristAttractionRepository;
import backend.overhere.dto.domain.LikeRequestDto;
import org.springframework.transaction.annotation.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.NoSuchElementException;

@Service
@RequiredArgsConstructor
@Transactional
public class LikeService {

    private final LikesRepository likesRepository;
    private final UserRepository userRepository;
    private final TouristAttractionRepository touristAttractionRepository;

    // 좋아요 추가 (생성)
    public LikeResponseDto addLike(LikeRequestDto requestDto) {
        if (isLikedByUser(requestDto.getUserId(), requestDto.getTouristAttractionId())) {
            throw new IllegalArgumentException("Like already exists");
        }

        User user = userRepository.findById(requestDto.getUserId())
                .orElseThrow(() -> new IllegalArgumentException("User not found"));
        TouristAttraction touristAttraction = touristAttractionRepository.findById(requestDto.getTouristAttractionId())
                .orElseThrow(() -> new IllegalArgumentException("Tourist Attraction not found"));

        Like like = new Like();
        like.setUser(user);
        like.setTouristAttraction(touristAttraction);
        likesRepository.save(like);

        return like.liketoDto();
    }

    // 좋아요 전체 조회 (읽기 전용 트랜잭션)
    @Transactional(readOnly = true)
    public List<LikeResponseDto> loadLikeAllByUserId(Long userId) {
        User user = userRepository.findById(userId)
                .orElseThrow(() -> new NoSuchElementException("User not found"));
        List<Like> likes = user.getLikes();
        List<LikeResponseDto> dtoList = new ArrayList<>();
        for (Like like : likes) {
            dtoList.add(like.liketoDto());
        }
        return dtoList;
    }

    // 좋아요 업데이트 (예시: 토글 형식 - 좋아요가 있으면 삭제, 없으면 추가)
    public LikeResponseDto updateLike(LikeRequestDto requestDto) {
        if (isLikedByUser(requestDto.getUserId(), requestDto.getTouristAttractionId())) {
            // 이미 좋아요가 존재하면 삭제 후, 반환값은 삭제 전의 데이터 혹은 null 처리
            deleteLike(requestDto);
            return null; // 삭제 후에는 좋아요가 없는 상태
        } else {
            // 좋아요가 없으면 추가
            return addLike(requestDto);
        }
    }

    // 좋아요 삭제
    public void deleteLike(LikeRequestDto requestDto) {
        Like like = likesRepository.findByUserIdAndTouristAttractionId(
                requestDto.getUserId(),
                requestDto.getTouristAttractionId()
        ).orElseThrow(() -> new IllegalArgumentException("Like not found"));
        likesRepository.delete(like);
    }

    // 좋아요 여부 확인
    @Transactional(readOnly = true)
    public boolean isLikedByUser(Long userId, Long touristAttractionId) {
        User user = userRepository.findById(userId).orElse(null);
        TouristAttraction touristAttraction = touristAttractionRepository.findById(touristAttractionId).orElse(null);
        return user != null && touristAttraction != null
                && likesRepository.existsByUserAndTouristAttraction(user, touristAttraction);
    }
}
