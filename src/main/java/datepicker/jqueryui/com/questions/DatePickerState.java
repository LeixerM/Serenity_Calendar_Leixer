package datepicker.jqueryui.com.questions;

import datepicker.jqueryui.com.ui.DatePickerPage;
import net.serenitybdd.screenplay.Question;
import net.serenitybdd.screenplay.questions.Text;
import net.serenitybdd.screenplay.questions.Value;

public final class DatePickerState {

    private DatePickerState() {
    }

    public static Question<String> fieldValue() {
        return Value.of(DatePickerPage.DATE_FIELD).describedAs("the date field value");
    }

    public static Question<String> displayedMonth() {
        return Text.of(DatePickerPage.DISPLAYED_MONTH).describedAs("the month shown by the calendar");
    }

    public static Question<String> highlightedDay() {
        return Text.of(DatePickerPage.HIGHLIGHTED_DAY).describedAs("the day highlighted by the calendar");
    }
}
