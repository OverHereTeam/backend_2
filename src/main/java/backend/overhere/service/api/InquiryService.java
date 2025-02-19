package backend.overhere.service.api;

import backend.overhere.domain.Faq;
import backend.overhere.domain.Inquiry;
import backend.overhere.domain.Notice;
import backend.overhere.dto.domain.*;
import backend.overhere.repository.InquiryRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
@Transactional
public class InquiryService {

    private final InquiryRepository inquiryRepository;

    public InquiryDetailResponseDto getInquiries(Long userId, int page, int size){
        Pageable pageable = PageRequest.of(page, size);
        Page<Inquiry> inquiryPage = inquiryRepository.findByUserIdOrderByCreatedAtDesc(userId, pageable);
        List<InquiryDetailResponseDto.PageInquiryDetailResponseDto> collect = inquiryPage.getContent().stream()
                .map(inquiry -> InquiryDetailResponseDto.PageInquiryDetailResponseDto.builder().id(inquiry.getId()).title(inquiry.getTitle()).createdAt(inquiry.getCreatedAt()).isAnswered(inquiry.isAnswered()).inquiryType(inquiry.getInquiryType()).build())
                .collect(Collectors.toList());
        return new InquiryDetailResponseDto(inquiryPage.getTotalPages(),collect);
    }

    public InquiryResponseDto addInquiry(InquiryRequestDto request){
        Inquiry inquiry = new Inquiry();
        inquiry.setContent(request.getContent());
        inquiry.setTitle(request.getTitle());
        inquiry.setAnswered(false);
        inquiry.setInquiryType(request.getInquiryType());
        inquiryRepository.save(inquiry);

        return inquiry.inquirytoInquiryResponseDto();
    }

}
