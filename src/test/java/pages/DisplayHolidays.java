package pages;

import java.util.List;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;

public class DisplayHolidays {
	WebDriver driver;

	public DisplayHolidays(WebDriver driver) {
		this.driver = driver;

	}

	@FindBy(tagName = "tr")
	List<WebElement> allHols;

	public List<WebElement> retrieveHols() {
		return allHols;
	}

}
