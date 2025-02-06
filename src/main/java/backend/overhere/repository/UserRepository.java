package backend.overhere.repository;

import backend.overhere.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Repository
public interface UserRepository extends JpaRepository<User,Long> {
    User findByEmailAndProvider(String email,String provider);
    User findByProviderAndProviderId(String provider, String providerId);
    User findByEmail(String email);

}
