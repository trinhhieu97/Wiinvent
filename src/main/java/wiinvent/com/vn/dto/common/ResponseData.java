package wiinvent.com.vn.dto.common;

import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.Getter;
import org.springframework.http.HttpStatus;
import wiinvent.com.vn.constant.ResponseCode;

@Getter
public class ResponseData<T> {
    private final int code;
    private final String message;

    public static final ResponseData SUCCESS = new ResponseData(ResponseCode.SUCCESS);

    @JsonInclude(JsonInclude.Include.NON_NULL)
    private final Object data;

    public ResponseData(int statusCode, String message) {
        this.code = statusCode;
        this.message = message;
        this.data = null;
    }

    public ResponseData(HttpStatus statusCode, String message) {
        this(statusCode.value(), message);
    }

    public ResponseData(T data) {
        this.code = ResponseCode.SUCCESS.getCode();
        this.message = ResponseCode.SUCCESS.getMessage();
        this.data = data;
    }

    public ResponseData(int statusCode, String message, T data) {
        this.code = statusCode;
        this.message = message;
        this.data = data;
    }
}
