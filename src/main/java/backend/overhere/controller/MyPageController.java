package backend.overhere.controller;

import backend.overhere.configuration.security.userDetails.CustomUserDetails;
import backend.overhere.dto.domain.*;
import backend.overhere.service.api.FaqService;
import backend.overhere.service.api.InquiryService;
import backend.overhere.service.api.MyPageService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/v1/mypage")
@RequiredArgsConstructor
@Tag(name="마이페이지 API", description = "마이페이지 관련 API입니다.")
public class MyPageController {

    private final MyPageService myPageService;
    private final InquiryService inquiryService;
    private final FaqService faqService;

    @Operation(summary = "내 관광지 좋아요 API",description = "내 관광지 좋아요 리스트 API 입니다.")
    @GetMapping("/touristAttraction/likes")
    public ResponseEntity<List<TouristSearchResponseDto>> getTouristAttractionLikes(@AuthenticationPrincipal CustomUserDetails userDetails, @RequestParam(defaultValue = "0") int page, @RequestParam(defaultValue = "6") int size){
        List<TouristSearchResponseDto> responseDtos = myPageService.loadTouristAttractionsByLike(userDetails.getId(), page, size);
        return ResponseEntity.ok(responseDtos);
    }

    @Operation(summary = "내 코스 좋아요 API",description = "내 코스 좋아요 리스트 API 입니다.")
    @GetMapping("/course/likes")
    public ResponseEntity<List<SearchCourseResponseDto>> getCourseLikes(@AuthenticationPrincipal CustomUserDetails userDetails, @RequestParam(defaultValue = "0") int page, @RequestParam(defaultValue = "3") int size){
        List<SearchCourseResponseDto> searchCourseResponseDtos = myPageService.loadCourseByLike(userDetails.getId(), page, size);
        return ResponseEntity.ok(searchCourseResponseDtos);
    }

    @Operation(summary = "1대1 문의 리스트 API",description = "1대1 문의 리스트 API 입니다.")
    @GetMapping("/inquiries")
    public ResponseEntity<List<InquiryDetailResponseDto>> getInquiries(@AuthenticationPrincipal CustomUserDetails userDetails, @RequestParam(defaultValue = "0") int page, @RequestParam(defaultValue = "5") int size){
        List<InquiryDetailResponseDto> inquiries = inquiryService.getInquiries(userDetails.getId(), page, size);
        return ResponseEntity.ok(inquiries);
    }

    @Operation(summary = "1대1 문의 추가 API",description = "1대1 문의 추가 API 입니다.")
    @PostMapping("/inquiries")
    public ResponseEntity<InquiryResponseDto> addInquiry(@AuthenticationPrincipal CustomUserDetails userDetails,@Validated @RequestBody InquiryRequestDto requestDto){
        requestDto.setUserId(userDetails.getId());
        InquiryResponseDto inquiryResponseDto = inquiryService.addInquiry(requestDto);
        return ResponseEntity.ok(inquiryResponseDto);
    }

    @Operation(summary = "자주 묻는 질문 리스트 API",description = "자주 묻는 질문 리스트 API 입니다.")
    @GetMapping("/faqs")
    public ResponseEntity<List<FaqDetailResponseDto>> getFaqs(@AuthenticationPrincipal CustomUserDetails userDetails, @RequestParam(defaultValue = "0") int page, @RequestParam(defaultValue = "5") int size){
        List<FaqDetailResponseDto> faqs = faqService.getFaqs(page, size);
        return ResponseEntity.ok(faqs);
    }

    @Operation(summary = "자주 묻는 질문 리스트 추가 API",description = "자주 묻는 질문 리스트 추가 API입니다.")
    //@PreAuthorize("hasRole('ADMIN')") // 관리자만 접근 가능
    @PostMapping("/faqs")
    public ResponseEntity<FaqResponseDto> addFaq(@Validated @RequestBody FaqRequestDto request){
        FaqResponseDto faqResponseDto = faqService.addFaq(request);
        return ResponseEntity.ok(faqResponseDto);
    }

    @Operation(summary = "개별 자주 묻는 질문 정보 API ",description = "개별 자주 묻는 질문 정보 API입니다.")
    @GetMapping("/faqs/{faqsId}")
    public ResponseEntity<FaqDetailResponseDto> getNoticeDetail(@PathVariable Long faqsId){
        FaqDetailResponseDto faqDetail = faqService.getFaqDetail(faqsId);
        return ResponseEntity.ok(faqDetail);
    }




}
