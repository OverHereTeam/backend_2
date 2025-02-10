package backend.overhere.service.api;

import backend.overhere.repository.AttractionRepository;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@Transactional
@RequiredArgsConstructor
public class AttractionService {
    private final AttractionRepository attractionRepository;

    //관광지 이름
    //주소
    //전화번호
    //시간
    public loadAttractionBasicInfo(){

    }


}
