package wiinvent.com.vn.aop;

import jakarta.servlet.http.HttpServletRequest;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.security.core.AuthenticationException;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import wiinvent.com.vn.constant.ResponseCode;
import wiinvent.com.vn.dto.common.ResponseData;
import wiinvent.com.vn.exception.ApplicationException;

import java.util.Map;
import java.util.stream.Collectors;

@RestControllerAdvice
@Slf4j
public class GlobalExceptionHandler {

    @ExceptionHandler(MethodArgumentNotValidException.class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    public ResponseData<Map<String, String>> handleValidationException(
            MethodArgumentNotValidException ex) {

        Map<String, String> errors = ex.getBindingResult()
                .getFieldErrors()
                .stream()
                .collect(Collectors.toMap(
                        FieldError::getField,
                        FieldError::getDefaultMessage,
                        (oldVal, newVal) -> oldVal
                ));

        return new ResponseData<>(
                ResponseCode.WRONG_DATA_FORMAT.getCode(),
                ResponseCode.WRONG_DATA_FORMAT.getMessage(),
                errors
        );
    }

    @ExceptionHandler(AuthenticationException.class)
    @ResponseStatus(HttpStatus.UNAUTHORIZED)
    public ResponseData<String> handleAuthenticationException(AuthenticationException ex) {
        String clientMessage = ex.getMessage() != null ? ex.getMessage() : ResponseCode.UNAUTHORIZED.getMessage();

        return new ResponseData<>(
                ResponseCode.UNAUTHORIZED.getCode(),
                clientMessage
        );
    }

    @ExceptionHandler(ApplicationException.class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    public ResponseData<String> handleApplicationException(ApplicationException ex) {
        ResponseCode responseCode = ex.getResponseCode();

        return new ResponseData<>(
                responseCode.getCode(),
                responseCode.getMessage()
        );
    }

    @ExceptionHandler(Exception.class)
    @ResponseStatus(HttpStatus.INTERNAL_SERVER_ERROR)
    public ResponseData<String> handleUnhandledException(
            Exception ex,
            HttpServletRequest request) {

        log.error("Unhandled exception at {} {}",
                request.getMethod(),
                request.getRequestURI(),
                ex);

        return new ResponseData<>(
                ResponseCode.ERROR.getCode(),
                ResponseCode.ERROR.getMessage()
        );
    }
}
