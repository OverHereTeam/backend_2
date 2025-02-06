package backend.overhere.dto.dbInit.request;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Builder
public class RequestDto {
    private String areaCode;
    private String numOfRows;
    private String pageNo;
    private String contentId;
    private String contentTypeId;
}
