package wiinvent.com.vn.dto.response;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;
import wiinvent.com.vn.constant.enums.CheckinButtonState;

import java.util.List;

@Getter
@Setter
@AllArgsConstructor
public class CheckinStatusResponse {
    private List<CheckinDayStatus> days;
    private CheckinButtonState buttonState;
}
