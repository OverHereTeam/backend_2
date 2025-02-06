package backend.overhere.configuration.security.handler;


import backend.overhere.common.ResponseStatus;
import backend.overhere.dto.error.ErrorDto;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.dao.DataAccessException;
import org.springframework.http.HttpStatus;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.oauth2.core.OAuth2AuthenticationException;
import org.springframework.security.web.authentication.AuthenticationFailureHandler;
import org.springframework.stereotype.Component;

import java.io.IOException;

@Component
@RequiredArgsConstructor
@Slf4j
public class OauthLoginFailureHandler implements AuthenticationFailureHandler {
    @Override
    public void onAuthenticationFailure(HttpServletRequest request, HttpServletResponse response, AuthenticationException exception) throws IOException, ServletException {
        Throwable cause = exception;
        while(cause != null){
            log.warn("에러: {}",cause);
            if (cause instanceof DataAccessException) {
                ErrorDto.settingResponse(response,HttpStatus.INTERNAL_SERVER_ERROR,ResponseStatus.DB_ERROR);
                break;
            }

            if (cause instanceof OAuth2AuthenticationException) {
                ErrorDto.settingResponse(response,HttpStatus.UNAUTHORIZED,ResponseStatus.OAUTH_LOGIN_FAILED);
                break;
            }
            cause=cause.getCause();
        }

    }
}
