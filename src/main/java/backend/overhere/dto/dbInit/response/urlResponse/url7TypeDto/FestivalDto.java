package backend.overhere.dto.dbInit.response.urlResponse.url7TypeDto;


import lombok.*;

import java.util.List;

//contenttypeId : 15
@Setter
@Getter
@NoArgsConstructor
@Builder
@AllArgsConstructor
public class FestivalDto implements ResponseDtoUrl7{

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
        private String sponsor1;
        private String sponsor1tel;
        private String sponsor2;
        private String sponsor2tel;
        private String eventenddate;
        private String playtime;
        private String eventplace;
        private String eventhomepage;
        private String agelimit;
        private String bookingplace;
        private String placeinfo;
        private String subevent;
        private String program;
        private String eventstartdate;
        private String usetimefestival;
        private String discountinfofestival;
        private String spendtimefestival;
        private String festivalgrade;

    }
}
