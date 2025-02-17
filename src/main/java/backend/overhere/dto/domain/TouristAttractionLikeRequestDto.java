package backend.overhere.dto.domain;

import lombok.*;

@Getter
@Setter
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class TouristAttractionLikeRequestDto {
    private Long touristAttractionId;  // 좋아요를 누를 게시물의 ID
    private Long userId;  // 좋아요를 누르는 사용자의 ID
}