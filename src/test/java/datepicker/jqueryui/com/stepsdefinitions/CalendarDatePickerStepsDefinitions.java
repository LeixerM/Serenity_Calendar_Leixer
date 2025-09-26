package datepicker.jqueryui.com.stepsdefinitions;

import datepicker.jqueryui.com.questions.CampDatePickerCalendar;
import datepicker.jqueryui.com.tasks.NavigateTo;
import datepicker.jqueryui.com.tasks.SelectDateCalendarDatePickerTask;
import datepicker.jqueryui.com.utils.DateCalculator;
import io.cucumber.java.Before;
import io.cucumber.java.en.Given;
import io.cucumber.java.en.Then;
import io.cucumber.java.en.When;
import net.serenitybdd.screenplay.actors.OnStage;
import net.serenitybdd.screenplay.actors.OnlineCast;
import static net.serenitybdd.screenplay.GivenWhenThen.seeThat;
import static net.serenitybdd.screenplay.actors.OnStage.theActorCalled;
import static net.serenitybdd.screenplay.actors.OnStage.theActorInTheSpotlight;
import static org.hamcrest.Matchers.equalTo;

public class CalendarDatePickerStepsDefinitions {

    @Before
    public void setTheStage() {
        OnStage.setTheStage(new OnlineCast());
    }

    @Given("the user is on the jQuery calendar page")
    public void theUserIsOnTheJQueryCalendarPage() {
        theActorCalled("user").wasAbleTo(NavigateTo.navigatePageCalendar());
    }

    @When("select a specific date in the current month that corresponds to the {int}")
    public void theySelectASpecificDateInTheCurrentMonth(int day) {

        if (day == 15) {
            OnStage.theActorInTheSpotlight().attemptsTo(
                    SelectDateCalendarDatePickerTask.selectDateofCalendar(day)
            );
        } else if (day == 10) {
            OnStage.theActorInTheSpotlight().attemptsTo(
                    SelectDateCalendarDatePickerTask.updateMonthDatePicker(day)
            );
        } else {
            throw new IllegalArgumentException("El valor de día no es válido: " + day);
        }
        OnStage.theActorInTheSpotlight().remember("daySelected", day);
    }
    @Then("validates that the text of the specific date determined by the user")
    public void validatesThatTheTextOfTheSpecificDateDeterminedByTheUser() {
        int day = theActorInTheSpotlight().recall("daySelected");
        if (day == 15) {
            String dateExpected = DateCalculator.dateWithDay(day);
            OnStage.theActorInTheSpotlight().should(
                    seeThat("the value input of datapicker",
                            CampDatePickerCalendar.inputDateText(),
                            equalTo(dateExpected))
            );
        }  else if (day == 10) {
        String dateExpected = DateCalculator.dateWithMontDifferent(day);
            OnStage.theActorInTheSpotlight().should(
                    seeThat("the value input of datapicker",
                            CampDatePickerCalendar.inputDateText(),
                            equalTo(dateExpected))
            );
    } else {
        throw new IllegalArgumentException("El valor de día no es válido: " + day);
    }
    }
}
