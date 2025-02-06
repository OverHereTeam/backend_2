package backend.overhere.dto.dbInit.response.urlResponse;

import com.fasterxml.jackson.annotation.JsonSetter;
import lombok.Getter;
import lombok.Setter;

import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.stream.Collectors;

//이미지 정보
@Getter
@Setter
public class ResponseDtoUrl9 {
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

//        @JsonSetter
//        public void setItems(Object value) {
//            if (value instanceof String || value == null) {
//                this.items = new Items();
//                this.items.setItem(new ArrayList<>());
//            } else if (value instanceof LinkedHashMap) {
//                Items newItems = new Items();
//                newItems.setItem(new ArrayList<>());
//                this.items = newItems;
//            } else {
//                try {
//                    this.items = (Items) value;
//                } catch (ClassCastException e) {
//                    this.items = new Items();
//                    this.items.setItem(new ArrayList<>());
//                }
//            }
//        }
//
//    }
    }

    @Getter
    @Setter
    public static class Items {
        private List<Item> item;

//        @JsonSetter
//        public void setItem(Object value) {
//            if (value instanceof List<?> list) {
//                this.item = list.stream()
//                        .map(obj -> {
//                            if (obj instanceof LinkedHashMap<?, ?> map) {
//                                Item item = new Item();
//                                item.setContentid((String) map.get("contentid"));
//                                item.setOriginimgurl((String) map.get("originimgurl"));
//                                item.setImgname((String) map.get("imgname"));
//                                item.setSmallimageurl((String) map.get("smallimageurl"));
//                                item.setCpyrhtDivCd((String) map.get("cpyrhtDivCd"));
//                                item.setSerialnum((String) map.get("serialnum"));
//                                return item;
//                            }
//                            return (Item) obj;
//                        })
//                        .collect(Collectors.toList());
//            } else if (value instanceof String || value == null) {
//                this.item = new ArrayList<>();
//            }
//        }
//    }
    }
    @Getter
    @Setter
    public static class Item {
        private String contentid;
        private String originimgurl;
        private String imgname;
        private String smallimageurl;
        private String cpyrhtDivCd;
        private String serialnum;
    }

}
