package utils;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;

public abstract class TimeUtils {
    private final static DateTimeFormatter dateFormat = DateTimeFormatter.ofPattern("MM/dd/yyyy");
    private final static DateTimeFormatter timeFormat = DateTimeFormatter.ofPattern("HH:mm");
    private final static DateTimeFormatter dateTimeFormat = DateTimeFormatter.ofPattern("MM/dd/yy HH:mm");

    public static LocalDate generateDate(String date) {
        return LocalDate.parse(date, dateFormat);
    }

    public static LocalTime generateTime(String date) {
        return LocalTime.parse(date, timeFormat);
    }

    public static LocalDateTime genDateTime(String date) {
        return LocalDateTime.parse(date, dateTimeFormat);
    }
}
