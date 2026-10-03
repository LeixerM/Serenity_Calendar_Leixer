package datepicker.jqueryui.com.ui;

import net.serenitybdd.screenplay.targets.Target;
import org.openqa.selenium.By;

import java.time.LocalDate;

/**
 * Locators of the jQuery UI datepicker demo (rendered inside the demo iframe).
 */
public final class DatePickerPage {

    public static final Target DATE_FIELD = Target.the("date field")
            .located(By.id("datepicker"));

    public static final Target CALENDAR = Target.the("datepicker calendar")
            .located(By.id("ui-datepicker-div"));

    public static final Target NEXT_MONTH = Target.the("next month button")
            .located(By.cssSelector("#ui-datepicker-div a[data-handler='next']"));

    public static final Target PREVIOUS_MONTH = Target.the("previous month button")
            .located(By.cssSelector("#ui-datepicker-div a[data-handler='prev']"));

    public static final Target DISPLAYED_MONTH = Target.the("displayed month and year")
            .located(By.cssSelector("#ui-datepicker-div .ui-datepicker-title"));

    public static final Target HIGHLIGHTED_DAY = Target.the("highlighted day")
            .located(By.cssSelector("#ui-datepicker-div a.ui-state-active"));

    private DatePickerPage() {
    }

    /**
     * A selectable day cell, matched by day, month and year so the click only succeeds
     * once the calendar shows the expected month. jQuery UI stores months zero-based
     * in {@code data-month}.
     */
    public static Target dayCell(LocalDate date) {
        return Target.the("day " + date)
                .located(By.xpath(String.format(
                        "//td[@data-handler='selectDay' and @data-month='%d' and @data-year='%d']/a[normalize-space()='%d']",
                        date.getMonthValue() - 1, date.getYear(), date.getDayOfMonth())));
    }
}
