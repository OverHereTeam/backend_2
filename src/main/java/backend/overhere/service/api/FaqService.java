package backend.overhere.service.api;

import backend.overhere.domain.Faq;
import backend.overhere.dto.domain.*;
import backend.overhere.repository.FaqRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;

@Service
@Transactional
@RequiredArgsConstructor
public class FaqService {
    private final FaqRepository faqRepository;

    public FaqDetailResponseDto getFaqs(int page, int size) {
        Pageable pageable = PageRequest.of(page, size);
        Page<Faq> faqPage = faqRepository.findAllByOrderByCreatedAtDesc(pageable);

        List<FaqDetailResponseDto.PageFaqDetailResponseDto> collect = faqPage.getContent().stream()
                .map(faq -> FaqDetailResponseDto.PageFaqDetailResponseDto.builder().faqId(faq.getId()).title(faq.getTitle()).content(faq.getTitle()).createdAt(faq.getCreatedAt()).updatedAt(faq.getUpdatedAt()).build())
                .collect(Collectors.toList());
        return new FaqDetailResponseDto(faqPage.getTotalPages(),collect);
    }

    public SingleFaqDetailResponseDto getFaqDetail(Long id){
        Faq faq = faqRepository.findById(id).orElse(null);
        return SingleFaqDetailResponseDto.builder().faqId(faq.getId())
                .title(faq.getTitle())
                .content(faq.getContent())
                .createdAt(faq.getCreatedAt())
                .updatedAt(faq.getUpdatedAt())
                .build();
    }

    public FaqResponseDto addFaq(FaqRequestDto request){
        Faq faq = new Faq();
        faq.setContent(request.getContent());
        faq.setTitle(request.getTitle());
        faqRepository.save(faq);

        return faq.faqtoFaqResponseDto();
    }
}
