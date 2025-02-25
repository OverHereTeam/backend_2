package backend.overhere.dto.domain.coursedto;

import lombok.*;

@Getter
@Setter
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class CourseLikeRequestDto {
    private Long courseId;  // 좋아요를 누를 게시물의 ID
    private Long userId;
}
