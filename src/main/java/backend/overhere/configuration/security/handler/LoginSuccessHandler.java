package backend.overhere.configuration.security.handler;

import com.fasterxml.jackson.databind.ObjectMapper;
import backend.overhere.common.ResponseStatus;
import backend.overhere.configuration.security.userDetails.CustomUserDetails;
import backend.overhere.dto.ResponseDto;
import backend.overhere.service.auth.RefreshTokenService;
import backend.overhere.util.JwtUtil;
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
public class LoginSuccessHandler implements AuthenticationSuccessHandler {
    private final JwtUtil jwtUtil;
    private final RefreshTokenService refreshTokenService;
    private final ObjectMapper objectMapper;

    @Override
    public void onAuthenticationSuccess(HttpServletRequest request, HttpServletResponse response, Authentication authentication) throws IOException, ServletException {
        CustomUserDetails auth = (CustomUserDetails) authentication.getPrincipal();

        Collection<? extends GrantedAuthority> authorities = auth.getAuthorities();
        Iterator<? extends GrantedAuthority> iterator = authorities.iterator();

        Long id = Long.parseLong(auth.getUsername());
        String role = iterator.next().getAuthority();
        String email = auth.getEmail();


        String accessToken = jwtUtil.createJwt(id, role, email);
        String refreshToken = jwtUtil.createRefreshJwt(id);

        refreshTokenService.updateExpiredTokens(id);
        refreshTokenService.addRefresh(id,refreshToken);

        // 응답 헤더 및 쿠키 설정
        if (accessToken != null) {
            response.addHeader("Authorization", "Bearer " + accessToken);
        }


        response.setHeader("Set-Cookie","Refresh="+refreshToken+"; Path=/; Max-Age=259200; HttpOnly");
        ResponseDto responseDto = new ResponseDto(ResponseStatus.LOCAL_LOGIN_SUCCESS.getMessage());
        String json = objectMapper.writeValueAsString(responseDto);
        response.setStatus(HttpServletResponse.SC_OK);
        response.setCharacterEncoding("UTF-8");
        response.setContentType("application/json");
        response.getWriter().write(json);
    }
}
