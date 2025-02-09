package backend.overhere.service.dbInit;

import backend.overhere.dto.dbInit.response.urlResponse.ResponseDtoUrl3;
import backend.overhere.domain.TouristAttraction;
import backend.overhere.repository.TouristAttractionRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Stream;

@Service
@RequiredArgsConstructor
public class TouristAttractionService {
    private final TouristAttractionRepository touristAttractionRepository;

    //List 전부 Save
    @Transactional
    public void saveTouristAttractions(List<TouristAttraction> touristAttractionList) {
        touristAttractionRepository.saveAll(touristAttractionList);
    }

    //Dto 배열 List로 반환
    public List<TouristAttraction> toTouristAttractionList(ResponseDtoUrl3 responseDtoUrl3) {
        List<TouristAttraction> touristAttractionList = new ArrayList<>();

        Stream<ResponseDtoUrl3.Item> stream = responseDtoUrl3.getResponse().getBody().getItems().getItem().stream();
        stream.forEach(item -> {
            TouristAttraction touristAttraction = TouristAttraction.builder()
                    .contentId(item.getContentid())
                    .contentTypeId(item.getContenttypeid())
                    .areaCode(item.getAreacode())
                    .cat1(item.getCat1())
                    .cat2(item.getCat2())
                    .cat3(item.getCat3())
                    .thumbnail1(item.getFirstimage())
                    .thumbnail2(item.getFirstimage2())
                    .tel(item.getTel())
                    .title(item.getTitle())
                    .address1(item.getAddr1())
                    .address2(item.getAddr2())
                    .build();
            touristAttractionList.add(touristAttraction);
        });

        return touristAttractionList;
    }





}
