package datepicker.jqueryui.com.tasks;

import datepicker.jqueryui.com.ui.DatePickerPage;
import net.serenitybdd.screenplay.Performable;
import net.serenitybdd.screenplay.Task;
import net.serenitybdd.screenplay.actions.Enter;
import net.serenitybdd.screenplay.waits.WaitUntil;

import static net.serenitybdd.screenplay.matchers.WebElementStateMatchers.isVisible;

public final class TypeDate {

    private TypeDate() {
    }

    /** Types a date straight into the field; the datepicker parses it on key up and follows it. */
    public static Performable intoTheField(String value) {
        return Task.where("{0} types " + value + " into the date field",
                Enter.theValue(value).into(DatePickerPage.DATE_FIELD),
                WaitUntil.the(DatePickerPage.CALENDAR, isVisible()).forNoMoreThan(10).seconds()
        );
    }
}
