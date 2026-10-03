package datepicker.jqueryui.com.tasks;

import datepicker.jqueryui.com.interactions.MoveCalendar;
import datepicker.jqueryui.com.ui.DatePickerPage;
import datepicker.jqueryui.com.utils.CalendarDates;
import net.serenitybdd.screenplay.Performable;
import net.serenitybdd.screenplay.Task;
import net.serenitybdd.screenplay.actions.Click;
import net.serenitybdd.screenplay.waits.WaitUntil;

import java.time.LocalDate;

import static net.serenitybdd.screenplay.matchers.WebElementStateMatchers.isClickable;
import static net.serenitybdd.screenplay.matchers.WebElementStateMatchers.isVisible;

public final class SelectDate {

    private SelectDate() {
    }

    /** Opens the calendar, pages to the month of {@code target} (relative to {@code today}) and clicks the day. */
    public static Performable on(LocalDate target, LocalDate today) {
        return Task.where("{0} selects " + CalendarDates.asFieldValue(target) + " in the datepicker",
                Click.on(DatePickerPage.DATE_FIELD),
                WaitUntil.the(DatePickerPage.CALENDAR, isVisible()).forNoMoreThan(10).seconds(),
                MoveCalendar.byMonths(CalendarDates.monthsBetween(today, target)),
                WaitUntil.the(DatePickerPage.dayCell(target), isClickable()).forNoMoreThan(10).seconds(),
                Click.on(DatePickerPage.dayCell(target))
        );
    }
}
