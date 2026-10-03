package datepicker.jqueryui.com.utils;

import org.junit.jupiter.api.Test;

import java.time.LocalDate;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

class CalendarDatesTest {

    private static final LocalDate DECEMBER_15 = LocalDate.of(2026, 12, 15);
    private static final LocalDate JANUARY_10 = LocalDate.of(2027, 1, 10);

    @Test
    void nextMonthCrossesTheYearBoundaryInDecember() {
        assertEquals(LocalDate.of(2027, 1, 10), CalendarDates.resolve("next", "10", DECEMBER_15));
        assertEquals(1, CalendarDates.monthsBetween(DECEMBER_15, LocalDate.of(2027, 1, 10)));
    }

    @Test
    void previousMonthCrossesTheYearBoundaryInJanuary() {
        assertEquals(LocalDate.of(2026, 12, 20), CalendarDates.resolve("previous", "20", JANUARY_10));
        assertEquals(-1, CalendarDates.monthsBetween(JANUARY_10, LocalDate.of(2026, 12, 20)));
    }

    @Test
    void nextJanuaryIsAlwaysInTheFollowingYear() {
        assertEquals(LocalDate.of(2028, 1, 1), CalendarDates.resolve("next January", "1", JANUARY_10));
        assertEquals(12, CalendarDates.monthsBetween(JANUARY_10, LocalDate.of(2028, 1, 1)));
    }

    @Test
    void lastDayHandlesLeapYears() {
        assertEquals(LocalDate.of(2028, 2, 29), CalendarDates.resolve("current", "last", LocalDate.of(2028, 2, 3)));
        assertEquals(LocalDate.of(2027, 2, 28), CalendarDates.resolve("current", "last", LocalDate.of(2027, 2, 3)));
    }

    @Test
    void todayOnlyMakesSenseInTheCurrentMonth() {
        assertEquals(DECEMBER_15, CalendarDates.resolve("current", "today", DECEMBER_15));
        assertThrows(IllegalArgumentException.class, () -> CalendarDates.resolve("next", "today", DECEMBER_15));
    }

    @Test
    void formatsLikeTheDatePickerField() {
        assertEquals("02/29/2028", CalendarDates.asFieldValue(LocalDate.of(2028, 2, 29)));
        assertEquals("February 2028", CalendarDates.asCalendarTitle(LocalDate.of(2028, 2, 29)));
    }
}
