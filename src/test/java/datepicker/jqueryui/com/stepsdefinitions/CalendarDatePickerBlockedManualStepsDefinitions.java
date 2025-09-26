package datepicker.jqueryui.com.stepsdefinitions;

import datepicker.jqueryui.com.questions.CampDatePickerCalendar;
import datepicker.jqueryui.com.tasks.SelectDateCalendarDatePickerTask;
import datepicker.jqueryui.com.utils.DateCalculator;
import io.cucumber.java.en.Then;
import io.cucumber.java.en.When;

import static net.serenitybdd.screenplay.GivenWhenThen.seeThat;
import static net.serenitybdd.screenplay.actors.OnStage.theActorInTheSpotlight;
import static org.hamcrest.Matchers.equalTo;

public class CalendarDatePickerBlockedManualStepsDefinitions {

    @When("the user enters characters in the calendar field")
    public void theUserEntersCharactersInTheCalendarField() {
        theActorInTheSpotlight().attemptsTo(
                SelectDateCalendarDatePickerTask.ingressDate()
        );
        theActorInTheSpotlight().should(
                seeThat(CampDatePickerCalendar.inputDateTexts(), equalTo(false))
        );

    }
    @Then("it is validated that only the date can be selected from the calendar")
    public void itIsValidatedThatOnlyTheDateCanBeSelectedFromTheCalendar() {
        int day = 17;
        theActorInTheSpotlight().attemptsTo(
                SelectDateCalendarDatePickerTask.selectDateofCalendar(day)
        );
        theActorInTheSpotlight().remember("daySelected", day);

        String dateExpected = DateCalculator.dateWithDay(day);
        theActorInTheSpotlight().should(
                seeThat("the value input of datapicker is day selected",
                        CampDatePickerCalendar.inputDateText(),
                        equalTo(dateExpected))
        );
    }
}
