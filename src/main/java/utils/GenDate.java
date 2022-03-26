package utils;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;

public abstract class GenDate {
    private final static DateTimeFormatter format = DateTimeFormatter.ofPattern("MM/dd/yyyy");
    public static LocalDate generateDate(String date) {
        return LocalDate.parse(date, format);
    }
}
