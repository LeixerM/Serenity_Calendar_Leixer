package datepicker.jqueryui.com.questions;

import datepicker.jqueryui.com.userinterfaces.CalendarDatePickerUi;
import net.serenitybdd.screenplay.Question;

public class CampDatePickerCalendar {

    public static Question<String> inputDateText() {
        return actor -> CalendarDatePickerUi.DATE_CALENDAR_DATEPICKER.resolveFor(actor).getValue();
    }

    public static Question<Boolean> inputDateTexts() {
        return actor -> {
            Boolean allowed = actor.recall("datepickerWriteAllowed");
            return allowed != null && allowed;
        };
    }


}
