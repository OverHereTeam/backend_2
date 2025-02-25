package backend.overhere.controller;

import backend.overhere.dto.domain.noticedto.NoticeDetailResponseDto;
import backend.overhere.dto.domain.noticedto.NoticeRequestDto;
import backend.overhere.dto.domain.noticedto.NoticeResponseDto;
import backend.overhere.service.api.NoticeService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.List;


@RestController
@RequestMapping("/api/v1/notices")
@RequiredArgsConstructor
@Tag(name="공지사항 API", description = "공지사항 API입니다.")
public class NoticeController {

    private final NoticeService noticeService;

    @Operation(summary = "공지사항 리스트 API",description = "공지사항 리스트 API 입니다.")
    @GetMapping
    public ResponseEntity<List<NoticeDetailResponseDto>> getNotices(@RequestParam(defaultValue = "0") int page,@RequestParam(defaultValue = "10") int size) {
        List<NoticeDetailResponseDto> notices = noticeService.getNotices(page, size);
        return ResponseEntity.ok(notices);
    }

    @Operation(summary = "개별 공지사항 정보 API ",description = "개별 공지사항 정보 조회 API 입니다.")
    @GetMapping("/{noticeId}")
    public ResponseEntity<NoticeDetailResponseDto> getNoticeDetail(@PathVariable Long noticeId){
        NoticeDetailResponseDto noticeDetail = noticeService.getNoticeDetail(noticeId);
        return ResponseEntity.ok(noticeDetail);
    }

    @Operation(summary = "공지사항 추가 API ",description = "공지사항 추가 API 입니다.")
    @PostMapping
    public ResponseEntity<NoticeResponseDto> addNotice(@Validated @RequestBody NoticeRequestDto requestDto){
        NoticeResponseDto noticeResponseDto = noticeService.addNotice(requestDto);
        return ResponseEntity.ok(noticeResponseDto);
    }

}
