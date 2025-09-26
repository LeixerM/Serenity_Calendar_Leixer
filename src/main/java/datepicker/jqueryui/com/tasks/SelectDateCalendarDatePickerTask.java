package datepicker.jqueryui.com.tasks;

import datepicker.jqueryui.com.userinterfaces.CalendarDatePickerUi;
import net.serenitybdd.screenplay.Actor;
import net.serenitybdd.screenplay.Performable;
import net.serenitybdd.screenplay.Task;
import net.serenitybdd.screenplay.actions.*;
import net.serenitybdd.screenplay.waits.WaitUntil;

import static net.serenitybdd.screenplay.matchers.WebElementStateMatchers.*;


public class SelectDateCalendarDatePickerTask {
    public static Performable selectDateofCalendar(int day) {
        return Task.where("{0} select date of calendar datepicker jquery page",
                // Cambiar al iframe
                Switch.toFrame(0),
                // Clic en el campo de fecha
                Click.on(CalendarDatePickerUi.DATE_CALENDAR_DATEPICKER),
                Click.on(CalendarDatePickerUi.DAY_DATEPICKER(day))
        );
    }
    public static Performable updateMonthDatePicker(int day) {
        return Task.where("{0} select month of calendar datepicker jquery page",
                // Cambiar al iframe
                Switch.toFrame(0),
                // Clic en el campo de fecha
                Click.on(CalendarDatePickerUi.DATE_CALENDAR_DATEPICKER),
                WaitUntil.the(CalendarDatePickerUi.NEXT_BUTTON_MONTH_DATEPICKER,isVisible()).forNoMoreThan(10).seconds(),
                Click.on(CalendarDatePickerUi.NEXT_BUTTON_MONTH_DATEPICKER),
                Click.on(CalendarDatePickerUi.DAY_DATEPICKER(day))
        );

    }

    public static Performable ingressDate() {
        Actor actor = Actor.named("user");
        String date = "10/12/2024";
        actor.remember("datepickerWriteAllowed", false);
        actor.remember("attemptedDate", date);
        return Task.where("{0} ingress Date of calendar datepicker jquery page"
        );
    }
}

