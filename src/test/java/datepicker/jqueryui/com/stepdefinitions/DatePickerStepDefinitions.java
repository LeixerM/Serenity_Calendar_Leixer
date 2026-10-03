package datepicker.jqueryui.com.stepdefinitions;

import datepicker.jqueryui.com.questions.DatePickerState;
import datepicker.jqueryui.com.tasks.NavigateTo;
import datepicker.jqueryui.com.tasks.SelectDate;
import datepicker.jqueryui.com.tasks.TypeDate;
import datepicker.jqueryui.com.utils.CalendarDates;
import io.cucumber.java.Before;
import io.cucumber.java.en.Given;
import io.cucumber.java.en.Then;
import io.cucumber.java.en.When;
import net.serenitybdd.screenplay.actors.OnStage;
import net.serenitybdd.screenplay.actors.OnlineCast;
import net.serenitybdd.screenplay.ensure.Ensure;

import java.time.LocalDate;

import static net.serenitybdd.screenplay.actors.OnStage.theActorCalled;
import static net.serenitybdd.screenplay.actors.OnStage.theActorInTheSpotlight;

public class DatePickerStepDefinitions {

    private static final String TODAY = "today";
    private static final String EXPECTED_DATE = "expectedDate";

    @Before
    public void setTheStage() {
        OnStage.setTheStage(new OnlineCast());
    }

    @Given("the user is on the jQuery UI datepicker demo")
    public void theUserIsOnTheDatePickerDemo() {
        theActorCalled("Leixer").wasAbleTo(NavigateTo.theDatePickerDemo());
        // Freeze "today" once per scenario so expectations never drift across midnight.
        theActorInTheSpotlight().remember(TODAY, LocalDate.now());
    }

    @When("the user selects day {string} of the {string} month")
    public void theUserSelectsADay(String day, String month) {
        LocalDate today = theActorInTheSpotlight().recall(TODAY);
        LocalDate target = CalendarDates.resolve(month, day, today);
        theActorInTheSpotlight().remember(EXPECTED_DATE, target);
        theActorInTheSpotlight().attemptsTo(SelectDate.on(target, today));
    }

    @When("the user types {string} into the date field")
    public void theUserTypesADate(String value) {
        theActorInTheSpotlight().remember(EXPECTED_DATE, CalendarDates.parseFieldValue(value));
        theActorInTheSpotlight().attemptsTo(TypeDate.intoTheField(value));
    }

    @Then("the date field shows the selected date as MM\\/dd\\/yyyy")
    public void theDateFieldShowsTheSelectedDate() {
        LocalDate expected = theActorInTheSpotlight().recall(EXPECTED_DATE);
        theActorInTheSpotlight().attemptsTo(
                Ensure.that("the date field value", DatePickerState.fieldValue())
                        .isEqualTo(CalendarDates.asFieldValue(expected))
        );
    }

    @Then("the calendar jumps to that month and highlights that day")
    public void theCalendarFollowsTheTypedDate() {
        LocalDate expected = theActorInTheSpotlight().recall(EXPECTED_DATE);
        theActorInTheSpotlight().attemptsTo(
                Ensure.that("the month shown by the calendar", DatePickerState.displayedMonth())
                        .isEqualToIgnoringCase(CalendarDates.asCalendarTitle(expected)),
                Ensure.that("the day highlighted by the calendar", DatePickerState.highlightedDay())
                        .isEqualTo(String.valueOf(expected.getDayOfMonth()))
        );
    }
}
