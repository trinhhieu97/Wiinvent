package wiinvent.com.vn.util;

import java.time.format.DateTimeFormatter;

public final class DateTimeUtil {
    private DateTimeUtil() {}

    public static final String YEAR_MONTH_PATTERN = "yyyyMM";

    public static final DateTimeFormatter YEAR_MONTH_FORMATTER =
            DateTimeFormatter.ofPattern(YEAR_MONTH_PATTERN);
}
