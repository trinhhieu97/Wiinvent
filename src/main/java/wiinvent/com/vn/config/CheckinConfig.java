package wiinvent.com.vn.config;

import lombok.Getter;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Configuration;

@Configuration
@Getter
public class CheckinConfig {
    @Value("${application.checkin.max-days-per-month}")
    private int maxDaysPerMonth;
}
