package backend.overhere.dto.dbInit.response.urlResponse.url7TypeDto;

import lombok.*;

import java.util.List;

//contenttypeId : 14
@Setter
@Getter
@NoArgsConstructor
@Builder
@AllArgsConstructor
public class CultureFacilityDto implements ResponseDtoUrl7{

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
        private String scale;
        private String usefee;
        private String discountinfo;
        private String spendtime;
        private String parkingfee;
        private String infocenterculture;
        private String accomcountculture;
        private String usetimeculture;
        private String restdateculture;
        private String parkingculture;
        private String chkbabycarriageculture;
        private String chkpetculture;
        private String chkcreditcardculture;

    }
}
