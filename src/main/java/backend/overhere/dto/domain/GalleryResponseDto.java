package backend.overhere.dto.domain;

import backend.overhere.domain.Gallery;
import lombok.AllArgsConstructor;
import lombok.Getter;

@AllArgsConstructor
@Getter
public class GalleryResponseDto {
    private String imageUrl;

    // Entity → DTO 변환 메서드 추가
    public static GalleryResponseDto from(Gallery gallery) {
        return new GalleryResponseDto(gallery.getImageUrl());
    }
}
