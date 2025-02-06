package backend.overhere.dto.dbInit.response.urlResponse;

import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
public class ResponseDtoUrl6 {
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
        private String contenttypeid;
        private String title;
        private String createdtime;
        private String modifiedtime;
        private String tel;
        private String telname;
        private String homepage;
        private String booktour;
        private String areacode;
        private String sigungucode;
        private String mapx;
        private String mapy;
        private String mlevel;
        private String overview;
    }
}
