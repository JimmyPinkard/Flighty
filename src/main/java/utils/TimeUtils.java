package utils;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;

public class TimeUtils {
    private static TimeUtils instance;
    private final DateTimeFormatter dateFormat;
    private final DateTimeFormatter timeFormat;
    private final DateTimeFormatter dateTimeFormat;

    public TimeUtils() {
        dateFormat = DateTimeFormatter.ofPattern("MM/dd/yyyy");
        timeFormat = DateTimeFormatter.ofPattern("HH:mm");
        dateTimeFormat = DateTimeFormatter.ofPattern("MM/dd/yy HH:mm");
    }

    public static TimeUtils getInstance() {
        if(instance == null) {
            instance = new TimeUtils();
        }
        return instance;
    }

    public LocalDate generateDate(String date) {
        return LocalDate.parse(date, dateFormat);
    }

    public LocalTime generateTime(String date) {
        return LocalTime.parse(date, timeFormat);
    }

    public LocalDateTime genDateTime(String date) {
        return LocalDateTime.parse(date, dateTimeFormat);
    }
}
