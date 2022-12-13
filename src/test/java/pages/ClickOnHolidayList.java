package pages;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;

public class ClickOnHolidayList {
	WebDriver driver;

	public ClickOnHolidayList(WebDriver driver) {
		this.driver = driver;

	}

	@FindBy(xpath = "//*[@id=\"wrap\"]/div[2]/div[2]/div[1]/div[2]/div[2]/div[1]/div/div[2]/div[1]/a")
	WebElement holidayLink;

	public void clickOnHolidaysLink() {
		holidayLink.click();

	}

}
