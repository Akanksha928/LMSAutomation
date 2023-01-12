package pages;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;

public class LMSPage {
	WebDriver driver;

	public LMSPage(WebDriver driver) {
		this.driver = driver;
		PageFactory.initElements(driver, this);

	}

	@FindBy(xpath = "//*[@id=\"wrap\"]/div[2]/div[2]/div[1]/div[2]/div[2]/div[1]/div/div[2]/div[1]/a")
	WebElement holidayLink;

	public void clickOnHolidaysLink() {
		holidayLink.click();

	}

}
