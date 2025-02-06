package backend.overhere.entity;

import jakarta.persistence.*;
import lombok.*;

@Getter
@Setter
@Entity
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class User {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "user_id")
    private Long id;

    private String email;

    private String nickname;

    private String password;

    //enum
    private String role;

    //Provider Enum
    private String provider;

    //OAuth이면 provider에서의 Id, 일반 로그인이면 null
    private String providerId;

    //회원가입 정책 따라서 필드 선언....
}
