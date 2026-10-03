package datepicker.jqueryui.com.interactions;

import datepicker.jqueryui.com.ui.DatePickerPage;
import net.serenitybdd.annotations.Step;
import net.serenitybdd.screenplay.Actor;
import net.serenitybdd.screenplay.Interaction;
import net.serenitybdd.screenplay.actions.Click;
import net.serenitybdd.screenplay.targets.Target;
import net.serenitybdd.screenplay.waits.WaitUntil;

import static net.serenitybdd.screenplay.Tasks.instrumented;
import static net.serenitybdd.screenplay.matchers.WebElementStateMatchers.isVisible;

/**
 * Pages the open datepicker forward (positive) or backward (negative) a number of months.
 */
public class MoveCalendar implements Interaction {

    private final int months;

    public MoveCalendar(int months) {
        this.months = months;
    }

    public static MoveCalendar byMonths(int months) {
        return instrumented(MoveCalendar.class, months);
    }

    @Override
    @Step("{0} moves the calendar #months month(s)")
    public <T extends Actor> void performAs(T actor) {
        Target button = months >= 0 ? DatePickerPage.NEXT_MONTH : DatePickerPage.PREVIOUS_MONTH;
        for (int i = 0; i < Math.abs(months); i++) {
            actor.attemptsTo(
                    WaitUntil.the(button, isVisible()).forNoMoreThan(10).seconds(),
                    Click.on(button)
            );
        }
    }
}
