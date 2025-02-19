package backend.overhere.controller;

import backend.overhere.common.ResponseStatus;
import backend.overhere.dto.ResponseDto;
import backend.overhere.dto.domain.AttractionDetailResponseDto;
import backend.overhere.dto.domain.AttractionInfoResponseDto;
import backend.overhere.dto.domain.GalleryResponseDto;
import backend.overhere.service.api.AttractionService;
import backend.overhere.service.api.NonObstacleInfoService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.hibernate.query.Page;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/v1/attraction")
@RequiredArgsConstructor
@Tag(name="관광지 API", description = "관광지 API에 대한 설명입니다.")
public class AttractionController {
    //tourist_attraction
    //non_obstacle_info
    private final AttractionService attractionService;

    @Operation(summary = "관광지 기본 정보",description = "Path Variable로 받은 관광지의 기본 정보를 응답합니다.")
    @GetMapping("/{touristAttractionId}/info")
    public ResponseEntity<AttractionInfoResponseDto> getTouristAttractionInfo(@PathVariable Long touristAttractionId){
        AttractionInfoResponseDto response = attractionService.loadAttractionInfo(touristAttractionId);
        return ResponseEntity.ok(response);
    }

    @Operation(summary = "관광지 상세 정보",description = "Path Variable로 받은 관광지의 상세 정보를 응답합니다.")
    @GetMapping("/{touristAttractionId}/detail")
    public ResponseEntity<AttractionDetailResponseDto> getTouristAttractionDetail(@PathVariable Long touristAttractionId){
        AttractionDetailResponseDto response = attractionService.loadAttractionDetail(touristAttractionId);
        return ResponseEntity.ok(response);
    }

    @Operation(summary = "관광지 이미지 정보",description = "Path Variable로 받은 관광지의 이미지 정보를 응답합니다.")
    @GetMapping("/{touristAttractionId}/gallery")
    public ResponseEntity<List<GalleryResponseDto>> getTouristAttractionGallery(@PathVariable Long touristAttractionId){
        List<GalleryResponseDto> response = attractionService.loadGallery(touristAttractionId);
        return ResponseEntity.ok(response);
    }

}
