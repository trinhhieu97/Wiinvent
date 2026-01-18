package wiinvent.com.vn.dto.response;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
public class CheckinDayStatus {
    @JsonProperty("day_index")
    private int dayIndex;

    @JsonProperty("reward_point")
    private int rewardPoint;

    private boolean checked;

    private boolean today;
}
