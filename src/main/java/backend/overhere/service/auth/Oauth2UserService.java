package backend.overhere.service.auth;

import backend.overhere.configuration.security.userDetails.CustomOAuth2UserDetails;
import backend.overhere.dto.oauth.response.GoogleResponse;
import backend.overhere.dto.oauth.response.KaKaoResponse;
import backend.overhere.dto.oauth.response.NaverResponse;
import backend.overhere.dto.oauth.response.OAuth2Response;
import backend.overhere.entity.User;
import backend.overhere.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.oauth2.client.userinfo.DefaultOAuth2UserService;
import org.springframework.security.oauth2.client.userinfo.OAuth2UserRequest;
import org.springframework.security.oauth2.core.OAuth2AuthenticationException;
import org.springframework.security.oauth2.core.user.OAuth2User;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

//Oauth2에 대한 DB 접근 로직
@Service
@RequiredArgsConstructor
@Transactional
public class Oauth2UserService extends DefaultOAuth2UserService {
    private final UserRepository userRepository;

    @Override
    public OAuth2User loadUser(OAuth2UserRequest userRequest) throws OAuth2AuthenticationException{
        OAuth2User oAuth2User = super.loadUser(userRequest);

        String registrationId = userRequest.getClientRegistration().getRegistrationId();
        OAuth2Response oAuth2Response=null;
        if(registrationId.equals("naver")){
            oAuth2Response = new NaverResponse(oAuth2User.getAttributes());
        }
        else if(registrationId.equals("google")){
            oAuth2Response= new GoogleResponse(oAuth2User.getAttributes());

        }
        else if(registrationId.equals("kakao")){
            oAuth2Response=new KaKaoResponse(oAuth2User.getAttributes());
        }
        else{
            throw new OAuth2AuthenticationException("OAuth2 DB 정보 찾지 없음");
        }

        String provider = oAuth2Response.getProvider();
        String providerId = oAuth2Response.getProviderId();

        User user = userRepository.findByProviderAndProviderId(provider,providerId);
        //OAuth 유저 없을때
        if(user==null){

            User userNew = User.builder().email(oAuth2Response.getEmail()).role("ROLE_USER").nickname(oAuth2Response.getName()).password(null).providerId(providerId).provider(provider).build();
            userRepository.save(userNew);

            return new CustomOAuth2UserDetails(userNew,oAuth2User.getAttributes());

        }else{

            user.setEmail(oAuth2Response.getEmail());
            user.setNickname(oAuth2Response.getName());

            userRepository.save(user);


            return new CustomOAuth2UserDetails(user,oAuth2User.getAttributes());
        }


    }
}