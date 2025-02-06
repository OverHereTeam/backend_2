package backend.overhere.dto.dbInit.response.urlResponse.url7TypeDto;


import lombok.*;

import java.util.List;

//contenttypeId : 32
@Setter
@Getter
@NoArgsConstructor
@Builder
@AllArgsConstructor
public class AccommodationDto implements ResponseDtoUrl7{

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
        private String goodstay;
        private String benikia;
        private String hanok;
        private String roomcount;
        private String roomtype;
        private String refundregulation;
        private String checkintime;
        private String checkouttime;
        private String chkcooking;
        private String seminar;
        private String sports;
        private String sauna;
        private String beauty;
        private String beverage;
        private String karaoke;
        private String barbecue;
        private String campfire;
        private String bicycle;
        private String fitness;
        private String publicpc;
        private String publicbath;
        private String subfacility;
        private String foodplace;
        private String reservationurl;
        private String pickup;
        private String infocenterlodging;
        private String parkinglodging;
        private String reservationlodging;
        private String scalelodging;
        private String accomcountlodging;

    }
}
