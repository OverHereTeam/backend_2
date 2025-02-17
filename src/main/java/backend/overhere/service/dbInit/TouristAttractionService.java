package backend.overhere.service.dbInit;

import backend.overhere.dto.dbInit.request.RequestDto;
import backend.overhere.dto.dbInit.response.urlResponse.ResponseDtoUrl3;
import backend.overhere.domain.TouristAttraction;
import backend.overhere.repository.TouristAttractionRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Stream;

@Service
@RequiredArgsConstructor
@Transactional
public class TouristAttractionService {
    private final TouristAttractionRepository touristAttractionRepository;

    //List 전부 Save
    public void saveTouristAttractions(List<TouristAttraction> touristAttractionList) {
        touristAttractionRepository.saveAll(touristAttractionList);
    }


    public Page<TouristAttraction> getAttractionSearch(String areacode, String type, String searchParam, int page, int size) {
        Pageable pageable = PageRequest.of(page, size);

        Specification<TouristAttraction> spec = Specification.where(null);

        // 지역 조건 추가
        if (areacode != null) {
            spec = spec.and(hasAreaCode(areacode));
        }

        // 유형 조건 추가
        if (type != null) {
            spec = spec.and(hasContentTypeId(type));
        }

        // 검색어 조건 추가
        if (searchParam != null) {
            spec = spec.and(hasSearchParam(searchParam));
        }

        return touristAttractionRepository.findAll(spec, pageable);
    }


    //Dto 배열 List로 반환
    public List<TouristAttraction> toTouristAttractionList(ResponseDtoUrl3 responseDtoUrl3) {
        List<TouristAttraction> touristAttractionList = new ArrayList<>();

        Stream<ResponseDtoUrl3.Item> stream = responseDtoUrl3.getResponse().getBody().getItems().getItem().stream();
        stream.forEach(item -> {
            TouristAttraction touristAttraction = TouristAttraction.builder()
                    .contentId(item.getContentid())
                    .contentTypeId(item.getContenttypeid())
                    .areaCode(areacodeSetting(item.getAreacode()))
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

    // 지역이 남도, 북도일 경우 조정 >> 남도를 북도 Code로 통일
    private static String areacodeSetting(String areaCode) {
        if(areaCode.equals("38")){
            return "37";
        }
        else if(areaCode.equals("36")){
            return "35";
        }
        else if (areaCode.equals("34")){
            return "33";
        }
        else{
            return areaCode;
        }
    }

    // 지역 코드 조건
    private Specification<TouristAttraction> hasAreaCode(String areaCode) {
        return (root, query, criteriaBuilder) -> criteriaBuilder.equal(root.get("areaCode"), areaCode);
    }

    // 유형 조건
    private Specification<TouristAttraction> hasContentTypeId(String contentTypeId) {
        return (root, query, criteriaBuilder) -> criteriaBuilder.equal(root.get("contentTypeId"), contentTypeId);
    }

    // 검색어 조건 (제목, 개요 등을 포함하여 검색)
    private Specification<TouristAttraction> hasSearchParam(String searchParam) {
        return (root, query, criteriaBuilder) -> {
            if (searchParam == null || searchParam.isEmpty()) {
                return criteriaBuilder.conjunction(); // 검색어가 없으면 항상 참
            }
            return criteriaBuilder.or(
                    criteriaBuilder.like(root.get("title"), "%" + searchParam + "%"),
                    criteriaBuilder.like(root.get("overview"), "%" + searchParam + "%")
            );
        };
    }




}
