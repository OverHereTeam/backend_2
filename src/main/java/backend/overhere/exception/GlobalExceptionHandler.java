package backend.overhere.exception;

import backend.overhere.common.ResponseStatus;
import backend.overhere.dto.error.ErrorDto;
import io.jsonwebtoken.ExpiredJwtException;
import io.jsonwebtoken.JwtException;
import jakarta.servlet.ServletException;
import org.springframework.dao.DataAccessException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.servlet.NoHandlerFoundException;
import org.springframework.web.servlet.resource.NoResourceFoundException;

@ControllerAdvice
public class GlobalExceptionHandler {

    @ExceptionHandler(ExpiredJwtException.class)
    public ResponseEntity<?> expiredJwtException(ExpiredJwtException ex){
        return ErrorDto.settingResponse(HttpStatus.UNAUTHORIZED, ResponseStatus.R_TOKEN_EXPIRED);
    }

    @ExceptionHandler(JwtException.class)
    public ResponseEntity<?> jwtException(JwtException ex){
        return ErrorDto.settingResponse(HttpStatus.UNAUTHORIZED,ResponseStatus.TOKEN_INVALID);
    }

    @ExceptionHandler(LogoutException.class)
    public ResponseEntity<?> logoutException(LogoutException ex){
        return ErrorDto.settingResponse(HttpStatus.UNAUTHORIZED,ResponseStatus.LOGOUT_FAILED);
    }

    @ExceptionHandler(DataAccessException.class)
    public ResponseEntity<?> dataAccessException(DataAccessException ex){
        return ErrorDto.settingResponse(HttpStatus.INTERNAL_SERVER_ERROR,ResponseStatus.DB_ERROR);
    }

    @ExceptionHandler({NoResourceFoundException.class, NoHandlerFoundException.class})
    public ResponseEntity<?> noResourceFoundException(Exception ex){
        return ErrorDto.settingResponse(HttpStatus.NOT_FOUND,ResponseStatus.NO_RESOURCE_FOUND);
    }

    @ExceptionHandler(ServletException.class)
    public ResponseEntity<?> servletException(ServletException ex){
        return ErrorDto.settingResponse(HttpStatus.INTERNAL_SERVER_ERROR,ResponseStatus.SERVLET_ERROR);
    }
}
