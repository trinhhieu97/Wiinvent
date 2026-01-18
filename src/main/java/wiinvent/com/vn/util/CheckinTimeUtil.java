package wiinvent.com.vn.util;

import java.time.LocalTime;
import java.util.List;

public class CheckinTimeUtil {
    private CheckinTimeUtil() {}
    public static final List<TimeWindow> CHECKIN_TIME_WINDOWS = List.of(
            new TimeWindow(LocalTime.of(1, 0),  LocalTime.of(11, 0)),
            new TimeWindow(LocalTime.of(19, 0), LocalTime.of(21, 0))
    );

    public record TimeWindow(LocalTime start, LocalTime end) {
        public boolean contains(LocalTime time) {
            return !time.isBefore(start) && time.isBefore(end);
        }
    }

    public static boolean isValidCheckinTime(LocalTime now) {
        return CHECKIN_TIME_WINDOWS
                .stream()
                .anyMatch(w -> w.contains(now));
    }
}
