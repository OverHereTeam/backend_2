package backend.overhere.controller;

import backend.overhere.common.ResponseStatus;
import backend.overhere.dto.ResponseDto;
import backend.overhere.dto.domain.AttractionDetailResponseDto;
import backend.overhere.dto.domain.AttractionInfoResponseDto;
import backend.overhere.dto.domain.GalleryResponseDto;
import backend.overhere.service.api.AttractionService;
import backend.overhere.service.api.NonObstacleInfoService;
import lombok.RequiredArgsConstructor;
import org.hibernate.query.Page;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/v1/attraction")
@RequiredArgsConstructor
public class AttractionController {
    //tourist_attraction
    //non_obstacle_info
    private final AttractionService attractionService;

    @GetMapping("/{touristAttractionId}/info")
    public ResponseEntity<ResponseDto<AttractionInfoResponseDto>> getTouristAttractionInfo(@PathVariable Long touristAttractionId){
        AttractionInfoResponseDto response = attractionService.loadAttractionInfo(touristAttractionId);
        ResponseEntity<ResponseDto<AttractionInfoResponseDto>> responseDtoResponseEntity = ResponseDto.settingResponse(HttpStatus.OK, ResponseStatus.SUCCESS, response);
        return responseDtoResponseEntity;
    }

    @GetMapping("/{touristAttractionId}/detail")
    public ResponseEntity<ResponseDto<AttractionDetailResponseDto>> getTouristAttractionDetail(@PathVariable Long touristAttractionId){
        AttractionDetailResponseDto response = attractionService.loadAttractionDetail(touristAttractionId);
        return ResponseDto.settingResponse(HttpStatus.OK, ResponseStatus.SUCCESS,response);
    }

    @GetMapping("/{touristAttractionId}/gallery")
    public ResponseEntity<ResponseDto<List<GalleryResponseDto>>> getTouristAttractionGallery(@PathVariable Long touristAttractionId){
        List<GalleryResponseDto> response = attractionService.loadGallery(touristAttractionId);
        return ResponseDto.settingResponse(HttpStatus.OK, ResponseStatus.SUCCESS,response);
    }




}
