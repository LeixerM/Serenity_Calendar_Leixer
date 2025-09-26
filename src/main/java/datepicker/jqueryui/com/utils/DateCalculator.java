package datepicker.jqueryui.com.utils;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;

public class DateCalculator {

    public static String dateWithDay(int day) {
        LocalDate today = LocalDate.now();
        LocalDate date = LocalDate.of(today.getYear(), today.getMonth(), day);
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("MM/dd/yyyy");
        return date.format(formatter);
    }

    public static String dateWithMontDifferent(int day) {
        LocalDate today = LocalDate.now();
        LocalDate date = LocalDate.of(today.getYear(), today.getMonth().plus(1), day);
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("MM/dd/yyyy");
        return date.format(formatter);
    }


}
