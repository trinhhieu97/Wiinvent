package wiinvent.com.vn.constant;

import lombok.Getter;
import org.springframework.http.HttpStatus;

@Getter
public enum ResponseCode {
    SUCCESS(0, "Success"),
    ERROR(1, "Error occurred"),
    USERNAME_ALREADY_EXISTS(2, "Username already exists"),
    USER_NOT_FOUND(3, "User not found"),
    INVALID_PASSWORD(4, "Invalid password"),
    WRONG_DATA_FORMAT(5, "Error Data Format"),
    UNAUTHORIZED(6,"Unauthorized"),
    ALREADY_CHECKED_IN(7, "Already checked in today"),
    MAX_CHECKIN_REACHED(8, "Max 7 checkins per month"),
    OUT_OF_TIME(9, "Out of check-in time"),
    INSUFFICIENT_POINT(10, "Insufficient point");

    private int code;
    private String message;

    ResponseCode(int i, String success) {
        this.code = i;
        this.message = success;
    }
}
