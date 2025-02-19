package backend.overhere.dto.dbInit.request;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Builder
public class RequestDto {
    private Integer areaCode;
    private Integer numOfRows;
    private Integer pageNo;
    private Long contentId;
    private Integer contentTypeId;
}
