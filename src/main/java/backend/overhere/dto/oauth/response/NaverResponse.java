package backend.overhere.dto.oauth.response;

import lombok.RequiredArgsConstructor;

import java.util.Map;

@RequiredArgsConstructor
public class NaverResponse implements OAuth2Response{
    private final Map<String, Object> attribute;

    @Override
    public String getProvider() {
        return "naver";
    }


    @Override
    public String getProviderId() {
        Map<String, Object> response = (Map<String, Object>) attribute.get("response");
        return (String) response.get("id");
    }

    @Override
    public String getEmail() {
        Map<String, Object> response = (Map<String, Object>) attribute.get("response");
        return (String) response.get("email");
    }

    @Override
    public String getName() {
        Map<String, Object> response = (Map<String, Object>) attribute.get("response");
        return (String) response.get("nickname");
    }
}
