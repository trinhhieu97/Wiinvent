package wiinvent.com.vn.dto.response;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Getter;
import lombok.Setter;
import wiinvent.com.vn.constant.enums.PointHistoryType;

import java.time.LocalDateTime;

@Getter
@Setter
public class PointHistoryResponse {
    @JsonProperty("change_point")
    private int changePoint;

    @JsonProperty("point_history_type")
    private PointHistoryType type;

    private String description;

    @JsonProperty("created_at")
    private LocalDateTime createdAt;
}
