package wiinvent.com.vn.security;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
public class JwtData {
    private String username;
    private String userId;
}
