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
        dateFormat = DateTimeFormatter.ofPattern("M/d/y");
        timeFormat = DateTimeFormatter.ofPattern("HH:mm");
        dateTimeFormat = DateTimeFormatter.ofPattern("M/d/y HH:mm");
    }

    public static TimeUtils getInstance() {
        if (instance == null) {
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

    public String toString(LocalDate date) {
        return date.format(dateFormat);
    }

    public String toString(LocalTime date) {
        return date.format(timeFormat);
    }

    public String toString(LocalDateTime date) {
        return date.format(timeFormat) + " UTC";
    }
}
