package backend.overhere.configuration.security.handler;

import backend.overhere.common.ResponseStatus;
import backend.overhere.dto.error.ErrorDto;
import backend.overhere.exception.JsonFormException;
import backend.overhere.exception.LoginFormException;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.dao.DataAccessException;
import org.springframework.http.HttpStatus;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.authentication.AuthenticationFailureHandler;
import org.springframework.stereotype.Component;

import java.io.IOException;

@Component
@RequiredArgsConstructor
@Slf4j
public class LoginFailureHandler implements AuthenticationFailureHandler {

    @Override
    public void onAuthenticationFailure(HttpServletRequest request, HttpServletResponse response, AuthenticationException exception) throws IOException, ServletException {
        log.debug("LoginFailureHandler.onAuthenticationFailure");

        Throwable cause = exception;
        while(cause != null){
            log.warn("에러: {}",cause);
            if (cause instanceof DataAccessException) {
                ErrorDto.settingResponse(response,HttpStatus.INTERNAL_SERVER_ERROR,ResponseStatus.DB_ERROR);
                break;
            }

            if (cause instanceof BadCredentialsException) {
                ErrorDto.settingResponse(response,HttpStatus.NOT_FOUND,ResponseStatus.LOCAL_LOGIN_FAILED);
                break;
            }

            if (cause instanceof LoginFormException){
                ErrorDto.settingResponse(response,HttpStatus.BAD_REQUEST,ResponseStatus.LOGIN_FORM_INVALID);
                break;
            }

            if(cause instanceof JsonFormException){
                ErrorDto.settingResponse(response,HttpStatus.BAD_REQUEST,ResponseStatus.JSON_FORM_ERROR);
            }
            cause=cause.getCause();
        }

    }
}