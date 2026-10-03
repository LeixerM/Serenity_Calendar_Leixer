package datepicker.jqueryui.com.tasks;

import net.serenitybdd.model.environment.EnvironmentSpecificConfiguration;
import net.serenitybdd.screenplay.Performable;
import net.serenitybdd.screenplay.Task;
import net.serenitybdd.screenplay.actions.Open;
import net.serenitybdd.screenplay.actions.Switch;
import net.thucydides.model.environment.SystemEnvironmentVariables;

public final class NavigateTo {

    private NavigateTo() {
    }

    /** Opens the datepicker demo (base URL from serenity.conf) and enters its demo iframe. */
    public static Performable theDatePickerDemo() {
        String baseUrl = EnvironmentSpecificConfiguration
                .from(SystemEnvironmentVariables.currentEnvironmentVariables())
                .getProperty("webdriver.base.url");
        return Task.where("{0} opens the jQuery UI datepicker demo",
                Open.url(baseUrl),
                Switch.toFrame(0)
        );
    }
}
