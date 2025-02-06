package backend.overhere.dto.dbInit.response.urlResponse;

import lombok.Getter;
import lombok.Setter;

import java.util.List;


//무장애정보
@Getter
@Setter
public class ResponseDtoUrl10 {
    private Response response;

    @Getter
    @Setter
    public static class Response {
        private Header header;
        private Body body;
    }

    @Getter
    @Setter
    public static class Header {
        private String resultCode;
        private String resultMsg;

    }

    @Getter
    @Setter
    public static class Body {
        private Items items;
        private int numOfRows;
        private int pageNo;
        private int totalCount;

    }

    @Getter
    @Setter
    public static class Items {
        private List<Item> item;
    }

    @Getter
    @Setter
    public static class Item {
        private String contentid;
        private String parking;
        private String route;
        private String publictransport;
        private String ticketoffice;
        private String promotion;
        private String wheelchair;
        private String exit;
        private String elevator;
        private String restroom;
        private String auditorium;
        private String room;
        private String handicapetc;
        private String braileblock;
        private String helpdog;
        private String guidehuman;
        private String audioguide;
        private String bigprint;
        private String brailepromotion;
        private String guidesystem;
        private String blindhandicapetc;
        private String signguide;
        private String videoguide;
        private String hearingroom;
        private String hearinghandicapetc;
        private String stroller;
        private String lactationroom;
        private String babysparechair;
        private String infantsfamilyetc;

    }
}
