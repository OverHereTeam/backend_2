package backend.overhere.dto.domain.faqdto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Builder
@AllArgsConstructor
public class FaqResponseDto {
    private Long faqId;
}
