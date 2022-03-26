package utils;

import java.time.LocalDate;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;

public abstract class TimeUtils {
    private final static DateTimeFormatter dateFormat = DateTimeFormatter.ofPattern("MM/dd/yyyy");
    private final static DateTimeFormatter timeFormat = DateTimeFormatter.ofPattern("HH:mm");

    public static LocalDate generateDate(String date) {
        return LocalDate.parse(date, dateFormat);
    }

    public static LocalTime generateTime(String date) {
        return LocalTime.parse(date, timeFormat);
    }
}
