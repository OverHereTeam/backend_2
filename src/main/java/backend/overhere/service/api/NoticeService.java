package backend.overhere.service.api;

import backend.overhere.domain.Inquiry;
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

    public List<NoticeResponseDto> getNotices(int page, int size) {
        Pageable pageable = PageRequest.of(page, size);
        Page<Notice> noticePage = noticeRepository.findAllByOrderByCreatedAtDesc(pageable);

        return noticePage.getContent().stream()
                .map(notice -> new NoticeResponseDto(
                        notice.getId(),
                        notice.getTitle(),
                        notice.getCreatedAt(),
                        notice.getUpdatedAt()
                ))
                .collect(Collectors.toList());
    }

    public NoticeDetailResponseDto getNoticeDetail(Long id){
        Notice notice = noticeRepository.findById(id).orElse(null);
        return new NoticeDetailResponseDto(notice.getId(),notice.getTitle(),notice.getContent(),notice.getCreatedAt(),notice.getUpdatedAt());
    }

    public NoticeResponseDto addNotice(NoticeRequestDto request){
        Notice notice = new Notice();
        notice.setContent(request.getContent());
        notice.setTitle(request.getTitle());
        noticeRepository.save(notice);

        return notice.noticetoNoticeResponseDto();
    }


}
