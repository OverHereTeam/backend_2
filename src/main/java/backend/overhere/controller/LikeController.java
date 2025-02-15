package backend.overhere.controller;

import backend.overhere.configuration.security.userDetails.CustomUserDetails;
import backend.overhere.dto.ResponseDto;
import backend.overhere.common.ResponseStatus;
import backend.overhere.dto.domain.LikeRequestDto;
import backend.overhere.dto.domain.LikeResponseDto;
import backend.overhere.service.api.LikeService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

import java.util.List;


@RestController
@RequestMapping("/api/v1/likes")
@RequiredArgsConstructor
@Tag(name="회원관리 API", description = "회원가입, 로그아웃, Access Token 재발급 관련 API입니다.")
public class LikeController {

    private final LikeService likeService;

    // 좋아요 추가 (POST: /api/v1/likes)
    @Operation(summary = "좋아요 추가 API",description = "좋아요 추가 API 입니다.")
    @PostMapping
    public ResponseEntity<LikeResponseDto> addLike(@RequestBody LikeRequestDto requestDto,
                                                   @AuthenticationPrincipal CustomUserDetails userDetails) {
        // Spring Security 컨텍스트에서 얻은 사용자 정보를 requestDto에 설정
        requestDto.setUserId(userDetails.getId());
        LikeResponseDto response = likeService.addLike(requestDto);
        return ResponseEntity.status(HttpStatus.CREATED).body(response);
    }

    // 사용자별 좋아요 전체 조회 (GET: /api/v1/likes)
    @GetMapping
    @Operation(summary = "좋아요 전체 조회 API",description = "좋아요 전체 조회 API 입니다.")
    public ResponseEntity<List<LikeResponseDto>> getLikes(@AuthenticationPrincipal CustomUserDetails userDetails) {
        List<LikeResponseDto> likes = likeService.loadLikeAllByUserId(userDetails.getId());
        return ResponseEntity.ok(likes);
    }

    // 좋아요 업데이트 (PUT: /api/v1/likes/{touristAttractionId})
    @Operation(summary = "좋아요 업데이트 API",description = "좋아요 업데이트 API 입니다.")
    @PutMapping("/{touristAttractionId}")
    public ResponseEntity<LikeResponseDto> updateLike(@PathVariable Long touristAttractionId,
                                                      @AuthenticationPrincipal CustomUserDetails userDetails) {
        LikeRequestDto requestDto = new LikeRequestDto();
        requestDto.setUserId(userDetails.getId());
        requestDto.setTouristAttractionId(touristAttractionId);
        LikeResponseDto response = likeService.updateLike(requestDto);
        // 업데이트 결과에 따라 response가 null일 수 있음 (예: 삭제된 경우)
        return ResponseEntity.ok(response);
    }

    // 좋아요 삭제 (DELETE: /api/v1/likes/{touristAttractionId})
    @Operation(summary = "좋아요 삭제 API",description = "좋아요 삭제 API 입니다.")
    @DeleteMapping("/{touristAttractionId}")
    public ResponseEntity<Void> deleteLike(@PathVariable Long touristAttractionId,
                                           @AuthenticationPrincipal CustomUserDetails userDetails) {
        LikeRequestDto requestDto = new LikeRequestDto();
        requestDto.setUserId(userDetails.getId());
        requestDto.setTouristAttractionId(touristAttractionId);
        likeService.deleteLike(requestDto);
        return ResponseEntity.noContent().build();
    }
}
