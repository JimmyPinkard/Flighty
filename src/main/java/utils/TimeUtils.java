package utils;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;

public abstract class TimeUtils {
    private final static DateTimeFormatter dateFormat = DateTimeFormatter.ofPattern("M/d/y");
    private final static DateTimeFormatter timeFormat = DateTimeFormatter.ofPattern("HH:mm");
    private final static DateTimeFormatter dateTimeFormat =
            DateTimeFormatter.ofPattern("M/d/y HH:mm");

    public static LocalDate generateDate(String date) {
        return LocalDate.parse(date, dateFormat);
    }

    public static LocalTime generateTime(String date) {
        return LocalTime.parse(date, timeFormat);
    }

    public static LocalDateTime genDateTime(String date) {
        return LocalDateTime.parse(date, dateTimeFormat);
    }

    public static String toString(LocalDate date) {
        return date.format(dateFormat);
    }

    public static String toString(LocalTime date) {
        return date.format(timeFormat);
    }

    public static String toString(LocalDateTime date) {
        return date.format(timeFormat);
    }
}
