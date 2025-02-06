package backend.overhere.configuration.security.userDetails;

import backend.overhere.entity.User;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.oauth2.core.user.OAuth2User;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Map;

//OAuth 로그인에서의 중복 사용자인지 비교만 provider 필드 + " " + providerId 필드 를 합쳐서 비교
//일반 로그인 , OAuth 로그인 모두 나머지는 DB ID에서 비교
public class CustomOAuth2UserDetails implements OAuth2User {
    private final User user;
    private final Map<String,Object> attributes;

    public CustomOAuth2UserDetails(User user,Map<String,Object> attributes) {
        this.user = user;
        this.attributes=attributes;
    }

    @Override
    public Map<String, Object> getAttributes() {
        return attributes;
    }

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        Collection<GrantedAuthority> collection = new ArrayList<>();

        collection.add(new GrantedAuthority() {

            @Override
            public String getAuthority() {
                return "ROLE_USER";
            }
        });

        return collection;
    }

    @Override
    public String getName() {
        return user.getId().toString();
    }

    public String getEmail(){
        return user.getEmail();
    }

    public String getNickname() { return user.getNickname(); }


}
