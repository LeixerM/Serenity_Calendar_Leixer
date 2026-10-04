package datepicker.jqueryui.com.utils;

import java.time.LocalDate;
import java.time.Month;
import java.time.YearMonth;
import java.time.format.DateTimeFormatter;
import java.time.temporal.ChronoUnit;
import java.util.Locale;

/**
 * Resolves the date descriptions used in the feature files into concrete dates
 * relative to a reference day, so scenarios stay deterministic whatever day they run on.
 */
public final class CalendarDates {

    private static final DateTimeFormatter FIELD_FORMAT = DateTimeFormatter.ofPattern("MM/dd/yyyy");
    private static final DateTimeFormatter TITLE_FORMAT = DateTimeFormatter.ofPattern("MMMM yyyy", Locale.ENGLISH);

    private CalendarDates() {
    }

    /**
     * @param month "current", "next", "previous" or "next January"
     * @param day   a day number, "last" (last day of that month) or "today"
     * @param today reference date
     */
    public static LocalDate resolve(String month, String day, LocalDate today) {
        YearMonth current = YearMonth.from(today);
        YearMonth yearMonth = resolveMonth(month.trim(), current);
        String dayValue = day.trim();
        if (dayValue.equalsIgnoreCase("today")) {
            if (!yearMonth.equals(current)) {
                throw new IllegalArgumentException("Day 'today' can only be combined with the current month");
            }
            return today;
        }
        if (dayValue.equalsIgnoreCase("last")) {
            return yearMonth.atEndOfMonth();
        }
        return yearMonth.atDay(Integer.parseInt(dayValue));
    }

    /** Months to page forward (positive) or backward (negative) from the month of {@code today}. */
    public static int monthsBetween(LocalDate today, LocalDate target) {
        return (int) ChronoUnit.MONTHS.between(YearMonth.from(today), YearMonth.from(target));
    }

    public static String asFieldValue(LocalDate date) {
        return date.format(FIELD_FORMAT);
    }

    public static String asCalendarTitle(LocalDate date) {
        return date.format(TITLE_FORMAT);
    }

    public static LocalDate parseFieldValue(String value) {
        return LocalDate.parse(value.trim(), FIELD_FORMAT);
    }

    private static YearMonth resolveMonth(String month, YearMonth current) {
        switch (month.toLowerCase(Locale.ROOT)) {
            case "current":
                return current;
            case "next":
                return current.plusMonths(1);
            case "previous":
                return current.minusMonths(1);
            case "next january":
                return YearMonth.of(current.getYear() + 1, Month.JANUARY);
            default:
                throw new IllegalArgumentException("Unsupported month description: " + month);
        }
    }
}
