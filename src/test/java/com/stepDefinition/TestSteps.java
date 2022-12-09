package com.stepDefinition;

import java.time.Duration;
import java.util.ArrayList;
import java.util.List;

import org.apache.log4j.Logger;
import org.apache.log4j.PropertyConfigurator;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.edge.EdgeDriver;
import org.openqa.selenium.support.PageFactory;

import com.aventstack.extentreports.ExtentReports;
import com.aventstack.extentreports.ExtentTest;
import com.aventstack.extentreports.Status;
import com.aventstack.extentreports.markuputils.ExtentColor;
import com.aventstack.extentreports.markuputils.MarkupHelper;
import com.aventstack.extentreports.reporter.ExtentSparkReporter;

import io.cucumber.java.en.Given;
import io.cucumber.java.en.Then;
import io.cucumber.java.en.When;
import pages.ClickOnHolidayList;
import pages.DisplayHolidays;
import pages.ValidateHolidays;

public class TestSteps {

	WebDriver driver;
	ExtentReports report;
	ExtentTest test;
	Logger log = Logger.getLogger(TestSteps.class);

	@Given("User is on EY LMS page")
	public void user_is_on_ey_lms_page() {

		// Setting up
		System.setProperty("webdriver.edge.driver", "Drivers\\msedgedriver.exe");
		driver = new EdgeDriver();
		driver.get("https://lms.ey.net/");
		driver.manage().window().maximize();

		// Configuring log4j
		PropertyConfigurator.configure("src\\test\\resources\\log4j.properties");

		report = new ExtentReports();
		report.setSystemInfo("OS", System.getProperty("os.name"));
		report.setSystemInfo("Java Version", System.getProperty("java.version"));
		report.setSystemInfo("URL", "https://lms.ey.net/");

		ExtentSparkReporter spark = new ExtentSparkReporter("target/report.html");
		report.attachReporter(spark);
		test = report.createTest("HolidaysList");

		test.log(Status.PASS, "LMS Webpage opened");
		log.info("LMS Webpage opened");

	}

	@When("User navigates to the Holiday List Page")
	public void user_navigates_to_the_holiday_list_page() {
		ClickOnHolidayList display = PageFactory.initElements(driver, ClickOnHolidayList.class);
		display.clickOnHolidaysLink();
		test.log(Status.PASS, "Clicked on Holidays List link");
		log.info("Clicked on Holidays link");
	}

	@Then("Validate if the public holiday count is equal to or greater than {int}")
	public void validate_if_the_public_holiday_count_is_equal_to_or_greater_than(Integer int1) {
		driver.manage().timeouts().implicitlyWait(Duration.ofSeconds(5));
		ValidateHolidays validation = PageFactory.initElements(driver, ValidateHolidays.class);

		log.info("Checking if public holidays are greater or lesser than 10");
		if (validation.checkSize() >= 10) {
			test.fail("Public holidays are greater than or equal to 10");
			log.info("Public holidays less than or equal to 10");

		} else {
			test.pass("Public holidays less than 10");
			log.info("Public holidays less than 10");
		}

	}

	@Then("User is able to split the holiday details as per Holiday Type")
	public void user_is_able_to_split_the_holiday_details_as_per_holiday_type() {

		DisplayHolidays display = PageFactory.initElements(driver, DisplayHolidays.class);

		List<String> optionalHols = new ArrayList<String>();

		// Iterating through list of web elements to retrieve optional holidays
		for (WebElement wb : display.retrieveHols()) {
			if (wb.getText().contains("Optional Holiday")) {
				optionalHols.add(wb.getText().replace("Optional Holiday", ""));
			}
		}

		test.pass(MarkupHelper.createLabel("List of Optional Holidays", ExtentColor.GREEN));
		test.info(MarkupHelper.createOrderedList(optionalHols));
		log.info("Printed list of optional holidays in report");

		List<String> publicHols = new ArrayList<String>();


		for (WebElement wb : display.retrieveHols()) {
			if (wb.getText().contains("Public Holiday")) {
				publicHols.add(wb.getText().replace("Public Holiday", ""));
			}
		}

		test.pass(MarkupHelper.createLabel("List of Public Holidays", ExtentColor.GREEN));
		test.info(MarkupHelper.createOrderedList(publicHols));
		log.info("Printed list of Public holidays in report");
		report.flush();
		log.info("Browser closed.");
		driver.quit();

	}

}
