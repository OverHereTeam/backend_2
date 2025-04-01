package backend.overhere.service.api;

import backend.overhere.domain.User;
import backend.overhere.dto.LoginInformationDto;
import backend.overhere.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
@RequiredArgsConstructor
public class UserService {
    private final UserRepository userRepository;

    public Optional<User> findById(Long id){
        return userRepository.findById(id);
    }
    public User findByEmailAndProvider(String email,String provider){
        return userRepository.findByEmailAndProvider(email,provider);
    }

    public LoginInformationDto findUserDtoById(Long id){
        User user = userRepository.findById(id).orElseThrow(() -> new RuntimeException("User not found"));
        return LoginInformationDto.of(user);
    }
}
