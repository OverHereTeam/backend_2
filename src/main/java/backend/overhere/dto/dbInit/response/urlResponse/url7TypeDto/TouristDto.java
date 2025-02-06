package backend.overhere.dto.dbInit.response.urlResponse.url7TypeDto;


import lombok.*;

import java.util.List;

//contenttypeId : 12
@Setter
@Getter
@NoArgsConstructor
@Builder
@AllArgsConstructor
public class TouristDto implements ResponseDtoUrl7{
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
        private String heritage1;
        private String heritage2;
        private String heritage3;
        private String infocenter;
        private String opendate;
        private String restdate;
        private String expguide;
        private String expagerange;
        private String accomcount;
        private String useseason;
        private String usetime;
        private String parking;
        private String chkbabycarriage;
        private String chkpet;
        private String chkcreditcard;

    }
}