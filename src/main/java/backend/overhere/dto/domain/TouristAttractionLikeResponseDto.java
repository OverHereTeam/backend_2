package backend.overhere.dto.domain;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class TouristAttractionLikeResponseDto {
    private Long likeId;
    private Long touristAttractionId;
    private String username;  // 좋아요를 누른 사용자 정보
}
