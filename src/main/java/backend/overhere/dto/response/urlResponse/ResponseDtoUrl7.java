package backend.overhere.dto.response.urlResponse;

import lombok.Getter;
import lombok.Setter;

import java.util.List;

//소개정보
@Getter
@Setter
public class ResponseDtoUrl7 {
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
        //cotentTypeId마다 다름
    }


}
