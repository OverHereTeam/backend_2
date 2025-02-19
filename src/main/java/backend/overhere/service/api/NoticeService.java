package backend.overhere.service.api;

import backend.overhere.domain.Notice;
import backend.overhere.dto.domain.*;
import backend.overhere.repository.NoticeRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;

@Service
@Transactional
@RequiredArgsConstructor
public class NoticeService {
    private final NoticeRepository noticeRepository;

    public NoticeDetailResponseDto getNotices(int page, int size) {
        Pageable pageable = PageRequest.of(page, size);
        Page<Notice> noticePage = noticeRepository.findAllByOrderByCreatedAtDesc(pageable);

        List<NoticeDetailResponseDto.PageNoticeDetailResponseDto> collect = noticePage.getContent().stream()
                .map(notice -> NoticeDetailResponseDto.PageNoticeDetailResponseDto.builder().id(notice.getId()).title(notice.getTitle()).content(notice.getContent()).createdAt(notice.getCreatedAt()).updatedAt(notice.getUpdatedAt()).build())
                .collect(Collectors.toList());
        return new NoticeDetailResponseDto(noticePage.getTotalPages(), collect);
    }

    public SingleNoticeDetailResponseDto getNoticeSingleDetail(Long id){
        Notice notice = noticeRepository.findById(id).orElse(null);
        return SingleNoticeDetailResponseDto.builder()
                .id(notice.getId())
                .title(notice.getTitle())
                .content(notice.getContent())
                .createdAt(notice.getCreatedAt())
                .updatedAt(notice.getUpdatedAt())
                .build();
    }

    public NoticeResponseDto addNotice(NoticeRequestDto request){
        Notice notice = new Notice();
        notice.setContent(request.getContent());
        notice.setTitle(request.getTitle());
        noticeRepository.save(notice);

        return notice.noticetoNoticeResponseDto();
    }


}
