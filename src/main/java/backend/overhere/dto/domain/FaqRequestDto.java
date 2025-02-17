package backend.overhere.dto.domain;

import jakarta.persistence.Lob;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class FaqRequestDto {
    private String title;

    @Lob
    private String content;
}
