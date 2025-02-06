package backend.overhere.dto.dbInit.response.urlResponse.url7TypeDto;

import lombok.*;

import java.util.List;


//contenttypeId : 39
@Setter
@Getter
@NoArgsConstructor
@Builder
@AllArgsConstructor
public class RestaurantDto implements ResponseDtoUrl7{

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
        private String seat;
        private String kidsfacility;
        private String firstmenu;
        private String treatmenu;
        private String smoking;
        private String packing;
        private String infocenterfood;
        private String scalefood;
        private String parkingfood;
        private String opendatefood;
        private String opentimefood;
        private String restdatefood;
        private String discountinfofood;
        private String chkcreditcardfood;
        private String reservationfood;
        private String lcnsno;
    }

}

