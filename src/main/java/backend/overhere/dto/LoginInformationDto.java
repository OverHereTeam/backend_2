package backend.overhere.dto;

import backend.overhere.domain.User;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class LoginInformationDto {
    private String email;
    private String nickname;
    private String loginType;

    public static LoginInformationDto of (User user) {
        LoginInformationDto dto = new LoginInformationDto();
        dto.email = user.getEmail();
        dto.nickname = user.getNickname();
        dto.loginType = user.getProvider();
        return dto;
    }
}
