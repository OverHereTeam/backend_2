package backend.overhere.service.api;

import backend.overhere.domain.Faq;
import backend.overhere.dto.domain.faqdto.FaqDetailResponseDto;
import backend.overhere.dto.domain.faqdto.FaqRequestDto;
import backend.overhere.dto.domain.faqdto.FaqResponseDto;
import backend.overhere.repository.FaqRepository;
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
public class FaqService {
    private final FaqRepository faqRepository;

    public List<FaqDetailResponseDto> getFaqs(int page, int size) {
        Pageable pageable = PageRequest.of(page, size);
        Page<Faq> faqPage = faqRepository.findAllByOrderByCreatedAtDesc(pageable);

        return faqPage.getContent().stream()
                .map(faq -> new FaqDetailResponseDto(
                        faq.getId(),
                        faq.getTitle(),
                        faq.getContent(),
                        faq.getCreatedAt(),
                        faq.getUpdatedAt()
                ))
                .collect(Collectors.toList());
    }

    public FaqDetailResponseDto getFaqDetail(Long id){
        Faq faq = faqRepository.findById(id).orElse(null);
        return new FaqDetailResponseDto(faq.getId(),faq.getTitle(),faq.getContent(),faq.getCreatedAt(),faq.getUpdatedAt());
    }

    public FaqResponseDto addFaq(FaqRequestDto request){
        Faq faq = new Faq();
        faq.setContent(request.getContent());
        faq.setTitle(request.getTitle());
        faqRepository.save(faq);

        return faq.faqtoFaqResponseDto();
    }
}
