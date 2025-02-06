package backend.overhere.service;

import backend.overhere.entity.User;
import backend.overhere.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
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
}
