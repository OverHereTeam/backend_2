package backend.overhere.dto.oauth.response;

import lombok.RequiredArgsConstructor;

import java.util.Map;

@RequiredArgsConstructor
public class GoogleResponse implements OAuth2Response{
    private final Map<String,Object> attribute;

    @Override
    public String getProvider(){
        return "google";
    }

    //UserId
    @Override
    public String getProviderId(){
        return attribute.get("sub").toString();
    }

    @Override
    public String getEmail(){
        return attribute.get("email").toString();
    }

    @Override
    public String getName(){
        return attribute.get("name").toString();
    }

}
