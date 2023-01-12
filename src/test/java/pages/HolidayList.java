package pages;

import java.util.List;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;

public class HolidayList {
	WebDriver driver;

	public HolidayList(WebDriver driver) {
		this.driver = driver;
		PageFactory.initElements(driver, this);

	}

	@FindBy(xpath = "//*[contains(text(),'Public Holiday')]")
	List<WebElement> PublicHoliday;
	
	@FindBy(tagName = "tr")
	List<WebElement> allHols;

	public int checkSize() {
		return PublicHoliday.size();
	}

	public List<WebElement> retrieveHols() {
		return allHols;
	}

}
