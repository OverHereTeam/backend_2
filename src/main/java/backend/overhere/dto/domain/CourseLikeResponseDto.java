package backend.overhere.dto.domain;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class CourseLikeResponseDto {
    private Long likeId;
    private Long courseId;
    private String username;
}
