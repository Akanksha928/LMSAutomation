package pages;

import java.util.List;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;

public class ValidateHolidays {
	final WebDriver driver;

	public ValidateHolidays(WebDriver driver) {
		this.driver = driver;

	}

	@FindBy(xpath = "//*[contains(text(),'Public Holiday')]")
	List<WebElement> PublicHoliday;

	public int checkSize() {
		return PublicHoliday.size();
	}

}
