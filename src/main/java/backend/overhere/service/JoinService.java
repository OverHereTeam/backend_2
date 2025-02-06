package backend.overhere.service;

import backend.overhere.dto.SignUpRequestDto;
import backend.overhere.entity.User;
import backend.overhere.repository.RefreshTokenRepository;
import backend.overhere.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
@Transactional
public class JoinService {
    private final UserRepository userRepository;
    private final BCryptPasswordEncoder encoder;
    private final RefreshTokenRepository refreshTokenRepository;

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

}
