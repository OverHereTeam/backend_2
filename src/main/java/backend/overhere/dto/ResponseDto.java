package backend.overhere.dto;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.core.JsonProcessingException;
import backend.overhere.common.ResponseStatus;
import backend.overhere.dto.error.ErrorDto;
import backend.overhere.util.Util;
import jakarta.servlet.http.HttpServletResponse;
import lombok.AllArgsConstructor;
import lombok.Getter;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import java.io.IOException;

import static backend.overhere.util.Util.mapper;


@Getter
@AllArgsConstructor
public class ResponseDto {
    private String message;
}
