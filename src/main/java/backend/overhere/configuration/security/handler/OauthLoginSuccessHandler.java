package backend.overhere.configuration.security.handler;

import com.fasterxml.jackson.databind.ObjectMapper;
import backend.overhere.common.ResponseStatus;
import backend.overhere.configuration.security.userDetails.CustomOAuth2UserDetails;
import backend.overhere.dto.ResponseDto;
import backend.overhere.service.auth.RefreshTokenService;
import backend.overhere.util.JwtUtil;
import backend.overhere.util.Util;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.web.authentication.AuthenticationSuccessHandler;
import org.springframework.stereotype.Component;

import java.io.IOException;
import java.util.Collection;
import java.util.Iterator;

@Component
@RequiredArgsConstructor
public class OauthLoginSuccessHandler implements AuthenticationSuccessHandler {
    private final JwtUtil jwtUtil;
    private final RefreshTokenService refreshTokenService;
    private final Util util;
    private final ObjectMapper mapper;

    @Override
    public void onAuthenticationSuccess(HttpServletRequest request, HttpServletResponse response, Authentication authentication) throws IOException, ServletException {

        // CustomOAuth2UserDetails에서 사용자 정보 추출
        CustomOAuth2UserDetails oAuth2UserDetails = (CustomOAuth2UserDetails) authentication.getPrincipal();
        Long id = Long.parseLong(oAuth2UserDetails.getName());

        // 사용자 역할(Role) 가져오기
        Collection<? extends GrantedAuthority> authorities = authentication.getAuthorities();
        Iterator<? extends GrantedAuthority> iterator = authorities.iterator();
        String role = iterator.next().getAuthority();

        String refreshToken = jwtUtil.createRefreshJwt(id);

        refreshTokenService.updateExpiredTokens(id);
        // Refresh Token 저장
        refreshTokenService.addRefresh(id, refreshToken);

        ResponseDto.settingResponse(response,HttpStatus.FOUND,ResponseStatus.OAUTH_LOGIN_SUCCESS,null,refreshToken);
        response.sendRedirect("http://localhost:3000/refresh");
    }
}
