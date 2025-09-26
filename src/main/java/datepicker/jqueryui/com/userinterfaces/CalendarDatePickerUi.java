package datepicker.jqueryui.com.userinterfaces;

import net.serenitybdd.screenplay.targets.Target;
import org.openqa.selenium.By;

public class CalendarDatePickerUi {


    public static final Target INPUT_CALENDAR_DATEPICKER = Target.the("input datepicker calendar")
            .locatedBy("frame");

    public static final Target DATE_CALENDAR_DATEPICKER = Target.the("text date of the datepicker calendar")
            .located(By.id("datepicker"));

    public static Target DAY_DATEPICKER(int day) {
        return Target.the("day " + day + " of calendar datepicker")
                .locatedBy("//a[text()='" + day + "']");
    }

    public static final Target NEXT_BUTTON_MONTH_DATEPICKER = Target.the("button next month of datepicker calendar")
            .located(By.xpath("//span[contains(@class,'ui-icon-circle-triangle-e')]"));

}
