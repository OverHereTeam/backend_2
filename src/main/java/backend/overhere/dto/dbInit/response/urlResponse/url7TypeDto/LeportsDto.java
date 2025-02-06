package backend.overhere.dto.dbInit.response.urlResponse.url7TypeDto;


import lombok.*;

import java.util.List;

//contenttypeId : 28
@Setter
@Getter
@NoArgsConstructor
@Builder
@AllArgsConstructor
public class LeportsDto implements ResponseDtoUrl7{

    private Response response;

    @Getter
    @Setter
    public static class Response{
        private Header header;
        private Body body;
    }
    @Getter
    @Setter
    public static class Header{
        private String resultCode;
        private String resultMsg;
    }
    @Getter
    @Setter
    public static class Body{
        private Items items;
        private int numOfRows;
        private int pageNo;
        private int totalCount;
    }
    @Getter
    @Setter
    public static class Items{
        private List<Item> item;
    }
    @Getter
    @Setter
    public static class Item{
        private String contentid;
        private String contenttypeid;
        private String openperiod;
        private String reservation;
        private String infocenterleports;
        private String scaleleports;
        private String accomcountleports;
        private String restdateleports;
        private String usetimeleports;
        private String usefeeleports;
        private String expagerangeleports;
        private String parkingleports;
        private String parkingfeeleports;
        private String chkbabycarriageleports;
        private String chkpetleports;
        private String chkcreditcardleports;

    }
}
