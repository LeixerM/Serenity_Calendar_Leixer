package datepicker.jqueryui.com.runners;


import net.serenitybdd.cucumber.CucumberWithSerenity;
import org.junit.runner.RunWith;
import io.cucumber.junit.CucumberOptions.SnippetType;
import io.cucumber.junit.CucumberOptions;

@RunWith(CucumberWithSerenity.class)
@CucumberOptions(
        plugin = {"pretty"},
        features = "src/test/resources/features",
        glue = "datepicker.jqueryui.com.stepsdefinitions",
        snippets = SnippetType.CAMELCASE
        //tags = "@CampBlocked"
)
public class CalendarDatePickerRunners {
}
