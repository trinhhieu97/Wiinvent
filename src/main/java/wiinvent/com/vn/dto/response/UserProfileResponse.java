package wiinvent.com.vn.dto.response;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class UserProfileResponse {
    private String id;

    @JsonProperty("avatar_url")
    private String avatarUrl;

    @JsonProperty("display_name")
    private String username;

    @JsonProperty("total_point")
    private long totalPoint;
}
