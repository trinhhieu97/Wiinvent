package wiinvent.com.vn.exception;

import lombok.Data;
import lombok.EqualsAndHashCode;
import wiinvent.com.vn.constant.ResponseCode;

@Data
@EqualsAndHashCode(callSuper = false)
public class ApplicationException extends RuntimeException {
    private ResponseCode responseCode;

    public ApplicationException(ResponseCode code) {
        this.responseCode = code;
    }

    public String getMessage() {
        return responseCode.getMessage();
    }
}
