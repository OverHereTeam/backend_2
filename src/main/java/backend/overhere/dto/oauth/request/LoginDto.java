package backend.overhere.dto.oauth.request;

import lombok.Data;

@Data
public class LoginDto {
    String password;
    String email;
}
