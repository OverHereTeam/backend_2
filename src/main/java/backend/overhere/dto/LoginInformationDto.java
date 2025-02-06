package backend.overhere.dto;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class LoginInformationDto {
    private String email;
    private String nickname;

    public LoginInformationDto(String email, String nickname) {
        this.email = email;
        this.nickname = nickname;
    }
}
