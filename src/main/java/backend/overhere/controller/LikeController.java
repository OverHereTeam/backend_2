package backend.overhere.controller;

import backend.overhere.configuration.security.userDetails.CustomUserDetails;
import backend.overhere.dto.domain.CourseLikeRequestDto;
import backend.overhere.dto.domain.CourseLikeResponseDto;
import backend.overhere.dto.domain.TouristAttractionLikeRequestDto;
import backend.overhere.dto.domain.TouristAttractionLikeResponseDto;
import backend.overhere.service.api.LikeService;
import io.swagger.v3.oas.annotations.Operation;
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
    @Operation(summary = "관광지 좋아요 추가 API",description = "관광지 좋아요 추가 API 입니다.")
    @PostMapping("/touristAttraction")
    public ResponseEntity<TouristAttractionLikeResponseDto> addTouristAttractionLike(@RequestBody TouristAttractionLikeRequestDto requestDto,
                                                                                     @AuthenticationPrincipal CustomUserDetails userDetails) {
        // Spring Security 컨텍스트에서 얻은 사용자 정보를 requestDto에 설정
        requestDto.setUserId(userDetails.getId());
        TouristAttractionLikeResponseDto response = likeService.addTouristAttractionLike(requestDto);
        return ResponseEntity.status(HttpStatus.CREATED).body(response);
    }

    // 사용자별 좋아요 전체 조회 (GET: /api/v1/likes)
    @GetMapping("/touristAttraction")
    @Operation(summary = "관광지 좋아요 전체 조회 API",description = "관광지 좋아요 전체 조회 API 입니다.")
    public ResponseEntity<List<TouristAttractionLikeResponseDto>> getTouristAttractionLikes(@AuthenticationPrincipal CustomUserDetails userDetails) {
        List<TouristAttractionLikeResponseDto> likes = likeService.loadTouristAttractionLikeAllByUserId(userDetails.getId());
        return ResponseEntity.ok(likes);
    }

    // 좋아요 업데이트 (PUT: /api/v1/likes/{touristAttractionId})
    @Operation(summary = "관광지 좋아요 업데이트 API",description = "관광지 좋아요 업데이트 API 입니다.")
    @PutMapping("/{touristAttractionId}")
    public ResponseEntity<TouristAttractionLikeResponseDto> updateTouristAttractionLikes(@PathVariable Long touristAttractionId,
                                                                                         @AuthenticationPrincipal CustomUserDetails userDetails) {
        TouristAttractionLikeRequestDto requestDto = new TouristAttractionLikeRequestDto();
        requestDto.setUserId(userDetails.getId());
        requestDto.setTouristAttractionId(touristAttractionId);
        TouristAttractionLikeResponseDto response = likeService.updateTouristAttractionLike(requestDto);
        // 업데이트 결과에 따라 response가 null일 수 있음 (예: 삭제된 경우)
        return ResponseEntity.ok(response);
    }

    // 좋아요 삭제 (DELETE: /api/v1/likes/{touristAttractionId})
    @Operation(summary = "관광지 좋아요 삭제 API",description = "관광지 좋아요 삭제 API 입니다.")
    @DeleteMapping("/{touristAttractionId}")
    public ResponseEntity<Void> deleteTouristAttractionLikes(@PathVariable Long touristAttractionId,
                                                             @AuthenticationPrincipal CustomUserDetails userDetails) {
        TouristAttractionLikeRequestDto requestDto = new TouristAttractionLikeRequestDto();
        requestDto.setUserId(userDetails.getId());
        requestDto.setTouristAttractionId(touristAttractionId);
        likeService.deleteTouristAttractionLike(requestDto);
        return ResponseEntity.noContent().build();
    }

    // 좋아요 추가 (POST: /api/v1/likes)
    @Operation(summary = "코스 좋아요 추가 API",description = "코스 좋아요 추가 API 입니다.")
    @PostMapping("/course")
    public ResponseEntity<CourseLikeResponseDto> addCourseLike(@RequestBody CourseLikeRequestDto requestDto, @AuthenticationPrincipal CustomUserDetails userDetails) {
        // Spring Security 컨텍스트에서 얻은 사용자 정보를 requestDto에 설정
        requestDto.setUserId(userDetails.getId());
        CourseLikeResponseDto response = likeService.addCourseLike(requestDto);
        return ResponseEntity.status(HttpStatus.CREATED).body(response);
    }
}
