package backend.overhere.configuration.security;

import com.fasterxml.jackson.databind.ObjectMapper;
import backend.overhere.common.ResponseStatus;
import backend.overhere.dto.error.ErrorDto;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.AuthenticationEntryPoint;
import org.springframework.stereotype.Component;


import java.io.IOException;

//인가 문제
@Component
@RequiredArgsConstructor
public class CustomAuthenticationEntryPoint implements AuthenticationEntryPoint {

    @Override
    public void commence(HttpServletRequest request, HttpServletResponse response, AuthenticationException authException) throws IOException, ServletException {
        ErrorDto.settingResponse(response,HttpStatus.FORBIDDEN,ResponseStatus.AUTHORIZATION_FAILED);
    }
}
