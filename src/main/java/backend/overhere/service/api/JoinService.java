package backend.overhere.service.api;

import backend.overhere.domain.CourseLike;
import backend.overhere.dto.SignUpRequestDto;
import backend.overhere.domain.User;
import backend.overhere.repository.*;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@RequiredArgsConstructor
@Transactional
public class JoinService {
    private final UserRepository userRepository;
    private final BCryptPasswordEncoder encoder;
    private final RefreshTokenRepository refreshTokenRepository;
    private final LikeRepository likeRepository;
    private final CourseRepository courseRepository;
    private final CourseLikeRepository courseLikeRepository;

    public void join(SignUpRequestDto dto){
        User user = new User();
        user.setRole(dto.getRole());
        user.setProvider("LOCAL");
        user.setEmail(dto.getEmail());
        user.setNickname(dto.getNickname());
        user.setPassword(encoder.encode(dto.getPassword()));
        userRepository.save(user);
    }

    public void logout(String refreshToken){
        //refreshToken으로 엔티티에서 refresh 필드값 기준으로 찾아와서 해당 토큰 DB에서 제거
        refreshTokenRepository.deleteByRefresh(refreshToken);
    }

    @Transactional
    public void withdrawUser(Long userId) {
        // 1. 유저 조회
        User user = userRepository.findById(userId)
                .orElseThrow(() -> new RuntimeException("해당 유저가 존재하지 않습니다."));

        // 2. TouristAttraction Like 삭제
        likeRepository.deleteAllByUser(user);

        // 3. CourseLike 삭제 전, courseId 목록 추출
        List<CourseLike> courseLikes = courseLikeRepository.findAllByUser(user);
        List<Long> courseIdsToDelete = courseLikes.stream()
                .map(cl -> cl.getCourse().getId())
                .distinct()
                .toList();

        // CourseLike 먼저 삭제
        courseLikeRepository.deleteAllByUser(user);

        // 4. 해당 유저가 좋아요한 Course 삭제
        courseRepository.deleteAllByIdIn(courseIdsToDelete);

        // 5. 유저 정보 변경
        user.setEmail("탈퇴한 회원입니다.");
        user.setNickname("탈퇴한 회원입니다.");

        userRepository.save(user); // @Transactional이라 생략 가능
    }


}
