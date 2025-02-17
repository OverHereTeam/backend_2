package backend.overhere.dto;

import jakarta.validation.constraints.NotNull;
import lombok.Getter;
import lombok.NonNull;
import lombok.Setter;

@Getter
@Setter
public class SignUpRequestDto {
    @NotNull
    private String email;

    @NotNull
    private String nickname;

    @NotNull
    private String role;

    @NotNull
    private String password;
}
